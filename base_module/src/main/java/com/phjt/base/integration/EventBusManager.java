package com.phjt.base.integration;


import java.lang.reflect.Method;

import static com.phjt.base.base.Platform.DEPENDENCY_EVENTBUS;


/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/18 09:50
 * description:
 */
public class EventBusManager {

    public static final String TAG  ="EventBusManager";
    private static EventBusManager sInstance;

    public static EventBusManager getInstance() {
        if (sInstance == null) {
            synchronized (EventBusManager.class) {
                if (sInstance == null) {
                    sInstance = new EventBusManager();
                }
            }
        }
        return sInstance;
    }


    public void register(Object subscriber) {

        if (DEPENDENCY_EVENTBUS) {
            if (haveAnnotation(subscriber)) {
                org.greenrobot.eventbus.EventBus.getDefault().register(subscriber);
            }
        }


    }

    public void unregister(Object subscriber) {
        if (DEPENDENCY_EVENTBUS) {

            if (haveAnnotation(subscriber)) {

                org.greenrobot.eventbus.EventBus.getDefault().unregister(subscriber);
            }
        }
    }

    /**
     * 发送事件, 如果您在项目中同时依赖了两个 EventBus, 请自己使用想使用的 EventBus 的 Api 发送事件
     *
     * @param event 事件
     */
    public void post(Object event) {
        if (DEPENDENCY_EVENTBUS) {
            org.greenrobot.eventbus.EventBus.getDefault().post(event);
        }


    }

    public void postSticky(Object event) {
        if (DEPENDENCY_EVENTBUS) {
            org.greenrobot.eventbus.EventBus.getDefault().postSticky(event);
        }
    }

    public <T> T removeStickyEvent(Class<T> eventType) {
        if (DEPENDENCY_EVENTBUS) {
            return org.greenrobot.eventbus.EventBus.getDefault().removeStickyEvent(eventType);
        }
        return null;
    }

    /**
     * 清除订阅者和事件的缓存,
     */
    public void clear() {
        if (DEPENDENCY_EVENTBUS) {
            org.greenrobot.eventbus.EventBus.clearCaches();
        }
    }


    private boolean haveAnnotation(Object subscriber) {
        boolean skipSuperClasses = false;
        Class<?> clazz = subscriber.getClass();
        //查找类中符合注册要求的方法, 直到Object类
        while (clazz != null && !isSystemCalss(clazz.getName()) && !skipSuperClasses) {
            Method[] allMethods;
            try {
                allMethods = clazz.getDeclaredMethods();
            } catch (Throwable th) {
                try {
                    allMethods = clazz.getMethods();
                }catch (Throwable th2){
                    continue;
                }finally {
                    skipSuperClasses = true;
                }
            }
            for (int i = 0; i < allMethods.length; i++) {
                Method method = allMethods[i];
                Class<?>[] parameterTypes = method.getParameterTypes();
                //查看该方法是否含有 Subscribe 注解
                if (method.isAnnotationPresent(org.greenrobot.eventbus.Subscribe.class) && parameterTypes.length == 1) {
                    return true;
                }
            } //end for
            //获取父类, 以继续查找父类中符合要求的方法
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    private boolean isSystemCalss(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }
}
