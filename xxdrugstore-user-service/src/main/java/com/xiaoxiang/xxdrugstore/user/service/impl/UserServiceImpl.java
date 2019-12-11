package com.xiaoxiang.xxdrugstore.user.service.impl;



import com.alibaba.dubbo.config.annotation.Service;
import com.xiaoxiang.xxdrugstore.bean.UmsMember;
import com.xiaoxiang.xxdrugstore.bean.UmsMemberReceiveAddress;
import com.xiaoxiang.xxdrugstore.service.UserService;
import com.xiaoxiang.xxdrugstore.user.mapper.UmsMemberReceiveAddressMapper;
import com.xiaoxiang.xxdrugstore.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Date;
import java.util.List;

@Service //dubbo的Service注解
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    //UmsMember↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    //添加用户
    @Override
    public int addUser(String memberLevelId,
                       String username,
                       String password,
                       String nickname,
                       String phone,
                       String status,
                       Date createTime,
                       String icon,
                       int gender,
                       Date birthday,
                       String city,
                       String job,
                       String personalizedSignature,
                       int source_type,
                       int integration,
                       int growth,
                       int luckeyCount,
                       int historyIntegration) {
        UmsMember umsMember = new UmsMember();
        umsMember.setMemberLevelId(memberLevelId);
        umsMember.setUsername(username);
        umsMember.setPassword(password);
        umsMember.setNickname(nickname);
        umsMember.setPhone(phone);
        umsMember.setStatus(status);
        umsMember.setCreateTime(createTime);
        umsMember.setIcon(icon);
        umsMember.setGender(gender);
        umsMember.setBirthday(birthday);
        umsMember.setCity(city);
        umsMember.setJob(job);
        umsMember.setPersonalizedSignature(personalizedSignature);
        umsMember.setSourceType(source_type);
        umsMember.setIntegration(integration);
        umsMember.setGrowth(growth);
        umsMember.setLuckeyCount(luckeyCount);
        umsMember.setHistoryIntegration(historyIntegration);

        int result = userMapper.insertSelective(umsMember);
        return result;
    }


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
    public int updateUser(//@RequestBody UmsMember umsMember
                           String id, String memberLevelId, String username, String password, String nickname, String phone, String status, Date createTime, String icon, int gender, Date birthday, String city, String job, String personalizedSignature, int source_type, int integration, int growth, int luckeyCount, int historyIntegration
        ) {
        UmsMember umsMember = new UmsMember();
        umsMember.setId(id);
        umsMember.setMemberLevelId(memberLevelId);
        umsMember.setUsername(username);
        umsMember.setPassword(password);
        umsMember.setNickname(nickname);
        umsMember.setPhone(phone);
        umsMember.setStatus(status);
        umsMember.setCreateTime(createTime);
        umsMember.setIcon(icon);
        umsMember.setGender(gender);
        umsMember.setBirthday(birthday);
        umsMember.setCity(city);
        umsMember.setJob(job);
        umsMember.setPersonalizedSignature(personalizedSignature);
        umsMember.setSourceType(source_type);
        umsMember.setIntegration(integration);
        umsMember.setGrowth(growth);
        umsMember.setLuckeyCount(luckeyCount);
        umsMember.setHistoryIntegration(historyIntegration);
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
    public int addReceiveAddress(String memberId, String name, String phoneNumber,
                                 int defaultStatus, String postCode, String province,
                                 String city, String region, String detailAddress) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        umsMemberReceiveAddress.setName(name);
        umsMemberReceiveAddress.setPhoneNumber(phoneNumber);
        umsMemberReceiveAddress.setDefaultStatus(defaultStatus);
        umsMemberReceiveAddress.setPostCode(postCode);
        umsMemberReceiveAddress.setProvince(province);
        umsMemberReceiveAddress.setCity(city);
        umsMemberReceiveAddress.setRegion(region);
        umsMemberReceiveAddress.setDetailAddress(detailAddress);
        int result = umsMemberReceiveAddressMapper.insertSelective(umsMemberReceiveAddress);
        //方法：int insert(T record);
        //
        //说明：保存一个实体，null的属性也会保存，不会使用数据库默认值
        //方法：int insertSelective(T record);
        //
        //说明：保存一个实体，null的属性不会保存，会使用数据库默认值
        return result;
    }


    //删除收货地址
    @Override
    public int deleteReceiveAddress(String memberId, String name, String phoneNumber, int defaultStatus, String postCode, String province, String city, String region, String detailAddress) {
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
    public int updateReceiveAddress(String id, String memberId, String name, String phoneNumber, int defaultStatus, String postCode, String province, String city, String region, String detailAddress) {
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
}
