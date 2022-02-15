package com.phjt.shangxueyuan.app;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.phjt.base.http.GlobalHttpHandler;
import com.phjt.base.integration.AppManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.common.MyBusiness;
import com.phjt.shangxueyuan.mvp.ui.activity.LoginActivity;
import com.phjt.shangxueyuan.utils.AES;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.ApkUtils;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author : austen
 * company    : 普华
 * date       : 2019/3/27 19:04
 * description: 请求拦截 请求发出前 与 请求响应后 提前得到 {@link Request} 和  {@link Response}
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link Interceptor.Chain}
     * @param response   {@link Response}
     * @return {@link Response}
     */
    @NonNull
    @Override
    public Response onHttpResultResponse(@Nullable String httpResult,
                                         @NonNull Interceptor.Chain chain, @NonNull Response response) {
        if (!TextUtils.isEmpty(httpResult)) {
            try {
                BaseBean baseBean = new Gson().fromJson(httpResult, BaseBean.class);
                if (baseBean != null) {
                    if (baseBean.code == Constant.BUSINESS_CODE_FORCE_OFFLINE
                            || baseBean.code == Constant.BUSINESS_CODE_IS_NULL
                            || baseBean.code == Constant.BUSINESS_CODE_ERROR) {
                        //不清除token
                        MyBusiness.cleanSp();

                        final String msg = baseBean.msg;
                        AppManager.getAppManager().killAll(LoginActivity.class);
                        ArchitectUtils.startActivity(LoginActivity.class);
                        Activity currentActivity = AppManager.getAppManager().getCurrentActivity();
                        if (currentActivity != null) {
                            currentActivity.runOnUiThread(() ->
                                    TipsUtil.show(msg));
                        }
                    } else if (!TextUtils.isEmpty(baseBean.encodeStr)) {
                        //获取加密后的data，解密后重新赋值
                        String newData = AES.decryptFromBase64((String) baseBean.data, "FADA18=+" + baseBean.encodeStr);
                        baseBean.data = new Gson().fromJson(newData, Object.class);
                        String json = new Gson().toJson(baseBean);
                        return response.newBuilder()
                                .code(200)
                                .addHeader("Content-Type", "application/json")
                                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                                .message(json)
                                .request(chain.request())
                                .protocol(Protocol.HTTP_2)
                                .build();
                    }
                }
            } catch (Exception e) {
                LogUtils.e("onHttpResultResponse Exception", e.getMessage());
            }
        }
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link Interceptor.Chain}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @NonNull
    @Override
    public Request onHttpRequestBefore(@NonNull Interceptor.Chain chain, @NonNull Request request) {
        String token = SPUtils.getInstance().getString(Constant.SP_TOKEN);
        Request.Builder header = chain.request().newBuilder()
                .addHeader("token", token)
                .addHeader("source", "Android")
                .addHeader("version", ApkUtils.getVersionName(context));

        return header.build();
    }

}
