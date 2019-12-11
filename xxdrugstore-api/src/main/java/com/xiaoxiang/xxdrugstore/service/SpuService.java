package com.xiaoxiang.xxdrugstore.service;

import com.xiaoxiang.xxdrugstore.bean.PmsProductImage;
import com.xiaoxiang.xxdrugstore.bean.PmsProductInfo;
import com.xiaoxiang.xxdrugstore.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId,String skuId);
}
