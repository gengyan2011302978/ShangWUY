package com.phjt.shangxueyuan.utils;

import java.math.BigDecimal;

/**
 * @author: gengyan
 * date:    2020/8/20 11:57
 * company: 普华集团
 * description: 描述
 */
public class NumberUtil {

    /**
     * @param d
     * @return 返回string类型的 保留两位小数
     */
    public static String getStrDouble(double d) {
        return String.format("%.2f", d);
    }

    /**
     * @param d
     * @return 返回double类型的 保留两位小数
     */
    public static double getDouble(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param d
     * @return 返回String类型的 保留整数位
     */
    public static String getStrByDoubleToInt(double d) {
        return String.valueOf(Double.valueOf(d).intValue());
    }
}
