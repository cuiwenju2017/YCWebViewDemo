package com.example.ycwebviewdemo;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ycbjie.webviewlib.inter.InterWebListener;
import com.ycbjie.webviewlib.inter.VideoWebListener;
import com.ycbjie.webviewlib.utils.X5WebUtils;
import com.ycbjie.webviewlib.view.X5WebView;
import com.ycbjie.webviewlib.widget.WebProgress;


public class SecondActivity extends AppCompatActivity {

    private X5WebView webView;
    private WebProgress progress;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.pageCanGoBack()) {
                //退出网页
                return webView.pageGoBack();
            } else {
                handleFinish();
            }
        }
        return false;
    }

    public void handleFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        try {
            if (webView != null) {
                webView.destroy();
                webView = null;
            }
        } catch (Exception e) {
            Log.e("X5WebViewActivity", e.getMessage());
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        webView = findViewById(R.id.web_view);
        progress = findViewById(R.id.progress);
        progress.show();
        progress.setColor(this.getResources().getColor(R.color.colorAccent),this.getResources().getColor(R.color.colorPrimaryDark));

        webView.loadUrl("http://www.81ju.cn/");
        webView.getX5WebChromeClient().setWebListener(interWebListener);
        webView.getX5WebViewClient().setWebListener(interWebListener);

        //设置是否自定义视频视图
        webView.setShowCustomVideo(false);

        webView.getX5WebChromeClient().setVideoWebListener(new VideoWebListener() {
            @Override
            public void showVideoFullView() {
                //视频全频播放时监听
            }

            @Override
            public void hindVideoFullView() {
                //隐藏全频播放，也就是正常播放视频
            }

            @Override
            public void showWebView() {
                //显示webView
            }

            @Override
            public void hindWebView() {
                //隐藏webView
            }
        });
    }


    private InterWebListener interWebListener = new InterWebListener() {
        @Override
        public void hindProgressBar() {
            progress.hide();
        }

        @Override
        public void showErrorView(@X5WebUtils.ErrorType int type) {
            switch (type){
                //没有网络
                case X5WebUtils.ErrorMode.NO_NET:
                    break;
                //404，网页无法打开
                case X5WebUtils.ErrorMode.STATE_404:

                    break;
                //onReceivedError，请求网络出现error
                case X5WebUtils.ErrorMode.RECEIVED_ERROR:

                    break;
                //在加载资源时通知主机应用程序发生SSL错误
                case X5WebUtils.ErrorMode.SSL_ERROR:

                    break;
                default:
                    break;
            }
        }

        @Override
        public void startProgress(int newProgress) {
            progress.setWebProgress(newProgress);
        }

        @Override
        public void showTitle(String title) {

        }
    };
}
