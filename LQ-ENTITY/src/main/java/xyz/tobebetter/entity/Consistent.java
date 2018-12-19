/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity;

/**
 * 记录常量数据的实体
 *
 * @author zhuqing
 */
public class Consistent {


    /**
     * 密码
     */
    public final static String PASSWORD = "0110151510";

    public static final String SLIP_TIME_AND_TEXT = "-->";
    public static final String SLIP_START_AND_END = "==>";
    public static final String SLIP_EN_AND_CH = ">::<";
    public static final String SLIP_END = "<=====>";
    public static final String SLIP_SENTENCE = "\n";

    /**
     * 已经发布的
     */
    public final static int HAS_LAUNCHED = 101;
    /**
     * 未发布
     */
    public final static int UN_LAUNCH = 102;

    /**
     * 已背诵
     */
    public final static int HAS_RECITED = 201;

    /**
     * 未背诵
     */
    public final static int UN_RECIT = 202;
    /**
     * 正在背诵
     */
    public final static Integer RECITING = 203;

    /**
     * 课本类型
     */
    public final static int BOOK_TYPE = 301;
    /**
     * 章节类型
     */
    public final static int CHAPTER_TYPE = 302;

    /**
     * 文章类型
     */
    public final static int ARTICLE_TYPE = 302;

    /**
     * 段类型
     */
    public final static int SEGMENT_TYPE = 302;
    /**
     * 分类类型
     */
    public final static int CATALOG_TYPE = 303;

    public static final int DELETE = -1;

    public static final int USER_NORMAL = 0;
    public static final int USER_WECHAT = 1;
    public static final int USER_QQ = 2;

    /**
     * 正在学习
     */
    public static final int LEARNING = 401;
    /**
     * 已经学习
     */
    public static final int LEARNED = 402;
    /**
     * 未学习
     */
    public static final int UN_LEARN = 403;

    /**
     * 错误
     */
    public final static int ERROR = -1;
    /**
     * 成功
     */
    public final static int SUCCESS = 0;

    /**
     * 警告
     */
    public final static int WARNING = 1;
    
    /**
     * 临时用户标志
     */
    public final static int USER_TEMP_STATUS=0;
    
    /**
     * 临时用户标志
     */
    public final static int REGIST_USER=1;
    /**
     * 未保存数据的标志
     */
    public final static int UN_SAVED_STATUS= -2;
    
    
    
    public final static int MAN= 1;
    public final static int WOMEN = 0;
    
    
    
    public final static int ANDROID=400;
    public final static int IOS = 401;
    
    
    public final static int USER_TYPE=0;
    public final static int USER_TYPE_QQ = 1;
    public final static int USER_TYPE_WEIXIN = 2;
    public final static int USER_TYPE_WEIBO=3;

    

}
