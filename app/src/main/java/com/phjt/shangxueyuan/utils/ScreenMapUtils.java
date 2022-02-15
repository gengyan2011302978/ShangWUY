package com.phjt.shangxueyuan.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Roy
 * date:   2021/6/18
 * company: 普华集团
 * description:
 */
public class ScreenMapUtils {

    public static final String ANSWER_ONE = "导师答疑";
    public static final String ANSWER_TWO = "精选解答";
    public static final String ANSWER_THREE = "我的提问";

    public static final String TASK_RECORD = "发布问题";

    public Map<String, String> mScreenMap;

    public ScreenMapUtils() {
        mScreenMap = new HashMap<>();
    }

    private static class Holder {
        private static final ScreenMapUtils INSTANCE = new ScreenMapUtils();
    }

    public static ScreenMapUtils getInstance() {
        return ScreenMapUtils.Holder.INSTANCE;
    }

    public void checkMapNull() {
        if (mScreenMap == null) {
            mScreenMap = new HashMap<>();
        }
    }


    /**
     * 清空问答筛选标签
     */
    public void clearAnswer() {
        checkMapNull();
        mScreenMap.put(ANSWER_ONE, "");
        mScreenMap.put(ANSWER_TWO, "");
        mScreenMap.put(ANSWER_THREE, "");
    }

    /**
     * 清空任务记录
     */
    public void clearTask() {
        checkMapNull();
        mScreenMap.put(TASK_RECORD, "");
    }

}
