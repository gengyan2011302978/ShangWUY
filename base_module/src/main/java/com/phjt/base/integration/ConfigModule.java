package com.phjt.base.integration;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.phjt.base.base.delegate.AppLifecycles;
import com.phjt.base.di.module.GlobalConfigModule;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 10:42
 * description:
 * <p>
 * 模块初始化分析：
 * 在组件化之前，我们常常把项目中需要在启动时完成初始化的行为放在自定义的 Application 中，根据经验
 * 初始化行为可以分为两大类：
 * 1. 业务相关初始化：例如 服务器推送长连接建立、数据库准备
 * 2. 与业务无关的技术组件初始化：例如 日志工具、统计工具，性能工具，奔溃收集，兼容性方案。
 *
 *
 *
 */
public interface ConfigModule {
    /**
     * 配置各模块在以 app 全量编译时，模块的初始化顺序
     *
     * @return
     */
    int configModulePriority();

    /**
     * 使用 {@link GlobalConfigModule.Builder} 给框架配置一些配置参数
     *
     * @param context {@link Context}
     * @param builder {@link GlobalConfigModule.Builder}
     */
    void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder);

    /**
     * 使用 {@link AppLifecycles} 在 {@link Application} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Application} 的生命周期容器, 可向框架中添加多个 {@link Application} 的生命周期类
     */
    void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> lifecycles);

    /**
     * 使用 {@link Application.ActivityLifecycleCallbacks} 在 {@link Activity} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Activity} 的生命周期容器, 可向框架中添加多个 {@link Activity} 的生命周期类
     */
    void injectActivityLifecycle(@NonNull Context context,
                                 @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles);

    /**
     * 使用 {@link FragmentManager.FragmentLifecycleCallbacks} 在 {@link Fragment} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycles {@link Fragment} 的生命周期容器, 可向框架中添加多个 {@link Fragment} 的生命周期类
     */
    void injectFragmentLifecycle(@NonNull Context context,
                                 @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles);
}
