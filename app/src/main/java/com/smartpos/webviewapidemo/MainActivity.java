package com.smartpos.webviewapidemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.smartpos.webviewapidemo.common.Constants;
import com.smartpos.webviewapidemo.mvc.base.ActionCallback;
import com.smartpos.webviewapidemo.mvc.impl.ActionCallbackImpl;


public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ActionCallback actionCallback;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        webView = (WebView) findViewById(R.id.wv_webview);
        WebSettings settings = webView.getSettings();
        //allow webview to access files
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/main.html");
        webView.addJavascriptInterface(new JsCallInterface(this,actionCallback), "sdk");
    }

    private void initUI() {
        handler = new Handler(handlerCallback);
        actionCallback = new ActionCallbackImpl(this, handler);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(!JsCallInterface.isMain){
                webView.loadUrl("javascript:updateList(0,0)");
                actionCallback.sendResponse(getResources().getString(R.string.test_end));
            }else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public Handler.Callback handlerCallback = msg -> {
        switch (msg.what){
            case Constants.HANDLER_LOG_SUCCESS:
                loadSetTextClolor(msg.obj.toString(),"blue");
                break;
            case Constants.HANDLER_LOG_FAILED:
                loadSetTextClolor(msg.obj.toString(),"red");
                break;
            case Constants.HANDLER_LOG:
                loadSetTextClolor(msg.obj.toString(),"black");
                break;
        }
        return true;
    };

    private void loadSetTextClolor(String msg, String color){
        String url = "javascript:(function() { " +
                "var msg = '" + msg + "';" +
                "var color = '"+ color +"';" +
                "window.setTextColor(msg, color);" +
                "})()";
        webView.loadUrl(url);
    }

}
