/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.content;

import xyz.tobebetter.entity.Entity;

/**
 * 文章和分类的关联
 *
 * @author zhuqing
 */
public class ContentAndCatalog extends Entity{
    private String catalogId;
    private String contentId;

    /**
     * @return the catalogId
     */
    public String getCatalogId() {
        return catalogId;
    }

    /**
     * @param catalogId the catalogId to set
     */
    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
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
    
}
