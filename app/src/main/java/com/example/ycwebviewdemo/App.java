package com.example.ycwebviewdemo;

import android.app.Application;

import com.ycbjie.webviewlib.utils.X5WebUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化WebViewLib
        X5WebUtils.init(this);
    }
}
