package com.giovanny.study.entity.vo;

import lombok.Data;


/**
 * @packageName: com.example.demo1.entity.vo
 * @className: UuuVo
 * @description: UuuVo
 * @author: YangJun
 * @date: 2020/4/15 11:22
 * @version: v1.0
 **/
@Data
public class UuuVo{
    /**
     * 自增主键id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 分页单页记录数
     */
    private Integer pageSize;
}
