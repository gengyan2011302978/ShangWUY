package com.phjt.plugin_upload

import javax.inject.Inject

class QyWXExtension {

    String appName
    String webhook
    String content
    boolean isAll
    List<String> notice

    @Inject
    public QyWXExtension() {
    }
}