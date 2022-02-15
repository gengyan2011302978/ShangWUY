package com.phjt.shangxueyuan.interf;

/**
 * @author: Roy
 * date:   2020/3/27
 * company: 普华集团
 * description:
 */
public interface ICourseDetailDialog {

    void collectionCallBack();

    void multipleCallBack();

    void downloadCallBack();

    void weChatCallBack(int statusId, String name);

    void multipleChoseCallBack(float speedLevel);
}
