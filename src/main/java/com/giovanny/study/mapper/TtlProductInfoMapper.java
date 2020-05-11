package com.giovanny.study.mapper;



import com.giovanny.study.config.mybatis.MyMapper;
import com.giovanny.study.entity.po.TtlProductInfoPo;

import java.util.List;
import java.util.Map;

/**
 * @author kundy
 * @create 2019/2/16 10:42 AM
 */
public interface TtlProductInfoMapper extends MyMapper<TtlProductInfoPo> {

    List<TtlProductInfoPo> listProduct(Map<String, Object> map);

}
