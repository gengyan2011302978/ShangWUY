package com.phjt.plugin_upload

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory

class UploadApkExtension {

    PgyExtension pgyExtension
    QyWXExtension qyWXExtension

    public UploadApkExtension(ObjectFactory objectFactory) {
        pgyExtension = objectFactory.newInstance(PgyExtension)
        qyWXExtension = objectFactory.newInstance(QyWXExtension)
    }

    public void pgy(Action<PgyExtension> action) {
        action.execute(pgyExtension)
    }

    public void qywx(Action<QyWXExtension> action) {
        action.execute(qyWXExtension)
    }
}