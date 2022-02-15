package com.phjt.shangxueyuan.bean.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Entity
public class VideoInfo implements Serializable{
    static final long serialVersionUID = -15515456L;
    //任务的三个状态
    public static final int DOWN_WAITING = 5;
    public static final int DOWN_LOADING = 4;
    public static final int DOWN_STOP = 3;
    public static final int DOWN_ERROR = 2;
    public static final int DOWN_FINISH = 1;
    public boolean isEditCheckedFake;
    @Id
    private String videoFileId;
    private String name;
    private int duration;
    private String imgUrl;
    private String localVideoPath;
    private int downloadState = DOWN_STOP;
    private int taskId;
    private boolean isEditChecked;
    //文件总长度
    private int totalFileSize;
    @Generated(hash = 1048111194)
    public VideoInfo(boolean isEditCheckedFake, String videoFileId, String name,
            int duration, String imgUrl, String localVideoPath, int downloadState,
            int taskId, boolean isEditChecked, int totalFileSize) {
        this.isEditCheckedFake = isEditCheckedFake;
        this.videoFileId = videoFileId;
        this.name = name;
        this.duration = duration;
        this.imgUrl = imgUrl;
        this.localVideoPath = localVideoPath;
        this.downloadState = downloadState;
        this.taskId = taskId;
        this.isEditChecked = isEditChecked;
        this.totalFileSize = totalFileSize;
    }
    @Generated(hash = 296402066)
    public VideoInfo() {
    }
    public boolean getIsEditCheckedFake() {
        return this.isEditCheckedFake;
    }
    public void setIsEditCheckedFake(boolean isEditCheckedFake) {
        this.isEditCheckedFake = isEditCheckedFake;
    }
    public String getVideoFileId() {
        return this.videoFileId;
    }
    public void setVideoFileId(String videoFileId) {
        this.videoFileId = videoFileId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getLocalVideoPath() {
        return this.localVideoPath;
    }
    public void setLocalVideoPath(String localVideoPath) {
        this.localVideoPath = localVideoPath;
    }
    public int getDownloadState() {
        return this.downloadState;
    }
    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }
    public int getTaskId() {
        return this.taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public boolean getIsEditChecked() {
        return this.isEditChecked;
    }
    public void setIsEditChecked(boolean isEditChecked) {
        this.isEditChecked = isEditChecked;
    }
    public int getTotalFileSize() {
        return this.totalFileSize;
    }
    public void setTotalFileSize(int totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

}
