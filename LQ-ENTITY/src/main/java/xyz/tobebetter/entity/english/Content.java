/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english;

import com.fasterxml.jackson.annotation.JsonInclude;
import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuqing
 */
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Content extends Entity {

    public static final int LUNCH = 0;
    public static final int CANCEL_LUNCH = 1;
    
    private String content;

    private String userId;
    /**
     * 图片路
     */
    private String imagePath;
    /**
     * 音频路径
     */
    private String audioPath;

    /**
     * 标题
     */
    private String title;
    /**
     * fu类ID
     */
    private String parentId;

    /**
     * 内容所属的分类
     */
    private String catalogId;

    /**
     * 阅读数
     */
    private Long readNum ;

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return the audioPath
     */
    public String getAudioPath() {
        return audioPath;
    }

    /**
     * @param audioPath the audioPath to set
     */
    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }


    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 内容所属的分类
     *
     * @return the catalogId
     */
    public String getCatalogId() {
        return catalogId;
    }

    /**
     * 内容所属的分类
     *
     * @param catalogId the catalogId to set
     */
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * fu类ID
     *
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * fu类ID
     *
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 阅读数
     *
     * @return the readNum
     */
    public Long getReadNum() {
        return readNum;
    }

    /**
     * 阅读数
     *
     * @param readNum the readNum to set
     */
    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

}
