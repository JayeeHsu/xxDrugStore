package com.xiaoxiang.xxdrugstore.user.service.impl;



import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.xiaoxiang.xxdrugstore.bean.UmsMember;
import com.xiaoxiang.xxdrugstore.bean.UmsMemberReceiveAddress;
import com.xiaoxiang.xxdrugstore.service.UserService;
import com.xiaoxiang.xxdrugstore.user.mapper.UmsMemberReceiveAddressMapper;
import com.xiaoxiang.xxdrugstore.user.mapper.UserMapper;
import com.xiaoxiang.xxdrugstore.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import redis.clients.jedis.Jedis;



import java.util.Date;
import java.util.List;

@Service //dubbo的Service注解
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    @Autowired
    RedisUtil redisUtil;
    //UmsMember↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    //添加用户
    @Override
    public String addUser(UmsMember umsMember) {
        String Registed="1";
        UmsMember userByUserName = getUserByUserName(umsMember.getUsername());
        if(userByUserName!=null){//该用户名已被注册
            Registed="2";
        }else {
            umsMember.setStatus("1");
            umsMember.setCreateTime(new Date());
            userMapper.insertSelective(umsMember);
        }

        return Registed;

    }

//    @Override
//    public int addUser(String memberLevelId,
//                       String username,
//                       String password,
//                       String nickname,
//                       String phone,
//                       String status,
//                       Date createTime,
//                       String icon,
//                       String gender,
//                       Date birthday,
//                       String city,
//                       String job,
//                       String personalizedSignature,
//                       String source_type,
//                       int integration,
//                       int growth,
//                       int luckeyCount,
//                       int historyIntegration) {
//        UmsMember umsMember = new UmsMember();
//        umsMember.setMemberLevelId(memberLevelId);
//        umsMember.setUsername(username);
//        umsMember.setPassword(password);
//        umsMember.setNickname(nickname);
//        umsMember.setPhone(phone);
//        umsMember.setStatus(status);
//        umsMember.setCreateTime(createTime);
//        umsMember.setIcon(icon);
//        umsMember.setGender(gender);
//        umsMember.setBirthday(birthday);
//        umsMember.setCity(city);
//        umsMember.setJob(job);
//        umsMember.setPersonalizedSignature(personalizedSignature);
//        umsMember.setSourceType(source_type);
//        umsMember.setIntegration(integration);
//        umsMember.setGrowth(growth);
//        umsMember.setLuckeyCount(luckeyCount);
//        umsMember.setHistoryIntegration(historyIntegration);
//
//        int result = userMapper.insertSelective(umsMember);
//        return result;
//    }


    //通过主键删除用户
    @Override
    public int deleteUserById(String id) {
        UmsMember umsMember=new UmsMember();
        umsMember.setId(id);
        int result=userMapper.deleteByPrimaryKey(umsMember);
        return result;
    }
//修改用户
    @Override
    public int updateUser(@RequestBody UmsMember umsMember
                           //String id, String memberLevelId, String username, String password, String nickname, String phone, String status, Date createTime, String icon, int gender, Date birthday, String city, String job, String personalizedSignature, int source_type, int integration, int growth, int luckeyCount, int historyIntegration
        ) {
//        UmsMember umsMember = new UmsMember();
//        umsMember.setId(id);
//        umsMember.setMemberLevelId(memberLevelId);
//        umsMember.setUsername(username);
//        umsMember.setPassword(password);
//        umsMember.setNickname(nickname);
//        umsMember.setPhone(phone);
//        umsMember.setStatus(status);
//        umsMember.setCreateTime(createTime);
//        umsMember.setIcon(icon);
//        umsMember.setGender(gender);
//        umsMember.setBirthday(birthday);
//        umsMember.setCity(city);
//        umsMember.setJob(job);
//        umsMember.setPersonalizedSignature(personalizedSignature);
//        umsMember.setSourceType(source_type);
//        umsMember.setIntegration(integration);
//        umsMember.setGrowth(growth);
//        umsMember.setLuckeyCount(luckeyCount);
//        umsMember.setHistoryIntegration(historyIntegration);
        int result=userMapper.updateByPrimaryKeySelective(umsMember);
        return result;
    }
//根据userName查询用户
    @Override
    public UmsMember getUserByUserName(String userName) {
        UmsMember umsMember= new UmsMember();
        umsMember.setUsername(userName);
        UmsMember umsMemberResult=userMapper.selectOne(umsMember);
        return umsMemberResult;
    }


    //查询所有用户
    @Override
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMemberList = userMapper.selectAll();//userMapper.selectAllUser();
        return umsMemberList;
    }
    //UmsMember↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    //UmsMemberReceiveAddress↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    //添加收货地址
    @Override
    public String addReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress ) {

        umsMemberReceiveAddress.setDefaultStatus("0");
       umsMemberReceiveAddressMapper.insert(umsMemberReceiveAddress);
        //方法：int insert(T record);
        //
        //说明：保存一个实体，null的属性也会保存，不会使用数据库默认值
        //方法：int insertSelective(T record);
        //
        //说明：保存一个实体，null的属性不会保存，会使用数据库默认值
        return "1";
    }


    //删除收货地址
    @Override
    public int deleteReceiveAddress(String memberId, String name, String phoneNumber, String defaultStatus, String postCode, String province, String city, String region, String detailAddress) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = null;
        umsMemberReceiveAddress.setMemberId(memberId);
        umsMemberReceiveAddress.setName(name);
        umsMemberReceiveAddress.setPhoneNumber(phoneNumber);
        umsMemberReceiveAddress.setDefaultStatus(defaultStatus);
        umsMemberReceiveAddress.setPostCode(postCode);
        umsMemberReceiveAddress.setProvince(province);
        umsMemberReceiveAddress.setCity(city);
        umsMemberReceiveAddress.setRegion(region);
        umsMemberReceiveAddress.setDetailAddress(detailAddress);
        int result = umsMemberReceiveAddressMapper.delete(umsMemberReceiveAddress);
        return result;
    }

    //通过主键Id删除收货地址
    @Override
    public int deleteReceiveAddressById(String id) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setId(id);
        int result = umsMemberReceiveAddressMapper.delete(umsMemberReceiveAddress);
        return result;
    }

    //修改收货地址
    @Override
    public int updateReceiveAddress(String id, String memberId, String name, String phoneNumber, String defaultStatus, String postCode, String province, String city, String region, String detailAddress) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setId(id);
        umsMemberReceiveAddress.setMemberId(memberId);
        umsMemberReceiveAddress.setName(name);
        umsMemberReceiveAddress.setPhoneNumber(phoneNumber);
        umsMemberReceiveAddress.setDefaultStatus(defaultStatus);
        umsMemberReceiveAddress.setPostCode(postCode);
        umsMemberReceiveAddress.setProvince(province);
        umsMemberReceiveAddress.setCity(city);
        umsMemberReceiveAddress.setRegion(region);
        umsMemberReceiveAddress.setDetailAddress(detailAddress);
        int result = umsMemberReceiveAddressMapper.updateByPrimaryKeySelective(umsMemberReceiveAddress);
        return result;
    }




    //根据用户id查询收货地址
    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        //通过外键查询表字段
        //封装的参数对象
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);

        //List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.selectByExample(umsMemberReceiveAddress);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);

        return umsMemberReceiveAddresses;
    }


    //UmsMemberReceiveAddress↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    //登录
    @Override
    public UmsMember login(UmsMember umsMember) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();

            if(jedis!=null){//得到redis
                String umsMemberStr = jedis.get("user:" + umsMember.getPassword()+umsMember.getUsername() + ":info");

                if (StringUtils.isNotBlank(umsMemberStr)) {
                    //Redis缓存中有数据
                    //即密码正确
                    UmsMember umsMemberFromCache = JSON.parseObject(umsMemberStr, UmsMember.class);
                    return umsMemberFromCache;
                }
            }
            // 链接redis失败（redis宕机了）或redis中未找到用户(用户第一次登录，还没存进过缓存)，开启数据库
            UmsMember umsMemberFromDb =loginFromDb(umsMember);
            if(umsMemberFromDb!=null){
                jedis.setex("user:" + umsMember.getPassword()+umsMember.getUsername() + ":info",60*60*24, JSON.toJSONString(umsMemberFromDb));
            }
            return umsMemberFromDb;
        }finally {
            jedis.close();
        }
    }

    @Override
    public void addUserToken(String token, String memberId) {


            Jedis jedis=redisUtil.getJedis();
            try{
                jedis.setex("user:"+memberId+":token",60*60*2,token);
            }finally {
                jedis.close();
            }
        }

    @Override
    public UmsMember addOauthUser(UmsMember umsMember) {
        userMapper.insertSelective(umsMember);

        return umsMember;
    }

    @Override
    public UmsMember checkOauthUser(UmsMember umsCheck) {
        UmsMember umsMember = userMapper.selectOne(umsCheck);
        return umsMember;
    }

    @Override
    public UmsMemberReceiveAddress getReceiveAddressById(String receiveAddressId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setId(receiveAddressId);
        UmsMemberReceiveAddress umsMemberReceiveAddress1 = umsMemberReceiveAddressMapper.selectOne(umsMemberReceiveAddress);
        return umsMemberReceiveAddress1;
    }

    private UmsMember loginFromDb(UmsMember umsMember) {

        List<UmsMember> umsMembers = userMapper.select(umsMember);
        //这里是为了防止有垃圾信息导致同一个用户名查出了多条记录(注册功能有bug就可能这样)

        if(umsMembers!=null){//取第一个值
            return umsMembers.get(0);
        }

        return null;

    }

}
