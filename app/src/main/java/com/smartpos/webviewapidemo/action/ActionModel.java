
package com.smartpos.webviewapidemo.action;

import com.smartpos.webviewapidemo.common.Constants;
import com.smartpos.webviewapidemo.mvc.base.AbstractAction;
import com.smartpos.webviewapidemo.mvc.base.ActionCallback;

import java.util.Map;

public class ActionModel extends AbstractAction {
    protected ActionCallback mCallback;

    void setParameter(ActionCallback callback) {
        if (mCallback == null) {
            this.mCallback = callback;
        }
    }

    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);
        setParameter(callback);
    }

    void sendSuccessLog(String log) {
//        StackTraceElement[] element = Thread.currentThread().getStackTrace();
//        for (StackTraceElement stackTraceElement : element) {
//            Log.e("DEBUG", "method: " + stackTraceElement.getMethodName());
//        }
        mCallback.sendResponse(Constants.HANDLER_LOG_SUCCESS, "\t\t"
                + Thread.currentThread().getStackTrace()[3].getMethodName() + "\t" + log);
    }
    
    void sendSuccessLog2(String log) {
//        StackTraceElement[] element = Thread.currentThread().getStackTrace();
//        for (StackTraceElement stackTraceElement : element) {
//            Log.e("DEBUG", "method: " + stackTraceElement.getMethodName());
//        }
        mCallback.sendResponse(Constants.HANDLER_LOG_SUCCESS, "\t\t"+ log);
    }

    void sendFailedLog(String log) {
//        StackTraceElement[] element = Thread.currentThread().getStackTrace();
//        for (StackTraceElement stackTraceElement : element) {
//            Log.e("DEBUG", "method: " + stackTraceElement.getMethodName());
//        }
        mCallback.sendResponse(Constants.HANDLER_LOG_FAILED, "\t\t"
                + Thread.currentThread().getStackTrace()[3].getMethodName() + "\t" + log);
    }
    
    void sendFailedOnlyLog(String log) {
        mCallback.sendResponse(Constants.HANDLER_LOG_FAILED, "\t\t" + log);
    }

    void sendNormalLog(String log) {
        mCallback.sendResponse(Constants.HANDLER_LOG, "\t\t" + log);
    }
}
