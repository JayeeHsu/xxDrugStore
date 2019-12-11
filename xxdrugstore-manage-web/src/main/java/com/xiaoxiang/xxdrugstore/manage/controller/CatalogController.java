package com.xiaoxiang.xxdrugstore.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaoxiang.xxdrugstore.bean.PmsBaseCatalog1;
import com.xiaoxiang.xxdrugstore.bean.PmsBaseCatalog2;
import com.xiaoxiang.xxdrugstore.bean.PmsBaseCatalog3;
import com.xiaoxiang.xxdrugstore.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin  //允许跨域请求 相当于给自己做了一个安全的自我介绍  （请求头（前端）和响应头（后端）至少有一方有“自我介绍”才能跨域请求）
public class CatalogController {

    @Reference
    CatalogService catalogService;

    @RequestMapping("getCatalog1")
    @ResponseBody  //表示返回json
    public List<PmsBaseCatalog1> getCatalog1(){
       List<PmsBaseCatalog1> catalog1s=catalogService.getCatalog1();
        return catalog1s;
    }

    @RequestMapping("getCatalog2")
    @ResponseBody  //表示返回json
    public List<PmsBaseCatalog2> getCatalog2( String catalog1Id){
        List<PmsBaseCatalog2> catalog1s=catalogService.getCatalog2(catalog1Id);
        return catalog1s;
    }

    @RequestMapping("getCatalog3")
    @ResponseBody  //表示返回json
    public List<PmsBaseCatalog3> getCatalog3( String catalog2Id){
        List<PmsBaseCatalog3> catalog1s=catalogService.getCatalog3(catalog2Id);
        return catalog1s;
    }


}
