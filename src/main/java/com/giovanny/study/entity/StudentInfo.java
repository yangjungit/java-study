package com.giovanny.study.entity;

import lombok.Data;

/**
 * @packageName: com.giovanny.study.entity
 * @className: StudentInfo
 * @description: 测试流式处理的类
 * @author: YangJun
 * @date: 2020/5/12 9:30
 * @version: v1.0
 **/
@Data
public class StudentInfo {
    /**
     * 学号
     */
    private long id;

    private String name;

    private int age;

    /**
     * 年级
     */
    private int grade;

    /**
     * 专业
     */
    private String major;

    /**
     * 学校
     */
    private String school;

    public StudentInfo() {
    }

    public StudentInfo(long id, String name, int age, int grade, String major, String school) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.major = major;
        this.school = school;
    }
}
