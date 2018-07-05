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

    public static final String SLIP_TIME_AND_TEXT = "<:>";
    public static final String SLIP_EN_AND_CH = "<&>";
    public static final String SLIP_END = "<=====>";

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

}
