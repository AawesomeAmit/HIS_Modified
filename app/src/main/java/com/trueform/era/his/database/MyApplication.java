package com.trueform.era.his.database;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class MyApplication extends Application {

    private static MyApplication mainApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        DatabaseController.openDataBase(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    public static synchronized MyApplication getInstance() {
        return mainApplication;
    }
}
