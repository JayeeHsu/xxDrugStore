package com.xiaoxiang.xxdrugstore.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoxiang.xxdrugstore.bean.UmsMember;
import com.xiaoxiang.xxdrugstore.bean.UmsMemberReceiveAddress;
import com.xiaoxiang.xxdrugstore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin
public class  UserController {

    //@Autowired
    // 在同一个容器中才能用Autowired
    @Reference//所以用dubbo的Reference远程消费
    UserService userService;//这里需要在setting里吧Autowining for Bean Class关掉，否则会报错

    //TEST↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    @RequestMapping("index")
    @ResponseBody
    public String index() {
        return "hello";
    }
    //TEST↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    //UmsMember↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    //增加用户
    @RequestMapping("addUser")
    @ResponseBody

public String addUser(UmsMember umsMember){
//    String memberLevelId,
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
//                       String sourceType,
//                       int integration,
//                       int growth,
//                       int luckeyCount,
//                       int historyIntegration) {
        String result = userService.addUser(umsMember);
//                memberLevelId,
//                username,
//                password,
//                nickname,
//                phone,
//                status,
//                createTime,
//                icon,
//                gender,
//                birthday,
//                city,
//                job,
//                personalizedSignature,
//                sourceType,
//                integration,
//                growth,
//                luckeyCount,
//                historyIntegration);
        return result;
    }
    //通过主键id删除用户
    @RequestMapping("deleteUser")
    @ResponseBody
    public int deleteUserById(String id){
        int result=userService.deleteUserById(id);
        return result;
    }
    //修改用户
    @RequestMapping("updateUser")
    @ResponseBody
    public int updateUser(@RequestBody UmsMember umsMember
//                          String id,
//                          String memberLevelId,
//                          String username,
//                          String password,
//                          String nickname,
//                          String phone,
//                          int status,
//                          Date createTime,
//                          String icon,
//                          int gender,
//                          Date birthday,
//                          String city,
//                          String job,
//                          String personalizedSignature,
//                          int source_type,
//                          int integration,
//                          int growth,
//                          int luckeyCount,
//                          int historyIntegration
                          ){
        int result =userService.updateUser( umsMember
//                id,memberLevelId,
//                username,
//                password,
//                nickname,
//                phone,
//                status,
//                createTime,
//                icon,
//                gender,
//                birthday,
//                city,
//                job,
//                personalizedSignature,
//                source_type,
//                integration,
//                growth,
//                luckeyCount,
//                historyIntegration
        );
        return result;
    }

    //按username查询用户
    @RequestMapping("getUserByUserName")
    @ResponseBody
public UmsMember getUserByUserName(String userName){
        UmsMember umsMember=userService.getUserByUserName(userName);
        return umsMember;
    }

    //查询所有用户
    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMembers = userService.getAllUser();
        return umsMembers;
    }
    //UmsMember↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    //UmsMemberReceiveAddress↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    //添加收货地址
    @RequestMapping("addReceiveAddressByMemberId")
    @ResponseBody

    public String addUmsMemberReceiveAddress(UmsMemberReceiveAddress umsMemberReceiveAddress) {

        String result = userService.addReceiveAddress(umsMemberReceiveAddress);

        return result;
    }

    //删除收货地址
    @RequestMapping("deleteReceiveAddress")
    @ResponseBody

    public int deleteUmsMemberReceiveAddress(String memberId, String name, String phoneNumber, String defaultStatus, String postCode, String province, String city, String region, String detailAddress) {

        int result = userService.deleteReceiveAddress(memberId, name, phoneNumber, defaultStatus, postCode, province, city, region, detailAddress);

        return result;
    }

    //通过主键Id删除收货地址
    @RequestMapping("deleteReceiveAddressById")
    @ResponseBody
    public int deleteUmsMemberReceiveAddressById(String Id) {//加@RequestBody可以接json类型的参数
        int result = userService.deleteReceiveAddressById(Id);
        return result;
    }
    //修改收货地址

    @RequestMapping("updateReceiveAddress")
    @ResponseBody
    public int updateReceiveAddress(String Id, String memberId, String name, String phoneNumber, String defaultStatus, String postCode, String province, String city, String region, String detailAddress) {
        int result = userService.updateReceiveAddress(Id, memberId, name, phoneNumber, defaultStatus, postCode, province, city, region, detailAddress);
        return result;
    }

    //根据用户id查询收货地址
    @RequestMapping("getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {//加@RequestBody可以接json类型的参数

        List<UmsMemberReceiveAddress> UmsMemberReceiveAddresses = userService.getReceiveAddressByMemberId(memberId);

        return UmsMemberReceiveAddresses;
    }
//UmsMemberReceiveAddress↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑


}
