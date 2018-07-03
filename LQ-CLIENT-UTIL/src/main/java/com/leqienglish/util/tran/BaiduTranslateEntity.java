/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.tran;

/**
 *百度翻译后的转换实体
 * @author zhuleqi
 */
public class BaiduTranslateEntity {

    private String src;
    private String dst;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    @Override
    public String toString() {
        return "BaiduTranslateEntity{" + "src=" + src + ", dst=" + dst + '}';
    }

}
