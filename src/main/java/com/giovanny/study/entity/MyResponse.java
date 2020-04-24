package com.giovanny.study.entity;

import lombok.Data;

/**
 * @packageName: com.example.demo1.entity
 * @className: MyResponse
 * @description: MyResponse 统一的返回对象
 * @author: YangJun
 * @date: 2020/4/14 17:02
 * @version: v1.0
 **/
@Data
public class MyResponse {
    private int code;
    private String message;
    private Object data;

    public MyResponse() {
    }

    public MyResponse(int code) {
        this.code = code;
    }

    public MyResponse(Object data) {
        this.code = 200;
        this.data = data;
    }

    public MyResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyResponse(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public MyResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static MyResponse success() {
        return new MyResponse(200);
    }

    public static MyResponse success(String message, Object data) {
        return new MyResponse(message, data);
    }

    public static MyResponse success(Object data) {
        return new MyResponse(data);
    }

    public static MyResponse failed(int code, String message) {
        return new MyResponse(code,message);
    }
}
