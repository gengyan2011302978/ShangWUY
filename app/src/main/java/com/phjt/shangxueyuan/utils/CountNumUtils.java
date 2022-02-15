package com.phjt.shangxueyuan.utils;

import java.math.BigDecimal;

/**
 * @author: gengyan
 * date:    2020/5/11 14:34
 * company: 普华集团
 * description: 描述
 */
public class CountNumUtils {

    /**
     * 大于999，返回999+
     *
     * @param count number
     * @return 返回CountNum
     */
    public static String getCountNum(int count) {
        String strCount;
        if (count <= 0) {
            strCount = "赞";
        } else if (count > 999) {
            strCount = "999+";
        } else {
            strCount = String.valueOf(count);
        }
        return strCount;
    }

    public static String getStudyNum(int studyNum) {
        if (studyNum < 1000) {
            return String.valueOf(studyNum);
        } else if (studyNum < 10000) {
            double aDouble = new BigDecimal((float) studyNum / 1000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            //判断是否符合取整条件
            if ((int) aDouble - aDouble == 0) {
                if (aDouble >= 10) {
                    return "1万";
                }
                return ((int) aDouble + "k");
            } else {
                return (aDouble + "k");
            }
        } else if (studyNum < 100000) {
            double aDouble = new BigDecimal((float) studyNum / 10000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            //判断是否符合取整条件
            if ((int) aDouble - aDouble == 0) {
                return ((int) aDouble + "万");
            } else {
                return (aDouble + "万");
            }
        } else if (studyNum == 100000) {
            return "10万";
        } else {
            return "10万+";
        }
    }
}
