
package com.smartpos.webviewapidemo.action;

import com.smartpos.webviewapidemo.mvc.base.ActionCallback;
import com.smartpos.webviewapidemo.util.SystemUtil;

import java.util.Map;

public class GetSnAction extends ActionModel {


    @Override
    protected void doBefore(Map<String, Object> param, ActionCallback callback) {
        super.doBefore(param, callback);

    }

    public void getSn(Map<String, Object> param, ActionCallback callback) {
        String sn = SystemUtil.getSn();
        sendSuccessLog("SN："+ sn);
    }
}
