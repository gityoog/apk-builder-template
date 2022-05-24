package com.apkbuilder.template;

import java.util.function.Supplier;

import android.content.ContentResolver;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.provider.Settings;
import android.util.Log;

public class JSBridge {
  ContentResolver cr;
  WebView webView;

  static JSBridge factory(ContentResolver cr, WebView webView) {
    JSBridge bridge = new JSBridge();
    bridge.cr = cr;
    bridge.webView = webView;
    return bridge;
  }

  JSBridge() {

  }

  private <S> S exWrap(Supplier<? extends S> supplier) {
    try {
      return supplier.get();
    } catch (Exception e) {
      log(e.toString());
      return null;
    }
  }

  private void log(String text) {
    webView.post(new Runnable() {
      @Override
      public void run() {
        webView.loadUrl("javascript:log('" + text + "')");
      }
    });
  }

  @JavascriptInterface
  public void test() {
    Log.i("YOOG", "run test");
    throw new RuntimeException("test2");
  }

  @JavascriptInterface
  public boolean isAdbEnabled() {
    return Settings.Global.getInt(cr, Settings.Global.ADB_ENABLED, -1) == 1;
  }

  @JavascriptInterface
  public void setAdbEnabled(boolean enabled) {
    exWrap(() -> Settings.Global.putInt(cr, Settings.Global.ADB_ENABLED, enabled ? 1 : 0));
  }

  @JavascriptInterface
  public boolean isWifiAdbEnabled() {
    return !"null".equals(SystemProperties.get("service.adb.tcp.port", "null"));
  }

  @JavascriptInterface
  public void setWifiAdbEnabled(boolean enabled) {
    exWrap(() -> SystemProperties.set("service.adb.tcp.port", enabled ? "5555" : ""));
    if (isAdbEnabled()) {
      setAdbEnabled(false);
      new Handler().postDelayed(new Runnable() {
        public void run() {
          setAdbEnabled(true);
        }
      }, 100);
    }
  }
}
