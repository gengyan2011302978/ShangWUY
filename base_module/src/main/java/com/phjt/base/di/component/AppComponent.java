package com.phjt.base.di.component;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.phjt.base.base.delegate.AppDelegate;
import com.phjt.base.di.module.AppModule;
import com.phjt.base.di.module.ClientModule;
import com.phjt.base.di.module.GlobalConfigModule;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.imageloader.ImageLoader;
import com.phjt.base.integration.AppManager;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.integration.cache.Cache;


import java.io.File;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/22 11:42
 * description: 规律：
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {
    /**
     * 向外部提供 Application 对象
     *
     * @return Application
     */
    Application application();

    /**
     * 用于管理所有 {@link Activity}
     * 之前 {@link AppManager} 使用 Dagger 保证单例, 只能使用 {@link AppComponent#appManager()} 访问
     * 现在直接将 AppManager 独立为单例类, 可以直接通过静态方法 {@link AppManager#getAppManager()} 访问, 更加方便
     * 但为了不影响之前使用 {@link AppComponent#appManager()} 获取 {@link AppManager} 的项目, 所以暂时保留这种访问方式
     *
     * @return {@link AppManager}
     * @deprecated Use {@link AppManager#getAppManager()} instead
     */
    @Deprecated
    AppManager appManager();

    /**
     * 用于管理网络请求层, 以及数据缓存层
     *
     * @return {@link IRepositoryManager}
     */
    IRepositoryManager repositoryManager();

    /**
     * RxJava 错误处理管理类
     *
     * @return {@link RxErrorHandler}
     */
    RxErrorHandler rxErrorHandler();

    /**
     * 用于图片加载 在应用中能获取到{@link Context} 的地方都可获取此对象
     *
     * @return {@link ImageLoader}
     */
    ImageLoader imageLoader();

    /**
     * 缓存文件根目录 (Glide 的缓存都已经作为子文件夹放在这个根目录下), 应该将所有缓存都统一放到这个根目录下
     * 便于管理和清理, 可在 {@link ConfigModule#applyOptions(Context, GlobalConfigModule.Builder)}  种配置
     *
     * @return {@link File}
     */
    File cacheFile();

    /**
     * Json 序列化库
     *
     * @return {@link Gson}
     */
    Gson gson();

    /**
     * 网络请求框架
     *
     * @return {@link OkHttpClient}
     */
    OkHttpClient okHttpClient();

    /**
     * 用来存取一些整个 App 公用的数据, 切勿大量存放大容量数据, 这里的存放的数据和 {@link Application} 的生命周期一致
     *
     * @return {@link Cache}
     */
    Cache<String, Object> extras();

    /**
     * 用于创建框架所需缓存对象的工厂
     *
     * @return {@link Cache.Factory}
     */
    Cache.Factory cacheFactory();

    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     *
     * @return {@link ExecutorService}
     */
    ExecutorService executorService();

    /**
     * 将目标类所依赖的对象注入到目标类中
     *
     * @param delegate AppDelegate 要注入的目标类
     */
    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }
}
