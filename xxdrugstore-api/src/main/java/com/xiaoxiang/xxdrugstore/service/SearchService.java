package com.xiaoxiang.xxdrugstore.service;

import com.xiaoxiang.xxdrugstore.bean.PmsSearchParam;
import com.xiaoxiang.xxdrugstore.bean.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);


}
