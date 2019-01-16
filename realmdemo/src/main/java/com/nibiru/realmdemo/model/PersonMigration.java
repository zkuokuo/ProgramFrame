package com.nibiru.realmdemo.model;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019/1/16
 * 描述:对数据库进行升级操作
 */
public class PersonMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 1 && newVersion == 2) {
            RealmObjectSchema person = schema.get("Person");
            //新增@Required的id
            person
//                    .addPrimaryKey("id")//添加一个主键并赋值
//                    .transform(new RealmObjectSchema.Function() {
//                        @Override
//                        public void apply(DynamicRealmObject obj) {
//                            obj.set("id", 2);//为id设置值
//                        }
//                    })
                    .addField("age", int.class)//添加一个字段并赋值
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("age", 25);
                        }
                    });
            oldVersion++;  //需要手动增加
        }
    }
}
