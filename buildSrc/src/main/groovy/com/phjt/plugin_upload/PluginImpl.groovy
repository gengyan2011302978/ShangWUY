package com.phjt.plugin_upload

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class PluginImpl implements Plugin<Project> {

    UploadApkExtension extension

    @Override
    void apply(Project target) {
        extension = target.extensions.create('uploadApk', UploadApkExtension)

        if (target.android.hasProperty('applicationVariants')) {
            target.android.applicationVariants.all { variant ->
                Task uploadPgyTask = target.task("assemble${variant.name.capitalize()}Pgy").doLast {
                    System.out.print("\n=========Start uploading pgy======")
                    def (String appPackage, String apkPath, String fileName, String appVersion, String appBuild, String apiKey) = getParamsPgyer(target, variant)
                    OkHttpUtil okHttpUtil = new OkHttpUtil()
                    UploadAppResult uploadAppResult = okHttpUtil.uploadApkPgyer(apkPath, apiKey, fileName)

//                    测试企业微信发送使用
//                    UploadAppResult uploadAppResult = new UploadAppResult()
//                    uploadAppResult.setData(new UploadAppResult.ResultBean())

                    sendQyWXMsg(okHttpUtil, appVersion, "https://www.pgyer.com/${uploadAppResult.data.buildShortcutUrl}", uploadAppResult.data.buildQRCodeURL)
                }
                //在assembleTask执行后执行
                uploadPgyTask.dependsOn(target.tasks["assemble${variant.name.capitalize()}"])
            }
        }
    }

    private void sendQyWXMsg(OkHttpUtil okHttpUtil, String appVersion, String installUrl, String qr) {
        String appName = extension.getQyWXExtension().getAppName()
        String webhookUrl = extension.getQyWXExtension().getWebhook()
        String content = extension.getQyWXExtension().getContent()
        boolean isAll = extension.getQyWXExtension().isAll
        List<String> notice = extension.getQyWXExtension().notice

        String msg = "${appName}v${appVersion} Android最新测试包：${installUrl}"
        if (content != null && content.length() > 0) {
            msg = "${msg}\n此次更新：${content}"
        }
        okHttpUtil.setQxWXMsg(msg, webhookUrl, isAll, notice)
    }

    private List getParamsPgyer(Project project, variant) {
        String apiKey = extension.getPgyExtension().getApiKey()
        def (String appPackage, String apkPath, String fileName, String appVersion, String appBuild) = getCommon(project, variant)
        return [appPackage, apkPath, fileName, appVersion, appBuild, apiKey]
    }

    private List getCommon(Project project, variant) {
        String appPackage = project.android.defaultConfig.applicationId
        String appVersion = project.android.defaultConfig.versionName
        String appBuild = project.android.defaultConfig.versionCode
        String apkPath = variant.outputs.first().outputFile
        File file = new File(apkPath)
        String fileName = file.getName()
        return [appPackage, apkPath, fileName, appVersion, appBuild]
    }
}