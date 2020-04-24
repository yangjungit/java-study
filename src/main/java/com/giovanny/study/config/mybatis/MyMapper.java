package com.giovanny.study.config.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * @packageName: com.example.demo1.config.mybatis
 * @className: BaseMapper
 * @description: BaseMapper 这个mapper不能被扫描到
 * @author: YangJun
 * @date: 2020/4/14 16:36
 * @version: v1.0
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}