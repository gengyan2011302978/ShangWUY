package com.phjt.shangxueyuan.app;

import android.content.Context;
import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.phjt.base.http.errorhandler.handler.listener.ResponseErrorListener;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.LogUtils;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * @author : austen
 * company    : 普华
 * date       : 2019/5/22 11:43
 * description: 统一错误处理类
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Throwable t) {
        String msg = "抱歉,访问出了点问题哦!";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = "数据解析错误";
        }
        TipsUtil.showTips(msg);
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        LogUtils.e("httpException:" + httpException);
        return "数据加载失败";
    }
}
