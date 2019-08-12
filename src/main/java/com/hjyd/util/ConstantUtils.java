package com.hjyd.util;

/**
 * @program：ServiceA
 * @description：公共设置工具
 * @author：林黎明
 * @create：2019-05-10 11:28
 */
public class ConstantUtils {

    /**
     * 上传文件的目录地址
     * UPLOAD_FLAG: 上传类型（可选择LOCAL_UPLOAD：本地上传，FTP_UPLOAD：远程上传，需要配置LOCAL_UPLOAD_PATH）
     */

    public static String UPLOAD_FLAG = "FTP_UPLOAD";
    public static String LOCAL_UPLOAD_PATH = "D:\\upload\\files\\";

    /**
     * UPLOAD TO WHERE
     */
    public static final String UPLOAD_FLAG_LOCAL = "LOCAL_UPLOAD";
    public static final String UPLOAD_FLAG_FTP = "FTP_UPLOAD";


    /**
     * 文件大小限定
     **/
    public static final int MIN_FILE_SIZE = 104857600;

}
