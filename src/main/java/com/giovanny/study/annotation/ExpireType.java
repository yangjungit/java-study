package com.giovanny.study.annotation;

/**
 * @packageName: com.giovanny.study.annotation
 * @className: ExpireType
 * @description: 过期时间类型
 * @author: YangJun
 * @date: 2020/4/26 11:47
 * @version: v1.0
 **/
public enum ExpireType {


    /**
     * 秒
     */
    SECOND("SECOND"),
    /**
     * 毫秒
     */
    MILLISECOND("MILLISECOND");

    ExpireType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
