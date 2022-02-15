package com.phjt.base.http.errorhandler.handler;

import android.content.Context;

import com.phjt.base.http.errorhandler.handler.listener.ResponseErrorListener;


/**
 * @author : austen
 * company    : JGT
 * date       : 2019/5/22 11:04
 * description:
 */
public class ErrorHandlerFactory {

    public final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ResponseErrorListener mResponseErrorListener;

    public ErrorHandlerFactory(Context mContext, ResponseErrorListener mResponseErrorListener) {
        this.mResponseErrorListener = mResponseErrorListener;
        this.mContext = mContext;
    }

    /**
     *  处理错误
     * @param throwable
     */
    public void handleError(Throwable throwable) {
        mResponseErrorListener.handleResponseError(mContext, throwable);
    }
}
