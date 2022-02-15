package com.phjt.base.http.errorhandler.handler.listener;

import android.content.Context;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/5/22 11:05
 * description:
 */
public interface ResponseErrorListener {
    void handleResponseError(Context context, Throwable t);


    ResponseErrorListener EMPTY = new ResponseErrorListener() {
        @Override
        public void handleResponseError(Context context, Throwable t) {

        }
    };
}
