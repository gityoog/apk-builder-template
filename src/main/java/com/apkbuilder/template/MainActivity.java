package com.apkbuilder.template;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
  WebView webView;
  long lastBackPressTime = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    WebView.setWebContentsDebuggingEnabled(true);
    webView = findViewById(R.id.webview);
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        String scheme = Uri.parse(url).getScheme();
        return !("http".equals(scheme) || "https".equals(scheme));
      }
    });
    WebSettings webSettings = webView.getSettings();
    webSettings.setAllowFileAccess(true);
    webSettings.setJavaScriptEnabled(true);
    webSettings.setDomStorageEnabled(true);
    webSettings.setAllowUniversalAccessFromFileURLs(true);
    ContentResolver cr = getContentResolver();
    JSBridge jsBridge = JSBridge.factory(cr, webView);
    webView.addJavascriptInterface(jsBridge, "JSBridge");
    webView.loadUrl("file:///android_asset/main/index.html");
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (webView.canGoBack()) {
        webView.goBack();
        return true;
      } else {
        if (lastBackPressTime < System.currentTimeMillis() - 2000) {
          Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
          lastBackPressTime = System.currentTimeMillis();
          return false;
        } else {
          super.onBackPressed();
          return true;
        }
      }
    }
    return super.onKeyDown(keyCode, keyEvent);
  }
}