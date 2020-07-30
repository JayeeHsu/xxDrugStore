package com.xiaoxiang.xxware.mapper;



import com.xiaoxiang.xxware.bean.WmsWareInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @param
 * @return
 */
public interface WmsWareInfoMapper extends Mapper<WmsWareInfo> {


    public List<WmsWareInfo> selectWareInfoBySkuid(String skuid);



}
