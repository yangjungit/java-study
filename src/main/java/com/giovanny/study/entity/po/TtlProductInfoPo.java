package com.giovanny.study.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @packageName: com.giovanny.study.entity.po
 * @className: TtlProductInfoPo
 * @description: poi测试实体类
 * @author: YangJun
 * @date: 2020/5/7 15:04
 * @version: v1.0
 **/
@Data
@Table(name = "ttl_product_info")
public class TtlProductInfoPo {


    private Long id;
    private String productName;
    private Long categoryId;
    private String categoryName;
    private Long branchId;
    private String branchName;
    private Long shopId;
    private String shopName;
    private Double price;
    private Integer stock;
    private Integer salesNum;
    private String createTime;
    private String updateTime;
    private Byte isDel;

}
