package com.xiaoxiang.xxware.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;

import com.xiaoxiang.xxware.bean.WmsWareInfo;
import com.xiaoxiang.xxware.bean.WmsWareOrderTask;
import com.xiaoxiang.xxware.bean.WmsWareSku;
import com.xiaoxiang.xxware.enums.TaskStatus;
import com.xiaoxiang.xxware.service.XXwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @return
 */

@Controller
public class XXwareController {

    @Autowired
    XXwareService xxwareService;

    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("wareSkuListPage")
    public String wareSkuListPage(){
        return "wareSkuListPage";
    }

    //根据sku判断是否有库存
    @RequestMapping("hasStock")
    @ResponseBody
    public ResponseEntity<String> hasStock(@RequestParam Map<String,String> hashMap){
        String numstr = (String) hashMap.get("num");
        Integer num=Integer.parseInt(numstr);
        String skuid =(String)hashMap.get("skuId");
        boolean hasStock = xxwareService.hasStockBySkuId( skuid , num);
        if(hasStock){
            return   ResponseEntity.ok("1");
        }
        return  ResponseEntity.ok("0");
    }


    //根据skuid 返回 仓库
    @RequestMapping(value = "skuWareInfo")
    @ResponseBody
    public  ResponseEntity<String> getWareInfoBySkuid(String skuid){
        if(skuid==null){
            return     ResponseEntity.noContent().build();
        }
        List<WmsWareInfo> wareInfos = xxwareService.getWareInfoBySkuid( skuid );
        String jsonString = JSON.toJSONString(wareInfos);
        return ResponseEntity.ok(jsonString);
    }


    @RequestMapping(value = "wareInfo")
    @ResponseBody
    public void addWareInfo(){
          xxwareService.addWareInfo();
    }

    //根据skuid 返回 仓库
    @RequestMapping(value = "wareSkuMap"  )
    @ResponseBody
    public ResponseEntity<String> getWareSkuMap(@RequestParam("skuid") List<String> skuidsList){
       // List<String> skuidsList = JSON.parseArray(skuids, String.class) ;
        Map<String, List<String>> wareSkuMap = xxwareService.getWareSkuMap(skuidsList);
        String jsonString = JSON.toJSONString(wareSkuMap);
        return ResponseEntity.ok(jsonString);
    }



    @RequestMapping(value = "saveWareSku" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> addWareSku( WmsWareSku wareSku){
         xxwareService.addWareSku(wareSku);
         return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "wareSkuList" ,method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<WmsWareSku> getWareSkuList(HttpServletResponse response){
        List<WmsWareSku> wareSkuList = xxwareService.getWareSkuList();
        return wareSkuList;
    }

    @RequestMapping(value = "wareInfoList" ,method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<WmsWareInfo> getWareInfoList(){
        List<WmsWareInfo> wareInfoList = xxwareService.getWareInfoList();
        return wareInfoList;
    }


    /***
     * 出库
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value="delivery",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> deliveryStock(HttpServletRequest httpServletRequest){
        String id = httpServletRequest.getParameter("id");
        String trackingNo = httpServletRequest.getParameter("trackingNo");
        WmsWareOrderTask wareOrderTask=new WmsWareOrderTask();
        wareOrderTask.setId(id);
        wareOrderTask.setTrackingNo(trackingNo);
        xxwareService.deliveryStock(wareOrderTask);
        return  ResponseEntity.ok().build();
    }


    @RequestMapping(value="taskList",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getWareOrderTaskList(HttpServletRequest httpServletRequest){
        List<WmsWareOrderTask> wareOrderTaskList = xxwareService.getWareOrderTaskList(null);
        SerializeConfig config = new SerializeConfig();
        config.configEnumAsJavaBean(TaskStatus.class);
        String jsonString = JSON.toJSONString(wareOrderTaskList);
        return ResponseEntity.ok().body(jsonString);
    }



}
