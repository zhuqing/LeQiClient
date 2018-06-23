/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english;

import xyz.tobebetter.entity.Entity;

/**
 *课本，包含有多个章节
 * @author zhuqing
 */
public class Catalog extends Entity{
 
    /**
     * 分类的标题
     */
    private String title;
    /**
     * 图片路径
     */
    private String imagePath;
    /**
     * 书和章节
     */
    private Integer type;
    
    /**
     * 如果是章节的话 有父节点Id
     */
    private String parentId;
    
    /**
     * 详情描述
     */
    private String description;
    
    /**
     * 创建者
     */
    private String userId;
    
    /**
     * 订阅数
     */
    private Long subscribeNum;
    
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 创建者
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 创建者
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 详情描述
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 详情描述
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 订阅数
     * @return the subscribeNum
     */
    public Long getSubscribeNum() {
        return subscribeNum;
    }

    /**
     * 订阅数
     * @param subscribeNum the subscribeNum to set
     */
    public void setSubscribeNum(Long subscribeNum) {
        this.subscribeNum = subscribeNum;
    }

   
    
}
