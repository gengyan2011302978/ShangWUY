package com.phjt.shangxueyuan.utils;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phsxy.utils.SPUtils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author: Roy
 * date:   2020/5/6
 * company: 普华集团
 * description:
 */
public class FileUploadUtils {
    public static void upload(Activity activity, File file, int what, UploadCallback callback) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        CommonHttpManager.getInstance(activity)
                .obtainRetrofitService(ApiService.class)
                .uploadImg(body)
                .compose(RxUtils.applySchedulers())
                .subscribe(new ErrorHandleSubscriber<BaseBean>(CommonHttpManager.getHttpErrorHandler()) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (callback == null) {
                            return;
                        }
                        if (bean != null) {
                            if (bean.isOk() && bean.data != null) {
                                String json = new Gson().toJson(bean.data);
                                UploadResultBean resultBean = new Gson().fromJson(json, UploadResultBean.class);
                                resultBean.what = what;
                                callback.onFileUploadSuccess(resultBean);
                            } else {
                                callback.onFileUploadError(new Throwable(bean.msg));
                            }
                        } else {
                            callback.onFileUploadError(new Throwable("请求失败,请稍后重试"));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (callback != null) {
                            callback.onFileUploadError(e);
                        }
                    }
                });
    }



    public class UploadResultBean {
        @SerializedName("url")
        public String url;
        @SerializedName("realUrl")
        public String realUrl;
        public int what;

    }

    /**
     * 得到网页中图片的地址
     */
    public static Set<String> getImgStr(String htmlStr) {
        Set<String> pics = new HashSet<>();
        String img = "";
        Pattern pImage;
        Matcher mImage;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regExImg = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        pImage = Pattern.compile
                (regExImg, Pattern.CASE_INSENSITIVE);
        mImage = pImage.matcher(htmlStr);
        while (mImage.find()) {
            // 得到<img />数据
            img = mImage.group();
            // 匹配<img>中的src数据
            String regExImgSrc = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";
            Matcher m = Pattern.compile(regExImgSrc).matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }


    public interface UploadCallback {
        void onFileUploadSuccess(UploadResultBean bean);

        void onFileUploadError(Throwable e);
    }



}
