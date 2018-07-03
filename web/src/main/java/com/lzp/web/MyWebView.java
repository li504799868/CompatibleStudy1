package com.lzp.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.jetbrains.annotations.NotNull;


/**
 * @author li.zhipeng
 * @date 2017/9/14
 * <p>
 * 自定义WebView
 */

public class MyWebView extends RelativeLayout {

    private WebView webView;

    private ProgressBar loadingProgressBar;

    private static final int MAX_PROGRESS = 100;

    private OnReceivedTitleListener listener;

    public MyWebView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_web, this);
        webView = findViewById(R.id.web_view);
        loadingProgressBar = findViewById(R.id.loading_process_dialog_progressBar);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        loadingProgressBar.setMax(10000);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//接受证书
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                String scheme = uri.getScheme();
                // 是否打电话
                if (url.startsWith("tel:")) {

                    return true;
                }
                // 重新加载url
                else {
                    loadUrl(url);
                }
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (listener != null) {
                    listener.onReceivedTitle(title);
                }
            }

            /**
             * 经常用来重写Web自带的alter弹窗，但是请注意，这样bug，部分手机多次点击弹出后，无响应
             * */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return false;
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress * MAX_PROGRESS == loadingProgressBar.getMax()) {
                    loadingProgressBar.setVisibility(View.GONE);
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                loadingProgressBar.setProgress(progress * MAX_PROGRESS);
            }

        });
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置网页的宽度等于手机屏幕的宽度
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        // 允许android 5.0以上加载https和http的混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 是否加载缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    public void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(appendClientParams(url));
        }
    }

    public boolean canGoBack() {
        return webView.canGoBack();
    }

    public void goBack() {
        webView.goBack();
    }

    public void destroy(){
        webView.destroy();
    }

    /**
     * 注入JS对象
     */
    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    public void addJavascriptInterface(@NotNull Object object, String name) {
        if (webView != null) {
            webView.addJavascriptInterface(object, name);
        }
    }

    public void setListener(OnReceivedTitleListener listener) {
        this.listener = listener;
    }

    public interface OnReceivedTitleListener {
        void onReceivedTitle(String title);
    }

    /**
     * 为url链接拼接客户端参数
     *
     * @param url 链接
     */
    private String appendClientParams(String url) {
        return url;
    }


}
