# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 指定代码的压缩级别
-optimizationpasses 5
# 是否使用大小写混合
-dontusemixedcaseclassnames
# 是否混淆第三方jar
-dontskipnonpubliclibraryclasses
# 混淆时是否做预校验
-dontpreverify
# 混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

# com.phjt.shangxueyuan.app保持混淆
-keep public class com.phjt.shangxueyuan.app.**{*;}
-keep public class com.phjt.shangxueyuan.bean.**{*;}

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#避免资源混淆
-keep class **.R$* {*;}

# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#避免Serializable接口的子类中指定的某些成员变量和方法混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#分享
-keep class com.phjt.sharestatistic.** {*;}
-dontwarn com.phjt.sharestatistic.**

#友盟
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.phjt.shangxueyuan.R$*{
	public static final int *;
}

#友盟推送
-dontwarn com.umeng.**
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.** {*;}
-keep class com.ut.** {*;}
-keep class com.uc.** {*;}
-keep class com.ta.** {*;}

-keep public class **.R$* {
    public static final int *;
}

#支付
-dontwarn com.phsxy.paylibrary.**
-keep class com.phsxy.paylibrary.**{*;}
-dontwarn com.phsxy.paylibrary.alipay.**
-keep class com.phsxy.paylibrary.alipay.**{*;}
-dontwarn com.phsxy.paylibrary.wechatpay.**
-keep class com.phsxy.paylibrary.wechatpay.**{*;}
-dontwarn com.phsxy.paylibrary.OnPayResultListener**
-keep class com.phsxy.paylibrary.OnPayResultListener**{*;}
-dontwarn com.phsxy.paylibrary.PayUtil**
-keep class com.phsxy.paylibrary.PayUtil**{*;}
#支付宝支付
-dontwarn com.phsxy.paylibrary.alipay.AliPayResult**
-keep class com.phsxy.paylibrary.alipay.AliPayResult**{*;}
-dontwarn com.phsxy.paylibrary.alipay.AliPayUtil**
-keep class com.phsxy.paylibrary.alipay.AliPayUtil**{*;}

#微信支付
-dontwarn com.phsxy.paylibrary.wechatpay.WeChatPayUtils**
-keep class com.phsxy.paylibrary.wechatpay.WeChatPayUtils**{*;}
-dontwarn com.phsxy.paylibrary.wechatpay.WxPayBean**
-keep class com.phsxy.paylibrary.wechatpay.WxPayBean**{*;}

#smartrefresh
-dontwarn com.scwang.smartrefresh.**

#友盟推送
-dontwarn com.umeng.**
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }
-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}
-keep public class **.R$*{
   public static final int *;
}

#腾讯云播放器  tbs文件
-keep class com.tencent.** {*;}

#Agentweb
-keep class com.just.agentweb.** {*;}
-keep class se.emilsjolander.flipview.** {*;}
-dontwarn com.just.agentweb.**

#WebView混淆配置
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

#EventBus
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep class org.greenrobot.eventbus.EventBus { *; }
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

################### OKhttp3 打包警告 OkHttp3 是在Base 中进了混淆 但打包时有警告 ###############################
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-dontwarn okhttp3.**
-dontwarn okio.**

#ButterKnife混淆配置
 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
     @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
     @butterknife.* <methods>;
 }

#Retrofit2混淆配置
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#RxJava、RxAndroid混淆配置
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#Glide混淆配置
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#Gson混淆配置
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

#Banner混淆
-keep class com.youth.banner.** { *; }

#RxPermission混淆配置
-keep class com.tbruyelle.rxpermissions2.* {
    *;
}
-dontwarn com.tbruyelle.rxpermissions.**

#dagger配置
-keep class dagger.** { *; }

#immersionbar混淆配置
-keep class com.gyf.immersionbar.* {*;}
-dontwarn com.gyf.immersionbar.**

#greendao
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties { *; }

# If you DO use SQLCipher:
-keep class org.greenrobot.greendao.database.SqlCipherEncryptedHelper { *; }

# If you do NOT use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do NOT use RxJava:
-dontwarn rx.**


#盟主直播
-dontwarn com.mengzhu.**
-keep class  com.mengzhu.** { *; }
-dontwarn tv.mengzhu.**
-keep class  tv.mengzhu.** { *; }
-dontwarn org.fourthline.cling.**
-keep class org.fourthline.cling.**{*;}
#接入上传需要
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**
-keep public class com.mzmedia.presentation.dto.**{*;}

#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }

#Ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }