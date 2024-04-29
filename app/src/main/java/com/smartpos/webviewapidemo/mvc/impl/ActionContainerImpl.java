
package com.smartpos.webviewapidemo.mvc.impl;

import android.content.Context;
import android.util.Log;

import com.smartpos.webviewapidemo.R;
import com.smartpos.webviewapidemo.entity.MainItem;
import com.smartpos.webviewapidemo.entity.SubItem;
import com.smartpos.webviewapidemo.mvc.base.ActionContainer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActionContainerImpl extends ActionContainer {
    private static final String TAG = "ActionContainerImpl";
    private Context context;

    public ActionContainerImpl(Context context) {
        this.context = context;
    }

    @Override
    public void initActions(String testItems) {
        try {
            JSONArray jsonArray = new JSONArray(testItems);
            List<MainItem> mainItems = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                MainItem mainItem = new MainItem();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mainItem.setDisplayNameCN(jsonObject.getString("displayNameCN"));
                mainItem.setDisplayNameEN(jsonObject.getString("displayNameEN"));
                mainItem.setCommand(jsonObject.getString("command"));
                JSONArray subArrays = jsonObject.getJSONArray("subItems");
                for (int pos = 0; pos < subArrays.length(); pos++){
                    SubItem subItem = new SubItem();
                    subItem.setDisplayNameCN(subArrays.getJSONObject(pos).getString("displayNameCN"));
                    subItem.setDisplayNameEN(subArrays.getJSONObject(pos).getString("displayNameEN"));
                    subItem.setCommand(subArrays.getJSONObject(pos).getString("command"));
                    subItem.setNeedTest(Boolean.parseBoolean(subArrays.getJSONObject(pos).getString("needTest")));
                    mainItem.addSubItem(subItem);
                }
                mainItems.add(mainItem);
            }
            for (MainItem mainItem : mainItems) {
                try {
                    String classPath = getClassPath(mainItem);
                    Class clazz = Class.forName(classPath);
                    addAction(mainItem.getCommand(), clazz, true);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Can't find this action");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String getClassPath(MainItem mainItem) {
        String classPath = null;
        if (mainItem.isUnique()) {
            classPath = mainItem.getPackageName();
        } else {
            classPath = context.getResources().getString(R.string.action_package_name)
                    + mainItem.getCommand();
        }
        return classPath;
    }

}
