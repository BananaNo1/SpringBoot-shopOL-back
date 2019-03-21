package com.wust.graproject.util;

import java.math.BigDecimal;

/**
 * @author leis
 * @Descirption
 * @date 2019/3/12 9:50
 */


public class BigDecimalUtil {

    private BigDecimalUtil() {

    }

    public static BigDecimal mul(double v1, double v2) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(v2));
        return bigDecimal.multiply(bigDecimal1);
    }

    public static BigDecimal add(double v1, double v2) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(v1));
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(v2));
        return bigDecimal.add(bigDecimal1);
    }
}
