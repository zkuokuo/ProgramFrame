package com.nibiru.realmdemo;

import android.util.Log;

import com.nibiru.realmdemo.model.Dog;
import com.nibiru.realmdemo.model.NewUser;
import com.nibiru.realmdemo.model.Person;
import com.nibiru.realmdemo.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2017/10/13
 * 描述:单例设计模式
 */

public class RealmHelper {
    private final static String TAG = Class.class.getSimpleName();
    private Realm mRealm = Realm.getDefaultInstance();
    private RealmAsyncTask mRealmAsyncTask;

    //私有构造方法,防止被实例化
    private RealmHelper() {

    }

    //此处使用一个内部类来维护单例
    private static class RealmUtilFactory {
        private static RealmHelper instance = new RealmHelper();
    }

    //获取实例
    public static RealmHelper getInstance() {
        return RealmUtilFactory.instance;
    }

    /*----------------- add添加对象的方法 --------------------*/

    /**
     * 这个是同步添加一个对象
     */
    public void add(final User user) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User object = realm.createObject(User.class);
                object.setName(user.getName());
                object.setAge(user.getAge());
            }
        });
    }

    /**
     * 这个方法使用事务的方法同步添加数据
     */
    public void addTransaction(User user) {
        mRealm.beginTransaction();
        User object = mRealm.createObject(User.class);
        object.setName(user.getName());
        object.setAge(user.getAge());
        mRealm.commitTransaction();
    }

    /**
     * 这个方法添加的对象是有主键的,如果没有主键会报异常
     */
    public void addhasPrimkey(final NewUser user) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });
    }

    /**
     * 这个方法添加的对象没有主键,否则将会报异常
     */
    public void addNoPrimkey(final User user) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(user);
            }
        });
    }

    /**
     * 采用的是异步进行添加方法
     */
    public void addAsync(final User user) {
        mRealmAsyncTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User object = realm.createObject(User.class);
                object.setName(user.getName());
                object.setAge(user.getAge());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //成功的回调
                Log.d(TAG, "onSuccess: ");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //失败的回调
                Log.d(TAG, "onError: ");
            }
        });
    }

    /**
     * 如果采用异步进行操作的时候,当activity或者scene被销毁,
     * 在onSuccess或OnError中执行Ui操作,将导致崩溃,所以需要在onstop中取消
     * 避免crash的发生
     */
    public void onStopAsync() {
        if (mRealmAsyncTask != null && !mRealmAsyncTask.isCancelled()) {
            mRealmAsyncTask.cancel();
        }
    }

    /**
     * 添加集合类
     */
    public void addperson(final Person p) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person object = mRealm.createObject(Person.class,1);
                object.setName(p.getName());

                Dog dog1 = mRealm.createObject(Dog.class);
                dog1.setName("二哈");
                dog1.setAge(5);
                object.getDogs().add(dog1);

                Dog dog2 = mRealm.createObject(Dog.class);
                dog2.setName("金毛");
                dog2.setAge(2);
                object.getDogs().add(dog2);

            }
        });
    }

    /*----------------- 将json转化成对象添加 --------------------*/

    /**
     * Realm 解析 JSON 时遵循如下规则：
     * <p>
     * 使用包含空值（null）的 JSON 创建对象：
     * <p>
     * 对于非必须（可为空值的属性），设置其值为 null；
     * <p>
     * 对于必须（不可为空值的属性），抛出异常；
     * <p>
     * 使用包含空值（null）的 JSON 更新对象：
     * <p>
     * 对于非必须（可为空值的属性），设置其值为 null；
     * <p>
     * 对于必须（不可为空值的属性），抛出异常；
     * <p>
     * 使用不包含对应属性的 JSON： * 该属性保持不变
     */
    public void addJson() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObjectFromJson(Dog.class, "{name:\"小狗\",age:3}");
            }
        });
    }

    public void addIOJsonData() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    FileInputStream is = new FileInputStream(new File("path_to_file"));
                    realm.createAllFromJson(Dog.class, is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*----------------- 删除的方法 --------------------*/

    /**
     * 删除指定位置的数据
     */
    public void deleteIndex(final int index) {
        final RealmResults<User> users = mRealm.where(User.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                users.deleteFromRealm(index);
            }
        });
    }

    /**
     * 删除数据库中第一个数据
     */
    public void deleteFirst() {
        final RealmResults<User> all = mRealm.where(User.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                all.deleteFirstFromRealm();
            }
        });
    }

    /**
     * 删除数据库中最后一个数据
     */
    public void deleteLast() {
        final RealmResults<User> all = mRealm.where(User.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                all.deleteLastFromRealm();
            }
        });
    }

    /**
     * 删除数据库中所有的数据
     */
    public void deleteAll() {
        final RealmResults<User> all = mRealm.where(User.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                all.deleteAllFromRealm();
            }
        });
    }

    /**
     * 删除指定的对象
     */
    public void deleteUser(User user) {
        final RealmResults<User> all = mRealm.where(User.class).equalTo("name", user.getName()).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                all.deleteAllFromRealm();
            }
        });
    }

    /*----------------- 查询方法 --------------------*/

    /**
     * 查询所有的User列表,同步查询
     * <p>
     * RealmResults虽然实现了List接口，不过有很多方法是不能用的。
     * 比如add、addAll、remove、clear等，调用后会直接抛异常。
     * 不过也不用当心误用这些方法，因为它们都被标记为 @Deprecated 了。
     */
    public RealmResults<User> findAll(User user) {
        RealmResults<User> all = mRealm.where(User.class).findAll();
        return all;
    }

    /**
     * 查询所有的User列表,异步查询
     */
    public RealmResults<User> findAllAsync(User user) {
        RealmResults<User> all = mRealm.where(User.class).findAllAsync();
        boolean loaded = all.isLoaded();
        if (loaded) {
            Log.d(TAG, "findAllAsync: ------说明查询已经完成");
        }
        all.addChangeListener(new RealmChangeListener<RealmResults<User>>() {
            @Override
            public void onChange(RealmResults<User> element) {

            }
        });
        return all;
    }

    /**
     * 条件查询
     */
    public void find() {
        //查询第一条数据
        User user2 = mRealm.where(User.class).findFirst();
        //根据单条件查询
        RealmResults<User> all = mRealm.where(User.class).equalTo("name", "校长").findAll();
        //多条件查询(并列关系)
        mRealm.where(User.class).equalTo("name", "小明").equalTo("dags.name", "二哈").findAll();
        //多条件查询(或者关系)
        mRealm.where(User.class).equalTo("name", "校长").or().equalTo("age", 25).findAll();
        //对查询结果排序 Sort.ASCENDING(表示正序)   Sort.DESCENDING(表示倒序)
        mRealm.where(User.class).findAll().sort("age", Sort.DESCENDING);
        //对查询的结果进行聚合操作
        RealmResults<User> users = mRealm.where(User.class).findAll();
        long age = users.sum("age").longValue();
        long age1 = users.min("age").longValue();
        long age2 = users.max("age").longValue();
        double age3 = users.average("age");
        int size = users.size();
    }

}
