package com.phjt.shangxueyuan.bean.model;

import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class FileBean {
    //下载完成
    public static final int DOWNLOAD_COMPLETE=4;
    //准备下载
    public static final int DOWNLOAD_REDYA=0;
    //下载进行中
    public static final int DOWNLOAD_PROCEED=1;
    //暂停
    public static final int DOWNLOAD_PAUSE=2;
    //出错
    public static final int DOWNLOAD_ERROR=3;
    @Id
    private Long id;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 1-下载 2-下载完成
     */
    private int type;
    private String url;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 一级文件名字
     */
    private String fileLevel;
    /**
     * 一级icon
     */
    private String fileLevelUrl;
    /**
     * 一级desc
     */
    private String fileLevelDesc;
    /**
     * 保存路径
     */
    private String path;
    /**
     * 文件大小
     */
    private Long size;
    private String sizeStr;
    /**
     * 下载状态
     */
    private int status = 0;
    /**
     * 下载进度
     */
    private int progress;

    private int courseId;
    /**
     * 下载速度
     */
    private String speed;
    private Date createTime;

    private boolean checked;
    /**
     * 是否显示单选框
     */
    private boolean show;

    private int seq;

    public static int getDownloadComplete() {
        return DOWNLOAD_COMPLETE;
    }

    public static int getDownloadRedya() {
        return DOWNLOAD_REDYA;
    }

    public static int getDownloadProceed() {
        return DOWNLOAD_PROCEED;
    }

    public static int getDownloadPause() {
        return DOWNLOAD_PAUSE;
    }

    public static int getDownloadError() {
        return DOWNLOAD_ERROR;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileLevel() {
        return fileLevel;
    }

    public void setFileLevel(String fileLevel) {
        this.fileLevel = fileLevel;
    }

    public String getFileLevelUrl() {
        return fileLevelUrl;
    }

    public void setFileLevelUrl(String fileLevelUrl) {
        this.fileLevelUrl = fileLevelUrl;
    }

    public String getFileLevelDesc() {
        return fileLevelDesc;
    }

    public void setFileLevelDesc(String fileLevelDesc) {
        this.fileLevelDesc = fileLevelDesc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getSizeStr() {
        return sizeStr;
    }

    public void setSizeStr(String sizeStr) {
        this.sizeStr = sizeStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
