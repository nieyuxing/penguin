package com.qexz.common;

public class QexzConst {

    // Session
    public final static String CURRENT_ACCOUNT = "current_account";

    public final static String CURRENT_MANAGE_ACCOUNT = "current_manage_account";

    //本地服务器-文件,图片所在位置,图片上传位置
//    public static final String UPLOAD_FILE_PATH = "D:\\Temp/";
//    public static final String UPLOAD_FILE_IMAGE_PATH = "D:\\Temp/";
    //public static final String UPLOAD_FILE_RESUME_PATH = "file:D:/";
    //云服务器-文件,图片所在位置,图片上传位置
    public static final String UPLOAD_FILE_PATH = "/usr/local/exam/upload/";
    public static final String UPLOAD_FILE_IMAGE_PATH = "/usr/local/exam/upload/images/";

    public static final String UPLOAD_FILE_RESUME_PATH = "/usr/local/exam/upload/";
    public static final String IPADDRESS = "http://47.113.102.217:8083/upload/";
    public static final String IMGIPADDRESS = "http://47.113.102.217:8083/upload/images/";

    //默认头像url
    public static final String DEFAULT_AVATAR_IMG_URL = "headimg_placeholder.png";
    //职位默认图片
    public static final String DEFAULT_POSITION_IMG_URL = "problemset_default.jpg";

    public static final int positionPageSize = 16;
    public static final int contestPageSize = 10;
    public static final int questionPageSize = 10;
    public static final int accountPageSize = 16;
    public static final int postPageSize = 8;
    public static final int gradePageSize = 12;
    public static final int commentPageSize = 16;

    //MD5加盐
    public static final String MD5_SALT = "qexz";
    //分页数据
    public final static String DATA = "data";

    public final static String SPLIT_CHAR = "_~_";

    public final static String SPLIT_AP_CHAR = "#";

}
