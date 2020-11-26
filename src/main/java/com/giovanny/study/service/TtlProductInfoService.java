package com.giovanny.study.service;

import com.giovanny.study.entity.po.TtlProductInfoPo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * @author yangjun
 */
public interface TtlProductInfoService {

    List<TtlProductInfoPo> listProduct(Map<String, Object> map);

    void export(HttpServletResponse response, String fileName);

    void insertN(Integer num);


}
