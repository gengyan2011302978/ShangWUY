package com.phjt.base.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.phjt.base.http.GlobalHttpHandler;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.listener.ResponseErrorListener;
import com.phjt.base.http.log.RequestInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/25 14:15
 * description:
 */
@Module
public abstract class ClientModule {
    private static final int TIME_OUT = 30;

    /**
     * 提供 {@link Retrofit}
     *
     * @param application   {@link Application}
     * @param configuration {@link RetrofitConfiguration}
     * @param builder       {@link Retrofit.Builder}
     * @param client        {@link OkHttpClient}
     * @param httpUrl       {@link HttpUrl}
     * @param gson          {@link Gson}
     * @return {@link Retrofit}
     */
    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application, @Nullable RetrofitConfiguration configuration, Retrofit.Builder builder, OkHttpClient client
            , HttpUrl httpUrl, Gson gson) {
        builder
                .baseUrl(httpUrl)//域名
                .client(client);//设置 OkHttp
        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }

        builder
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 RxJava
                .addConverterFactory(GsonConverterFactory.create(gson));//使用 Gson
        return builder.build();
    }


    /**
     * 提供 {@link OkHttpClient}
     *
     * @param application     {@link Application}
     * @param configuration   {@link OkhttpConfiguration}
     * @param builder         {@link OkHttpClient.Builder}
     * @param intercept       {@link Interceptor}
     * @param interceptors    {@link List<Interceptor>}
     * @param handler         {@link GlobalHttpHandler}
     * @param executorService {@link ExecutorService}
     * @return {@link OkHttpClient}
     */
    @Singleton
    @Provides
    static OkHttpClient provideClient(Application application, @Nullable OkhttpConfiguration configuration, OkHttpClient.Builder builder, Interceptor intercept
            , @Nullable List<Interceptor> interceptors, @Nullable GlobalHttpHandler handler, ExecutorService executorService) {
        builder
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        if (handler != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });
        }

        //如果外部提供了 Interceptor 的集合则遍历添加
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        //为 OkHttp 设置默认的线程池
        builder.dispatcher(new Dispatcher(executorService));

        if (configuration != null) {
            configuration.configOkhttp(application, builder);
        }
        return builder.build();
    }


    /**
     * @param application
     * @param listener
     * @return
     */
    @Singleton
    @Provides
    static RxErrorHandler provideRxErrorHandler(Application application, ResponseErrorListener listener) {
        return RxErrorHandler.builder()
                .with(application)
                .responseErrorListener(listener)
                .build();
    }


    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Binds
    abstract Interceptor bindInterceptor(RequestInterceptor interceptor);


    /**
     * {@link Retrofit} 自定义配置接口
     */
    public interface RetrofitConfiguration {
        void configRetrofit(@NonNull Context context, @NonNull Retrofit.Builder builder);
    }


    /**
     * {@link OkHttpClient} 自定义配置接口
     */
    public interface OkhttpConfiguration {
        void configOkhttp(@NonNull Context context, @NonNull OkHttpClient.Builder builder);
    }


}
