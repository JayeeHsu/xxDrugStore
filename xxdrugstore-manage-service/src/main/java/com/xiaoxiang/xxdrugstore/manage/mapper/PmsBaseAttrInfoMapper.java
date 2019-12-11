package com.xiaoxiang.xxdrugstore.manage.mapper;

import com.xiaoxiang.xxdrugstore.bean.PmsBaseAttrInfo;
import com.xiaoxiang.xxdrugstore.bean.PmsBaseAttrValue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PmsBaseAttrInfoMapper extends Mapper<PmsBaseAttrInfo> {
    List<PmsBaseAttrInfo> selectAttrValueListByValueId(@Param("valueIdStr") String valueIdStr);
}
