package com.giovanny.study.mapper;


import com.giovanny.study.config.mybatis.MyMapper;
import com.giovanny.study.entity.po.TtlProductInfoPo;

import java.util.List;
import java.util.Map;

/**
 * @author yangjun
 */
public interface TtlProductInfoMapper extends MyMapper<TtlProductInfoPo> {

    List<TtlProductInfoPo> listProduct(Map<String, Object> map);

}
