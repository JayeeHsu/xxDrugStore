package com.xiaoxiang.xxdrugstore.bean;



import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @param
 * @return
 */
public class PmsSkuInfo implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    String id;
    @Column
    String productId;
    @Transient//临时属性，不保存到数据库
    String spuId;
    @Column
    BigDecimal price;
    @Column
    String skuName;
    @Column
    BigDecimal weight;
    @Column
    String skuDesc;
    @Column
    String catalog3Id;
    @Column
    String skuDefaultImg;
    @Transient
    List<PmsSkuImage> skuImageList;
    @Transient
    List<PmsSkuAttrValue> skuAttrValueList;
    @Transient
    List<PmsSkuSaleAttrValue> skuSaleAttrValueList;
    @Column
    String brandName;
    @Column
    String manufacturer;
    @Column
    String periodOfValidity;
    @Column
    String certificate;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPeriodOfValidity() {
        return periodOfValidity;
    }

    public void setPeriodOfValidity(String periodOfValidity) {
        this.periodOfValidity = periodOfValidity;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }




    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<PmsSkuImage> getSkuImageList() {
        return skuImageList;
    }

    public void setSkuImageList(List<PmsSkuImage> skuImageList) {
        this.skuImageList = skuImageList;
    }

    public List<PmsSkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<PmsSkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    public List<PmsSkuSaleAttrValue> getSkuSaleAttrValueList() {
        return skuSaleAttrValueList;
    }

    public void setSkuSaleAttrValueList(List<PmsSkuSaleAttrValue> skuSaleAttrValueList) {
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }
}
