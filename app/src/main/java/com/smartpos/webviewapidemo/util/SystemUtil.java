package com.smartpos.webviewapidemo.util;

import com.cloudpos.sdk.common.SystemProperties;

public class SystemUtil {
    public static String getSn() {
        return SystemProperties.get("ro.serialno", "null");
    }
}
