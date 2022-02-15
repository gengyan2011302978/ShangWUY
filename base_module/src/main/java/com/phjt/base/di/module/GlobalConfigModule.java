package com.phjt.base.di.module;

import android.app.Application;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.phjt.base.http.BaseUrl;
import com.phjt.base.http.GlobalHttpHandler;
import com.phjt.base.http.errorhandler.handler.listener.ResponseErrorListener;
import com.phjt.base.http.imageloader.BaseImageLoaderStrategy;
import com.phjt.base.http.log.DefaultFormatPrinter;
import com.phjt.base.http.log.FormatPrinter;
import com.phjt.base.http.log.RequestInterceptor;
import com.phjt.base.integration.cache.Cache;
import com.phjt.base.integration.cache.CacheType;
import com.phjt.base.integration.cache.IntelligentCache;
import com.phjt.base.integration.cache.LruCache;
import com.phjt.base.utils.DataHelper;
import com.phjt.base.utils.Preconditions;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.internal.Util;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/25 11:19
 * description:
 */
@Module
public class GlobalConfigModule {
    private HttpUrl mApiUrl;
    private BaseUrl mBaseUrl;
    private BaseImageLoaderStrategy mLoaderStrategy;
    private Cache.Factory mCacheFactory;
    private File mCacheFile;
    private ResponseErrorListener mErrorListener;
    private List<Interceptor> mInterceptors;
    private GlobalHttpHandler mHandler;
    private ClientModule.RetrofitConfiguration mRetrofitConfiguration;
    private ClientModule.OkhttpConfiguration mOkhttpConfiguration;
    private AppModule.GsonConfiguration mGsonConfiguration;
    private RequestInterceptor.Level mPrintHttpLogLevel;
    private FormatPrinter mFormatPrinter;
    private ExecutorService mExecutorService;

    public GlobalConfigModule(Builder builder) {
        this.mApiUrl = builder.apiUrl;
        this.mBaseUrl = builder.baseUrl;
        this.mLoaderStrategy = builder.loaderStrategy;
        this.mHandler = builder.handler;
        this.mOkhttpConfiguration = builder.okhttpConfiguration;
        this.mCacheFactory = builder.cacheFactory;
        this.mErrorListener = builder.responseErrorListener;
        this.mPrintHttpLogLevel = builder.printHttpLogLevel;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }

    /**
     * 提供 BaseUrl,默认使用 <"https://api.github.com/">
     */
    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        if (mBaseUrl != null) {
            HttpUrl httpUrl = mBaseUrl.url();
            if (httpUrl != null) {
                return httpUrl;
            }
        }
        return mApiUrl == null ? HttpUrl.parse("https://api.github.com/") : mApiUrl;
    }

    /**
     * 提供图片加载框架,默认使用 {@link Glide}
     */
    @Singleton
    @Provides
    @Nullable
    BaseImageLoaderStrategy provideImageLoaderStrategy() {
        return mLoaderStrategy;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.RetrofitConfiguration provideRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.OkhttpConfiguration provideOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    AppModule.GsonConfiguration provideGsonConfiguration() {
        return mGsonConfiguration;
    }

    @Singleton
    @Provides
    RequestInterceptor.Level providePrintHttpLogLevel() {
        return mPrintHttpLogLevel == null ? RequestInterceptor.Level.ALL : mPrintHttpLogLevel;
    }

    @Singleton
    @Provides
    FormatPrinter provideFormatPrinter() {
        return mFormatPrinter == null ? new DefaultFormatPrinter() : mFormatPrinter;
    }

    /**
     * 提供处理 RxJava 错误的管理器的回调
     *
     * @return
     */
    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

    /**
     * 提供缓存文件
     */
    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        return mCacheFile == null ? DataHelper.getCacheFile(application) : mCacheFile;
    }

    /**
     * 提供处理 Http 请求和响应结果的处理类
     */
    @Singleton
    @Provides
    @Nullable
    GlobalHttpHandler provideGlobalHttpHandler() {
        return mHandler;
    }

    @Singleton
    @Provides
    Cache.Factory provideCacheFactory(Application application) {

        return mCacheFactory == null ? new Cache.Factory() {
            @NonNull
            @Override
            public Cache build(CacheType type) {
                //若想自定义 LruCache 的 size, 或者不想使用 LruCache, 想使用自己自定义的策略
                //使用 GlobalConfigModule.Builder#cacheFactory() 即可扩展

                switch (type.getCacheTypeId()) {
                    //Activity、Fragment 以及 Extras 使用 IntelligentCache (具有 LruCache 和 可永久存储数据的 Map)
                    case CacheType.EXTRAS_TYPE_ID:
                    case CacheType.ACTIVITY_CACHE_TYPE_ID:
                    case CacheType.FRAGMENT_CACHE_TYPE_ID:
                        return new IntelligentCache(type.calculateCacheSize(application));
                    //其余使用 LruCache (当达到最大容量时可根据 LRU 算法抛弃不合规数据)
                    default:
                        return new LruCache(type.calculateCacheSize(application));
                }
            }
        } : mCacheFactory;
    }

    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     *
     * @return {@link Executor}
     */
    @Singleton
    @Provides
    ExecutorService provideExecutorService() {
        return mExecutorService == null ? new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), Util.threadFactory("Arms Executor", false))
                : mExecutorService;
    }

    public static final class Builder {
        private HttpUrl apiUrl;
        private BaseUrl baseUrl;
        private BaseImageLoaderStrategy loaderStrategy;
        private GlobalHttpHandler handler;
        private ResponseErrorListener responseErrorListener;
        private Cache.Factory cacheFactory;
        private File cacheFile;
        private List<Interceptor> interceptors;
        private GlobalHttpHandler mHandler;
        private ClientModule.RetrofitConfiguration retrofitConfiguration;
        private ClientModule.OkhttpConfiguration okhttpConfiguration;
        private RequestInterceptor.Level printHttpLogLevel;
        private FormatPrinter formatPrinter;
        private ExecutorService mExecutorService;

        private Builder() {

        }

        /**
         * 设置BaseUrl
         */
        public Builder baseurl(String baseUrl) {//基础url
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("BaseUrl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseurl(BaseUrl baseUrl) {
            this.baseUrl = Preconditions.checkNotNull(baseUrl,
                    BaseUrl.class.getCanonicalName() + "can not be null.");
            return this;
        }

        /**
         * 设置本地缓存文件 像 Glide
         *
         * @param cacheFile cacheFile
         */
        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        /**
         * 设置图片请求策略
         *
         * @param loaderStrategy {@link BaseImageLoaderStrategy}
         */
        public Builder imageLoaderStrategy(BaseImageLoaderStrategy loaderStrategy) {
            this.loaderStrategy = loaderStrategy;
            return this;
        }

        public Builder retrofitConfiguration(ClientModule.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(ClientModule.OkhttpConfiguration okhttpConfiguration) {
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        /**
         * 设置网络请求前后 对发生  和  返回数据进行处理
         *
         * @param handler GlobalHttpHandler
         * @return Builder
         */
        public Builder globalHttpHandler(GlobalHttpHandler handler) {
            this.handler = handler;
            return this;
        }

        /**
         * 设置全局的RxJava 错误处理
         *
         * @param responseErrorListener
         * @return
         */
        public Builder responseErrorListener(ResponseErrorListener responseErrorListener) {
            this.responseErrorListener = responseErrorListener;
            return this;
        }

        public Builder printHttpLogLevel(
                RequestInterceptor.Level printHttpLogLevel) {//是否让框架打印 Http 的请求和响应信息
            this.printHttpLogLevel = Preconditions.checkNotNull(printHttpLogLevel,
                    "The printHttpLogLevel can not be null, use RequestInterceptor.Level.NONE instead.");
            return this;
        }

        public Builder formatPrinter(FormatPrinter formatPrinter) {
            this.formatPrinter = Preconditions.checkNotNull(formatPrinter,
                    FormatPrinter.class.getCanonicalName() + "can not be null.");
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }
}
