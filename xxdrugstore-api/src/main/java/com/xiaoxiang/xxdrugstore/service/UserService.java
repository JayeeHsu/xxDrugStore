package com.xiaoxiang.xxdrugstore.service;
import com.xiaoxiang.xxdrugstore.bean.UmsMember;
import com.xiaoxiang.xxdrugstore.bean.UmsMemberReceiveAddress;

import java.util.Date;
import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);

    int addReceiveAddress(String memberId, String name, String phoneNumber, int defaultStatus, String postCode, String province, String city, String region, String detailAddress);

    int deleteReceiveAddress(String memberId, String name, String phoneNumber, int defaultStatus, String postCode, String province, String city, String region, String detailAddress);

    int deleteReceiveAddressById(String id);

    int updateReceiveAddress(String id, String memberId, String name, String phoneNumber, int defaultStatus, String postCode, String province, String city, String region, String detailAddress);

    int addUser(//UmsMember umsMember
                String memberLevelId,
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
                int historyIntegration );

    int deleteUserById(String id);

    int updateUser(//UmsMember umsMember
            String id, String memberLevelId, String username, String password, String nickname, String phone, String status, Date createTime, String icon, int gender, Date birthday, String city, String job, String personalizedSignature, int source_type, int integration, int growth, int luckeyCount, int historyIntegration
    );

    UmsMember getUserByUserName(String userName);
}
