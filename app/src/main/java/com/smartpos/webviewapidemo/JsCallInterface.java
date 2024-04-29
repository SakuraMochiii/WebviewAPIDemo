package com.smartpos.webviewapidemo;

import com.smartpos.webviewapidemo.common.Constants;
import com.smartpos.webviewapidemo.mvc.base.ActionCallback;
import com.smartpos.webviewapidemo.mvc.base.ActionManager;
import com.smartpos.webviewapidemo.mvc.impl.ActionContainerImpl;
import com.smartpos.webviewapidemo.util.LanguageHelper;
import com.smartpos.webviewapidemo.util.TerminalHelper;

import java.util.HashMap;
import java.util.Map;

public class JsCallInterface {
    private MainActivity mContext;
    public static boolean isMain = true;
    private ActionCallback callBack;
    private Map<String, Object> testParameters;

    public JsCallInterface(MainActivity mContext, ActionCallback handleCallBack) {
        this.mContext = mContext;
        this.callBack = handleCallBack;
        testParameters = new HashMap<String, Object>();
    }

    @android.webkit.JavascriptInterface
    public void initAction(String testDatas){
        ActionManager.initActionContainer(new ActionContainerImpl(mContext),testDatas);
    }

    @android.webkit.JavascriptInterface
    public void isMain(boolean isMain){
        this.isMain = isMain;
    }

    @android.webkit.JavascriptInterface
    public int getLanguage(){
       return LanguageHelper.getLanguageType(mContext);
    }

    @android.webkit.JavascriptInterface
    public String getXml(){
        String xmlName;
       int terminalType = TerminalHelper.getTerminalType();
        switch (terminalType) {
            case TerminalHelper.TERMINAL_TYPE_WIZARHAND_Q1:
                xmlName = "wizarhand_q1.xml";
                break;
            case TerminalHelper.TERMINAL_TYPE_WIZARPAD_1:
                xmlName = "wizarpad_1.xml";
                break;
            case TerminalHelper.TERMINAL_TYPE_WIZARHAND_M0:
                xmlName = "wizarhand_m0.xml";
                break;
            default:
                xmlName = "wizarpos_1.xml";
                break;
        }
        return xmlName;
    }

    @android.webkit.JavascriptInterface
    public void javaSDKDevice(String mainItem, String subItem) {
        testParameters.clear();
        testParameters.put(Constants.MAIN_ITEM, mainItem);
        testParameters.put(Constants.SUB_ITEM, subItem);
        ActionManager.doSubmit(mainItem + "/" + subItem,
                mContext, testParameters, callBack);
    }
    
}
