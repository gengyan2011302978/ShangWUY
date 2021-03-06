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
     * ?????? BaseUrl,???????????? <"https://api.github.com/">
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
     * ????????????????????????,???????????? {@link Glide}
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
     * ???????????? RxJava ???????????????????????????
     *
     * @return
     */
    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

    /**
     * ??????????????????
     */
    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        return mCacheFile == null ? DataHelper.getCacheFile(application) : mCacheFile;
    }

    /**
     * ???????????? Http ?????????????????????????????????
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
                //??????????????? LruCache ??? size, ?????????????????? LruCache, ?????????????????????????????????
                //?????? GlobalConfigModule.Builder#cacheFactory() ????????????

                switch (type.getCacheTypeId()) {
                    //Activity???Fragment ?????? Extras ?????? IntelligentCache (?????? LruCache ??? ???????????????????????? Map)
                    case CacheType.EXTRAS_TYPE_ID:
                    case CacheType.ACTIVITY_CACHE_TYPE_ID:
                    case CacheType.FRAGMENT_CACHE_TYPE_ID:
                        return new IntelligentCache(type.calculateCacheSize(application));
                    //???????????? LruCache (????????????????????????????????? LRU ???????????????????????????)
                    default:
                        return new LruCache(type.calculateCacheSize(application));
                }
            }
        } : mCacheFactory;
    }

    /**
     * ????????????????????????????????????,?????????????????????????????????
     * ???????????????????????????????????????????????????
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
         * ??????BaseUrl
         */
        public Builder baseurl(String baseUrl) {//??????url
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
         * ???????????????????????? ??? Glide
         *
         * @param cacheFile cacheFile
         */
        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        /**
         * ????????????????????????
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
         * ???????????????????????? ?????????  ???  ????????????????????????
         *
         * @param handler GlobalHttpHandler
         * @return Builder
         */
        public Builder globalHttpHandler(GlobalHttpHandler handler) {
            this.handler = handler;
            return this;
        }

        /**
         * ???????????????RxJava ????????????
         *
         * @param responseErrorListener
         * @return
         */
        public Builder responseErrorListener(ResponseErrorListener responseErrorListener) {
            this.responseErrorListener = responseErrorListener;
            return this;
        }

        public Builder printHttpLogLevel(
                RequestInterceptor.Level printHttpLogLevel) {//????????????????????? Http ????????????????????????
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
