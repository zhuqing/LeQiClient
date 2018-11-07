/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuqing
 */
public class Segment extends Entity{
    private String title;
    private String content;
    private String userId;
    private String contentId;
    /**
     * 音频的路径，优先去Segment中的文件
     * 先判断Segment中有没有音频路径，在判断content中有没有音频文件
     */
    private String audioPath;
    /**
     * 阅读数量，默认是0
     */
    private Long readNum = 0L;
    /**
     * 序号
     */
    private Integer indexNo;

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
     * @return the contentId
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * 阅读数量
     * @return the readNum
     */
    public Long getReadNum() {
        return readNum;
    }

    /**
     * 阅读数量
     * @param readNum the readNum to set
     */
    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    /**
     * @return the indexNo
     */
    public Integer getIndexNo() {
        return indexNo;
    }

    /**
     * @param indexNo the indexNo to set
     */
    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
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

   
    
}
