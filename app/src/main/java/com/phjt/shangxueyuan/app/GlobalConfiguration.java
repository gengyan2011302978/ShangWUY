package com.phjt.shangxueyuan.app;

import android.app.Application;
import android.content.Context;

import com.phjt.base.base.delegate.AppLifecycles;
import com.phjt.base.di.module.GlobalConfigModule;
import com.phjt.base.http.log.RequestInterceptor;
import com.phjt.base.integration.ConfigModule;
import com.phjt.imageloader.GlideImageLoaderStrategy;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.common.MyBusiness;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import me.jessyan.progressmanager.ProgressManager;

/**
 * @author : austen
 * company    : 普华
 * date       : 2019/3/27 17:16
 * description: 统一配置类 此类在 Manifest 文件中 配置
 * 通过反射将框架内部会进行配置分配
 */
public class GlobalConfiguration implements ConfigModule {

    @Override
    public int configModulePriority() {
        return 0;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder) {
        if (MyBusiness.isProductRelease()) {
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.baseurl(BuildConfig.HOST_URL)
                .imageLoaderStrategy(new GlideImageLoaderStrategy())
                .okhttpConfiguration((context1, builder1) -> {
                    //Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听
                    ProgressManager.getInstance().with(builder1);
//                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl.
//                    RetrofitUrlManager.getInstance().with(builder1);
                    builder1.connectTimeout(20, TimeUnit.SECONDS)
                            .callTimeout(20, TimeUnit.SECONDS);
                })
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl());

    }

    @Override
    public void injectAppLifecycle(@NonNull Context context,
                                   @NonNull List<AppLifecycles> lifecycles) {
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context,
                                        @NonNull List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(@NonNull Context context,
                                        @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
