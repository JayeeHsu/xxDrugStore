package com.xiaoxiang.xxdrugstore.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.dubbo.config.annotation.Service;


import com.xiaoxiang.xxdrugstore.bean.*;
import com.xiaoxiang.xxdrugstore.manage.mapper.PmsSkuAttrValueMapper;
import com.xiaoxiang.xxdrugstore.manage.mapper.PmsSkuImageMapper;
import com.xiaoxiang.xxdrugstore.manage.mapper.PmsSkuInfoMapper;
import com.xiaoxiang.xxdrugstore.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.xiaoxiang.xxdrugstore.service.SkuService;
import com.xiaoxiang.xxdrugstore.util.RedisUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.Index;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JestClient jestClient;

    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        // 插入skuInfo
        int i = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }


        //存入elasticsearch
        //查询mysql数据
        List<PmsSkuInfo> pmsSkuInfoList = new ArrayList<>();
        pmsSkuInfoList = getAllSku();
        //转化为es的数据结构
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();

        for (PmsSkuInfo pmsSkuInfoES : pmsSkuInfoList) {
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();

            BeanUtils.copyProperties(pmsSkuInfoES, pmsSearchSkuInfo);

            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
        }


        //导入es
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            Index put = new Index.Builder(pmsSearchSkuInfo).index("xxdrugstore").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId()).build();
            try {jestClient.execute(put);}
            catch (IOException e) {
                e.printStackTrace();
            }
        }




        // 发出商品的缓存同步消息
        // 发出商品的搜索引擎的同步消息

    }


    public PmsSkuInfo getSkuByIdFromDb(String skuId){
        // sku商品对象
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        // sku的图片集合
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(pmsSkuImages);
        return skuInfo;
    }

    @Override
    public PmsSkuInfo getSkuById(String skuId,String ip) {
        System.out.println("ip为"+ip+"的买家:"+Thread.currentThread().getName()+"进入的商品详情的请求");
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        // 链接缓存
        Jedis jedis = redisUtil.getJedis();
        // 查询缓存
        String skuKey = "sku:"+skuId+":info";
        String skuJson = jedis.get(skuKey);

        if(StringUtils.isNotBlank(skuJson)){//if(skuJson!=null&&!skuJson.equals(""))
            System.out.println("ip为"+ip+"的买家:"+Thread.currentThread().getName()+"从缓存中获取商品详情");

            pmsSkuInfo = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }else{
            // 如果缓存中没有，查询mysql
            System.out.println("ip为"+ip+"的买家:"+Thread.currentThread().getName()+"发现缓存中没有，申请缓存的分布式锁："+"sku:" + skuId + ":lock");

            // 设置分布式锁
            String token = UUID.randomUUID().toString();//生成一个独一无二的token
            //UUID.randomUUID().toString()是javaJDK提供的一个自动生成主键的方法。UUID(Universally Unique Identifier)全局唯一标识符,是指在一台机器上生成的数字，它保证对在同一时空中的所有机器都是唯一的，是由一个十六位的数字组成,表现出来的形式。
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10*1000);// 拿到锁的线程有10秒的过期时间,将token作为值给分布式锁
            if(StringUtils.isNotBlank(OK)&&OK.equals("OK")){
                // 设置成功，有权在10秒的过期时间内访问数据库
                System.out.println("ip为"+ip+"的买家:"+Thread.currentThread().getName()+"有权在10秒的过期时间内访问数据库："+"sku:" + skuId + ":lock");

                pmsSkuInfo =  getSkuByIdFromDb(skuId);

                if(pmsSkuInfo!=null){
                    // mysql查询结果存入redis
                    jedis.set("sku:"+skuId+":info",JSON.toJSONString(pmsSkuInfo));
                }else{
                    // 数据库中不存在该sku
                    // 为了防止缓存穿透将，null或者空字符串值设置给redis(如果不这样做，系统每次都会去mysql里查一个不存在的数据)
                    jedis.setex("sku:"+skuId+":info",60*3,JSON.toJSONString(""));
                    //设置三分钟的过期时间
                }

                // 在访问mysql后，将mysql的分布锁释放
                System.out.println("ip为"+ip+"的买家:"+Thread.currentThread().getName()+"使用完毕，将锁归还："+"sku:" + skuId + ":lock");
                String lockToken = jedis.get("sku:" + skuId + ":lock");
                if(StringUtils.isNotBlank(lockToken)&&lockToken.equals(token)){// 用token确认删除的是自己的sku的锁
                    //jedis.eval("lua");可与用lua脚本，在查询到key的同时删除该key，防止高并发下的get意外的发生
                    jedis.del("sku:" + skuId + ":lock");
                }
            }else{
                // 设置失败，自旋（该线程在睡眠几秒后，重新尝试访问本方法）
                System.out.println("ip为"+ip+"的买家:"+Thread.currentThread().getName()+"没有拿到锁，开始自旋");

                return getSkuById(skuId,ip);
            }
        }
        jedis.close();
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {

        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);

        return pmsSkuInfos;
    }

    @Override
    public List<PmsSkuInfo> getAllSku() {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();

        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String skuId = pmsSkuInfo.getId();

            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> select = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);

            pmsSkuInfo.setSkuAttrValueList(select);
        }
        return pmsSkuInfos;
    }

    @Override
    public boolean checkPrice(String productSkuId, BigDecimal productPrice) {

        boolean b = false;

        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(productSkuId);
        PmsSkuInfo pmsSkuInfo1 = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        BigDecimal price = pmsSkuInfo1.getPrice();

        if(price.compareTo(productPrice)==0){
            b = true;
        }


        return b;
    }
}
