package com.phjt.plugin_upload

class UploadAppResult {

    String code
    String message
    ResultBean data

    @Override
    public String toString() {
        return "UploadAppResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }


    class ResultBean {
        String buildKey
        Integer buildType
        Integer buildIsFirst
        Integer buildIsLastest
        Integer buildFileSize
        String buildName
        String buildVersion
        String buildVersionNo
        Integer buildBuildVersion
        String buildIdentifier
        String buildIcon
        String buildDescription
        String buildUpdateDescription
        String buildScreenShots
        String buildShortcutUrl
        String buildQRCodeURL
        String buildCreated
        String buildUpdated


        @Override
        public String toString() {
            return "ResultBean{" +
                    "buildKey='" + buildKey + '\'' +
                    ", buildType=" + buildType +
                    ", buildIsFirst=" + buildIsFirst +
                    ", buildIsLastest=" + buildIsLastest +
                    ", buildFileSize=" + buildFileSize +
                    ", buildName='" + buildName + '\'' +
                    ", buildVersion='" + buildVersion + '\'' +
                    ", buildVersionNo='" + buildVersionNo + '\'' +
                    ", buildBuildVersion=" + buildBuildVersion +
                    ", buildIdentifier='" + buildIdentifier + '\'' +
                    ", buildIcon='" + buildIcon + '\'' +
                    ", buildDescription='" + buildDescription + '\'' +
                    ", buildUpdateDescription='" + buildUpdateDescription + '\'' +
                    ", buildScreenShots='" + buildScreenShots + '\'' +
                    ", buildShortcutUrl='" + buildShortcutUrl + '\'' +
                    ", buildQRCodeURL='" + buildQRCodeURL + '\'' +
                    ", buildCreated='" + buildCreated + '\'' +
                    ", buildUpdated='" + buildUpdated + '\'' +
                    '}'
        }
    }
}