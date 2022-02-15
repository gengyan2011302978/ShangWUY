package com.phjt.base.base;

import android.util.Log;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/18 10:51
 * description:
 */
public class Platform {

    public static final boolean DEPENDENCY_EVENTBUS;

    static {
        DEPENDENCY_EVENTBUS = findClassByClassName("org.greenrobot.eventbus.EventBus");
    }

    private static boolean findClassByClassName(String className) {


        boolean hasDependency;
        try {
            Class.forName(className);
            hasDependency = true;
            Log.e("EventBusManager","hasDependency");
        } catch (ClassNotFoundException e) {
            Log.e("EventBusManager","ClassNotFoundException");
            hasDependency = false;
        }
        return hasDependency;
    }
}
