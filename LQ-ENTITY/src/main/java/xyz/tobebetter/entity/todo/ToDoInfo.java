/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.todo;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuleqi
 */
public class ToDoInfo extends Entity{
    /**
   *创建者用户的ID
     */
    private String userId;
    /**
     * todo名称
     */
    private String name;
    
    /**
     * 执行时间cron 格式
     */
    private String excuteDate;
    

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
}
