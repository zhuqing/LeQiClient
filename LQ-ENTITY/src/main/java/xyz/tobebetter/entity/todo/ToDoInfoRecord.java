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
public class ToDoInfoRecord extends Entity{
    private String userId;
    private String toDoInfoId;

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
     * @return the toDoInfoId
     */
    public String getToDoInfoId() {
        return toDoInfoId;
    }

    /**
     * @param toDoInfoId the toDoInfoId to set
     */
    public void setToDoInfoId(String toDoInfoId) {
        this.toDoInfoId = toDoInfoId;
    }
}
