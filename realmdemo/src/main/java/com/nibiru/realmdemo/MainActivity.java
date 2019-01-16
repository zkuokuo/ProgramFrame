package com.nibiru.realmdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nibiru.realmdemo.model.NewUser;
import com.nibiru.realmdemo.model.Person;
import com.nibiru.realmdemo.model.User;

import io.realm.Realm;

public class MainActivity extends Activity implements View.OnClickListener {

    private RealmHelper mRealmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = (Button) findViewById(R.id.bt1);
        Button btn2 = (Button) findViewById(R.id.bt2);
        Button btn3 = (Button) findViewById(R.id.bt3);
        Button btn4 = (Button) findViewById(R.id.bt4);
        Button btn5 = (Button) findViewById(R.id.bt5);
        Button btn6 = (Button) findViewById(R.id.bt6);
        Button btn7 = (Button) findViewById(R.id.bt7);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        mRealmHelper = RealmHelper.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.close();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.bt1:
                User user = new User();
                user.setName("校长");
                user.setAge(23);
                mRealmHelper.add(user);
                break;
            case R.id.bt2:
                User user1 = new User();
                user1.setName("小李");
                user1.setAge(24);
                mRealmHelper.addTransaction(user1);
                break;
            case R.id.bt3:
                NewUser newUser = new NewUser();
                newUser.setAge(26);
                newUser.setId(1);
                newUser.setName("小红");
                mRealmHelper.addhasPrimkey(newUser);
                break;
            case R.id.bt4:
                User user2 = new User();
                user2.setName("小王");
                user2.setAge(25);
                mRealmHelper.addNoPrimkey(user2);
                break;
            case R.id.bt5:
                User user3 = new User();
                user3.setName("小黑");
                mRealmHelper.addAsync(user3);
                break;
            case R.id.bt6:
                mRealmHelper.deleteAll();
                break;
            case R.id.bt7:
                User user7 = new User();
                user7.setName("校长");
                user7.setAge(28);
                mRealmHelper.deleteUser(user7);
                Person person=new Person();
                person.setName("zkk");
                mRealmHelper.addperson(person);
                break;
        }
    }
}
