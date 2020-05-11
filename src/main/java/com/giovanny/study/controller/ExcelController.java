package com.giovanny.study.controller;

import com.giovanny.study.entity.MyResponse;
import com.giovanny.study.entity.po.TtlProductInfoPo;
import com.giovanny.study.service.TtlProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @packageName: com.giovanny.study.controller
 * @className: ExcelController
 * @description:
 * @author: YangJun
 * @date: 2020/5/7 17:04
 * @version: v1.0
 **/
@RestController
public class ExcelController {

    @Autowired
    private TtlProductInfoService ttlProductInfoService;

    @RequestMapping(path = "/export/insert")
    public MyResponse insertN(Integer num) {
        ttlProductInfoService.insertN(num);
        return MyResponse.success();
    }

    @RequestMapping(path = "/export/select")
    public MyResponse findAll(HttpServletResponse response) {
        ttlProductInfoService.export(response,"TtlProductInfoPoAll");
        return MyResponse.success();
    }
}
