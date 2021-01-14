package com.aqinn.actmanagersysserver.utils;

import java.io.File;

/**
 * @author Aqinn
 * @date 2019/10/27 7:21 下午
 */
public class PathUtil {

    private static String separator = File.separator;

    public static final String tempFileFolder = getBasePath() + "TEMP/";

    public static final String downloadFileFolder = "";

    public static String tempFilePreName = "TEMP_";

    public static String getBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")){
            basePath = "D:/JavaEE_lab6_SSM/";
        }
        else {
            basePath = "/Users/aqinn/Desktop/JavaEE_lab6_SSM_FILE/";
        }
        //针对不同系统替换不同斜杠
        basePath = basePath.replace("/",separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId){
        String imagePath = "upload/item/shop/" + shopId + "/";
        String result = imagePath.replace("/",separator);
        return result;
    }


}
