package com.lyg.tools.blog.editblog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lyg.tools.R;

public class EditBlogActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blog);
        webView = findViewById(R.id.editBlogWeb);
        initWebView();
    }

    private void initWebView(){
        WebViewClient client = new WebViewClient();

        // 清除缓存
//        webView.clearCache(true);
        //设置Web视图
        webView.setWebViewClient(client);
        // 在js注册点击监听回调
        webView.requestFocus();
        // 存在于Android4.4以下
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        // 存在于Android4.4以下
        webView.removeJavascriptInterface("accessibility");
        // 存在于Android4.4以下
        webView.removeJavascriptInterface("accessibilityTraversal");

        WebSettings web = webView.getSettings();
        // 打开 WebView 的 storage 功能，这样 JS 的 localStorage,sessionStorage 对象才可以使用
        web.setDomStorageEnabled(true);
        //设置WebView属性，能够执行Javascript脚本
        web.setJavaScriptEnabled(true);
        //和setUseWideViewPort(true)一起解决网页自适应问题
        web.setLoadWithOverviewMode(true);
        //是否显示缩放按钮，默认false
        web.setBuiltInZoomControls(false);
        //是否可以缩放，默认true
        web.setSupportZoom(false);
        //设置文本的缩放倍数，默认为 100
//        web.textZoom = 2
        //设置此属性，可任意比例缩放。大视图模式
        web.setUseWideViewPort(false);
        //设置 缓存模式
        web.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置是否允许访问WebView内部文件
        web.setAllowFileAccess(false);
        //在不需要通过 file uri 加载的 Javascript 读取其他的本地文件的应用中建议设置禁止权限
        web.setAllowFileAccessFromFileURLs(false);
        //在不需要通过 file uri 加载的 Javascript 读取其他源的应用中建议设置禁止权限，这里说的源包括其他文件、https、http 等多样的源
        web.setAllowUniversalAccessFromFileURLs(false);
        //设置 JS 是否可以打开 WebView 新窗口
        web.setJavaScriptCanOpenWindowsAutomatically(false);
        //WebView 是否支持多窗口，如果设置为 true，需要重写  WebChromeClient#onCreateWindow(WebView, boolean, boolean, Message) 函数，默认为 false
        web.setSupportMultipleWindows(false);
        //这个属性用来设置 WebView 是否能够加载图片资源，需要注意的是，这个方法会控制所有图片，包括那些使用 data URI 协议嵌入的图片。
        // 使用 setBlockNetworkImage(boolean) 方法来控制仅仅加载使用网络 URI 协议的图片。
        // 需要提到的一点是,如果这个设置从 false 变为 true 之后，所有被内容引用的正在显示的 WebView 图片资源都会自动加载，该标识默认值为 true。
        web.setLoadsImagesAutomatically(true);
        //设置是否启动 database storage API 功能，默认值为 false
        web.setDatabaseEnabled(false);
        //打开 WebView 的 LBS 功能，这样 JS 的 geolocation 对象才可以使用，和setGeolocationDatabasePath一起使用
        web.setGeolocationEnabled(false);
        // 设置保存位置信息的数据库路径，和setGeolocationEnabled一起使用
//        web.setGeolocationDatabasePath(this.applicationContext.getDir("database", Context.MODE_PRIVATE).path);
        //设置是否打开 WebView 表单数据的保存功能
//        web.saveFormData = false;
        // 自动保存密码 废弃于API18
//        web.savePassword = false
        //设置是否 WebView 支持 “viewport” 的 HTML meta tag，这个标识是用来屏幕自适应的，当这个标识设置为 false 时，
        //页面布局的宽度被一直设置为 CSS 中控制的 WebView 的宽度；如果设置为 true 并且页面含有 viewport meta tag，那么
        //被这个 tag 声明的宽度将会被使用，如果页面没有这个 tag 或者没有提供一个宽度，那么一个宽型 viewport 将会被使用。
        web.setUseWideViewPort(true);
        //设置 WebView 的字体，可以通过这个函数，改变 WebView 的字体，默认字体为 "sans-serif"
//        web.standardFontFamily = ""
        //设置 WebView 字体的大小，默认大小为 16
//        web.defaultFontSize = 20
        //设置 WebView 支持的最小字体大小，默认为 8
//        web.minimumFontSize = 12
        // 自动播放视频
        web.setMediaPlaybackRequiresUserGesture(false);

        //加载需要显示的网页
        webView.loadUrl("http://lyg.cool/blogmanage/login");
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }

    }
}
