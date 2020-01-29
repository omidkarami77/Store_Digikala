package com.example.store_digikala;

import android.app.Application;



import com.example.store_digikala.model.address.DaoMaster;
import com.example.store_digikala.model.address.DaoSession;


import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private static App app;
    private DaoSession daoSession;

    public static App getApp() {
        return app;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper myDevOpenHelper =new MyDevOpenHelper(this,"avatar.db");
        Database db=myDevOpenHelper.getWritableDb();

        daoSession=new DaoMaster(db).newSession();
        app=this;


    }
}
