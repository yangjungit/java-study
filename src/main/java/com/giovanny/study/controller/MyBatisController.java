package com.giovanny.study.controller;


import com.giovanny.study.entity.MyResponse;
import com.giovanny.study.entity.po.Uuu;
import com.giovanny.study.entity.vo.UuuVo;
import com.giovanny.study.mapper.UuuMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @packageName: com.example.demo1.controller
 * @className: MyBatisController  不想写service那些了
 * @description:
 * @author: YangJun
 * @date: 2020/4/14 17:01
 * @version: v1.0
 **/
@RestController
@Slf4j
public class MyBatisController {
    @Autowired
    UuuMapper uuuMapper;

    @RequestMapping(path = "/uuu/get/{id}", method = RequestMethod.GET)
    public MyResponse selectOne(@PathVariable Integer id) {
        Uuu uuu = uuuMapper.selectByPrimaryKey(id);
        return MyResponse.success(uuu);
    }


    @RequestMapping(path = "/uuu/get/list", method = RequestMethod.GET)
    public MyResponse selectList(@RequestBody UuuVo uuuVo) {
        log.info("param:[{}]", uuuVo);
        PageHelper.startPage(uuuVo.getPageNo(), uuuVo.getPageSize());
        List<Uuu> uuuList = uuuMapper.findBy(uuuVo);
        PageInfo<Uuu> pageInfo = new PageInfo<>(uuuList);
        MyResponse response = MyResponse.success(pageInfo);
        log.info("response:[{}]", response);
        return response;
    }

}
