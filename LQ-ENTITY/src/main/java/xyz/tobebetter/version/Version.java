/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.tobebetter.version;

import xyz.tobebetter.entity.Entity;

/**
 *
 * @author zhuleqi
 */
public class Version extends Entity {
    /**
     * 版本号
     */
    private long versionNo;
    /**
     * 版本编码
     */
    private String versionCode;
    
    /**
     * 版本信息
     */
    private String message;
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * Android IOS
     */
    private Integer type;

    /**
     * @return the versionNo
     */
    public long getVersionNo() {
        return versionNo;
    }

    /**
     * @param versionNo the versionNo to set
     */
    public void setVersionNo(long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * @return the versionCode
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode the versionCode to set
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }
}
