/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.nav;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author zhuqing
 */

public class NavItem {

    /**
     * 导航的标题
     */
    private String id;
    /**
     * 导航的标题
     */
    private String title;
    /**
     * 导航对应的业务
     */
    private String modelId;
    /**
     * 子导航
     */
    private List<NavItem> children;

    public NavItem(String id, String title, String modelId) {
        this.id = id;
        this.title = title;
        this.modelId = modelId;
    }

    public NavItem(String title, String modelId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.modelId = modelId;
    }

    /**
     * 导航的标题
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 导航的标题
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 导航的标题
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 导航的标题
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 导航对应的业务
     *
     * @return the modelId
     */
    public String getModelId() {
        return modelId;
    }

    /**
     * 导航对应的业务
     *
     * @param modelId the modelId to set
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    /**
     * 子导航
     *
     * @return the children
     */
    public List<NavItem> getChildren() {
        return children;
    }

    /**
     * 子导航
     *
     * @param children the children to set
     */
    public void setChildren(List<NavItem> children) {
        this.children = children;
    }

}
