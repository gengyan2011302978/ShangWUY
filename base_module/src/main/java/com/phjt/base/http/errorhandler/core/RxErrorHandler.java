package com.phjt.base.http.errorhandler.core;

import android.content.Context;

import com.phjt.base.http.errorhandler.handler.ErrorHandlerFactory;
import com.phjt.base.http.errorhandler.handler.listener.ResponseErrorListener;


/**
 * @author : austen
 * company    : JGT
 * date       : 2019/5/22 11:02
 * description:
 */
public class RxErrorHandler {
    public final String TAG = this.getClass().getSimpleName();

    private ErrorHandlerFactory mHandlerFactory;

    public RxErrorHandler(Builder builder) {
        this.mHandlerFactory = builder.errorHandlerFactory;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ErrorHandlerFactory getHandlerFactory() {
        return mHandlerFactory;
    }

    public static final class Builder {
        private Context context;
        private ResponseErrorListener mResponseErrorListener;
        private ErrorHandlerFactory errorHandlerFactory;

        private Builder() {

        }

        public Builder with(Context context) {
            this.context = context;
            return this;
        }

        public Builder responseErrorListener(ResponseErrorListener responseErrorListener) {
            this.mResponseErrorListener = responseErrorListener;
            return this;
        }

        public RxErrorHandler build() {
            if (context == null) {
                throw new IllegalStateException("Context is required");
            }

            if (mResponseErrorListener == null) {
                throw new IllegalStateException("ResponseErrorListener is required");
            }

            this.errorHandlerFactory = new ErrorHandlerFactory(context, mResponseErrorListener);

            return new RxErrorHandler(this);
        }

    }
}
