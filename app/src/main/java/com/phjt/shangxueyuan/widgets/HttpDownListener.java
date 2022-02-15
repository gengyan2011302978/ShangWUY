package com.phjt.shangxueyuan.widgets;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface HttpDownListener{
    void onFailure(Call call, IOException e);
    void onResponse(Call call, Response response, long mTotalLength, long mAlreadyDownLength);
}
