package com.phjt.shangxueyuan.bean.model;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class File implements Serializable{
    static final long serialVersionUID = -15515456L;

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
    private String sizeStr = "0";
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

    private int videotype;

    @Generated(hash = 166936427)
    public File(Long id, String fileName, int type, String url, String fileType,
            String fileLevel, String fileLevelUrl, String fileLevelDesc,
            String path, Long size, String sizeStr, int status, int progress,
            int courseId, String speed, Date createTime, boolean checked,
            boolean show, int seq, int videotype) {
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.url = url;
        this.fileType = fileType;
        this.fileLevel = fileLevel;
        this.fileLevelUrl = fileLevelUrl;
        this.fileLevelDesc = fileLevelDesc;
        this.path = path;
        this.size = size;
        this.sizeStr = sizeStr;
        this.status = status;
        this.progress = progress;
        this.courseId = courseId;
        this.speed = speed;
        this.createTime = createTime;
        this.checked = checked;
        this.show = show;
        this.seq = seq;
        this.videotype = videotype;
    }

    @Generated(hash = 375897388)
    public File() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileLevel() {
        return this.fileLevel;
    }

    public void setFileLevel(String fileLevel) {
        this.fileLevel = fileLevel;
    }

    public String getFileLevelUrl() {
        return this.fileLevelUrl;
    }

    public void setFileLevelUrl(String fileLevelUrl) {
        this.fileLevelUrl = fileLevelUrl;
    }

    public String getFileLevelDesc() {
        return this.fileLevelDesc;
    }

    public void setFileLevelDesc(String fileLevelDesc) {
        this.fileLevelDesc = fileLevelDesc;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getSizeStr() {
        return this.sizeStr;
    }

    public void setSizeStr(String sizeStr) {
        this.sizeStr = sizeStr;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSpeed() {
        return this.speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getShow() {
        return this.show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getVideotype() {
        return this.videotype;
    }

    public void setVideotype(int videotype) {
        this.videotype = videotype;
    }
}
