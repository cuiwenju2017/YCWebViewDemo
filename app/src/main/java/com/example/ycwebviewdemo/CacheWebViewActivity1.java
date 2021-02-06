package com.example.ycwebviewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ycwebviewdemo.traffic.TrafficUtils;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.ycbjie.webviewlib.cache.WebResponseAdapter;
import com.ycbjie.webviewlib.cache.WebViewCacheDelegate;
import com.ycbjie.webviewlib.client.JsX5WebViewClient;
import com.ycbjie.webviewlib.inter.InterWebListener;
import com.ycbjie.webviewlib.utils.X5WebUtils;
import com.ycbjie.webviewlib.view.X5WebView;
import com.ycbjie.webviewlib.widget.WebProgress;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/9/17
 *     desc  : webView页面
 *     revise: 暂时先用假数据替代
 * </pre>
 */
public class CacheWebViewActivity1 extends AppCompatActivity {

    private X5WebView mWebView;
    private WebProgress progress;
    private String url;
    private TrafficUtils instance;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initTraffic();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() ==
                KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mWebView.pageCanGoBack()) {
                //退出网页
                return mWebView.pageGoBack();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            //mWebView = null;
        }
        super.onDestroy();
        if (instance!=null){
            instance.stopCalculateNetSpeed();
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_cache);
        initData();
        initView();
    }


    public void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            url = intent.getStringExtra("url");
        }
    }

    public void initView() {
        initFindViewById();
        progress.show();
        progress.setColor(this.getResources().getColor(R.color.colorAccent),this.getResources().getColor(R.color.colorPrimaryDark));
        initWebView();
        initTraffic();
    }

    private void initFindViewById() {
        mWebView = findViewById(R.id.web_view);
        progress = findViewById(R.id.progress);
    }

    private void initWebView() {
        YcX5WebViewClient webViewClient = new YcX5WebViewClient(mWebView, this);
        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl("http://www.81ju.cn/");
        mWebView.getX5WebChromeClient().setWebListener(interWebListener);
        mWebView.getX5WebViewClient().setWebListener(interWebListener);
    }

    @SuppressLint("SetTextI18n")
    private void initTraffic() {
        instance = TrafficUtils.getInstance(this, new Handler());
        instance.startCalculateNetSpeed();
        handler.sendEmptyMessageDelayed(1,1000);
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

    private class YcX5WebViewClient extends JsX5WebViewClient {
        public YcX5WebViewClient(X5WebView webView, Context context) {
            super(webView, context);
        }
        /**
         * 此方法废弃于API21，调用于非UI线程，拦截资源请求并返回响应数据，返回null时WebView将继续加载资源
         * 注意：API21以下的AJAX请求会走onLoadResource，无法通过此方法拦截
         *
         * 其中 WebResourceRequest 封装了请求，WebResourceResponse 封装了响应
         * 封装了一个Web资源的响应信息，包含：响应数据流，编码，MIME类型，API21后添加了响应头，状态码与状态描述
         * @param webView                           view
         * @param s                                 s
         */
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
            WebResourceResponse request = WebViewCacheDelegate.getInstance().interceptRequest(s);
            return WebResponseAdapter.adapter(request);
        }

        /**
         * 此方法添加于API21，调用于非UI线程，拦截资源请求并返回数据，返回null时WebView将继续加载资源
         *
         * 其中 WebResourceRequest 封装了请求，WebResourceResponse 封装了响应
         * 封装了一个Web资源的响应信息，包含：响应数据流，编码，MIME类型，API21后添加了响应头，状态码与状态描述
         * @param webView                           view
         * @param webResourceRequest                request，添加于API21，封装了一个Web资源的请求信息，
         *                                          包含：请求地址，请求方法，请求头，是否主框架，是否用户点击，是否重定向
         * @return
         */
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
            WebResourceResponse request = WebViewCacheDelegate.getInstance().
                    interceptRequest(webResourceRequest);
            return WebResponseAdapter.adapter(request);
        }

    }
}
