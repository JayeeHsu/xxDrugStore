package com.xiaoxiang.xxdrugstore.service;

import com.xiaoxiang.xxdrugstore.bean.PmsBaseAttrInfo;
import com.xiaoxiang.xxdrugstore.bean.PmsBaseAttrValue;
import com.xiaoxiang.xxdrugstore.bean.PmsBaseSaleAttr;

import java.util.List;
import java.util.Set;

public interface AttrService {
    List<PmsBaseSaleAttr> baseSaleAttrList();

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet);
}
