package com.nibiru.realmdemo.model;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/16
 * 描述:
 */

@RealmClass
public class NewUser implements RealmModel {
    @PrimaryKey
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
