package com.example.ycwebviewdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_2_1).setOnClickListener(this);
        findViewById(R.id.tv_2_3).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        findViewById(R.id.tv_8).setOnClickListener(this);
        findViewById(R.id.tv_11).setOnClickListener(this);
        findViewById(R.id.tv_13).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_2_1:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case R.id.tv_2_3:
                startActivity(new Intent(this, CacheWebViewActivity1.class));
                break;
            case R.id.tv_5:
                startActivity(new Intent(this, FiveActivity.class));
                break;
            case R.id.tv_8:
                startActivity(new Intent(this, EightActivity.class));
                break;
            case R.id.tv_11:
                openLink(this, "http://www.81ju.cn/");
                break;
            case R.id.tv_13:
                startActivity(new Intent(this, ScrollViewActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 使用浏览器打开链接
     */
    public void openLink(Context context, String content) {
        if (!TextUtils.isEmpty(content) && content.startsWith("http")) {
            Uri issuesUrl = Uri.parse(content);
            Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
            context.startActivity(intent);
        }
    }

}