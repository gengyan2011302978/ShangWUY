package com.phjt.plugin_upload

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

import java.util.concurrent.TimeUnit

class OkHttpUtil {
    OkHttpClient okHttpClient
    Gson gson
    QyWXMsg qyWXMsg

    public OkHttpUtil() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        gson = new Gson()
        qyWXMsg = new QyWXMsg()
    }

    UploadAppResult uploadApkPgyer(String apkPath, String apiKey, String fileName) {
        String upload_url = "https://www.pgyer.com/apiv2/app/upload"
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), new File(apkPath))
        MultipartBody body = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart("_api_key", apiKey)
                .addFormDataPart("file", fileName, fileBody)
                .build()
        Request requestApk = new Request.Builder().url(upload_url).post(body).build()

        Response response = okHttpClient.newCall(requestApk).execute()
        String result = response.body.string()
        System.out.print("\n=========uploadresult:::$result======")
        return gson.fromJson(result, UploadAppResult.class)
    }

    void setQxWXMsg(String text, String webhook, boolean isAll, List<String> notice) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , qyWXMsg.createTextMsg(text, isAll, notice))

        Request requestWX = new Request.Builder().url(webhook).post(requestBody).build()
        Response responseWx = okHttpClient.newCall(requestWX).execute()
        String result = responseWx.body.string()
        System.out.print("\n=========resultWXMsg:::$result======")
    }
}