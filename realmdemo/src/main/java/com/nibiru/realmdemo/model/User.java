package com.nibiru.realmdemo.model;

import io.realm.RealmObject;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/16
 * 描述:
 */
public class User extends RealmObject {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
