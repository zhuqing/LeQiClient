/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.entity.english.shortword;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuleqi
 */
public class SegmentAndShortWord extends Entity{
    
    private String segmentId;
    private String shortWordId;

    /**
     * @return the segmentId
     */
    public String getSegmentId() {
        return segmentId;
    }

    /**
     * @param segmentId the segmentId to set
     */
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    /**
     * @return the shortWordId
     */
    public String getShortWordId() {
        return shortWordId;
    }

    /**
     * @param shortWordId the shortWordId to set
     */
    public void setShortWordId(String shortWordId) {
        this.shortWordId = shortWordId;
    }
}
