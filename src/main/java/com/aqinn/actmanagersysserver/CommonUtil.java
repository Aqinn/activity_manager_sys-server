package com.aqinn.actmanagersysserver;

/**
 * @Author Aqinn
 * @Date 2020/12/25 10:51 上午
 */
public class CommonUtil {

    /**
     * 根据十进制数转换成二进制数，对应哪一位置上有 1 就是哪种签到方式被开启了
     *
     * @param dec
     * @return
     */
    public static Integer[] dec2typeArr(Integer dec) {
        String binStr = Integer.toString(dec.intValue(), 2);
        System.out.println(binStr);
        Integer[] res = new Integer[binStr.replace("0", "").length()];
        int flag = 0;
        for (int i = binStr.length() - 1; i >= 0; i--) {
            if (binStr.charAt(i) == '1')
                res[flag++] = binStr.length() - i;
        }
        return res;
    }

    /**
     * 上面方法 Integer[] dec2typeArr(Integer dec) 的反向过程
     *
     * @return
     */
    public static Integer typeArr2dec(Integer[] typeArr) {
        char[] temp = new char[]{'0', '0', '0', '0', '0', '0'};
        for (Integer integer : typeArr) {
            temp[temp.length - integer] = '1';
        }
        return Integer.parseInt(String.valueOf(temp), 2);
    }

}
