package com.xiaoxiang.xxdrugstore.service;

import com.xiaoxiang.xxdrugstore.bean.PmsBaseCatalog1;
import com.xiaoxiang.xxdrugstore.bean.PmsBaseCatalog2;
import com.xiaoxiang.xxdrugstore.bean.PmsBaseCatalog3;

import java.util.List;

public interface CatalogService {
    List<PmsBaseCatalog1> getCatalog1();
    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);
    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);
}
