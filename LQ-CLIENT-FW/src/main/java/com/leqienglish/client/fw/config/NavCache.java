/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 导航与缓存的关系
 *
 * @author duyi
 */
public class NavCache {

    private String businessId;

    private Map<String, Long> cacheUseTime = new HashMap<>();

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Map<String, Long> getCacheUseTime() {
        return cacheUseTime;
    }

    public void setCacheUseTime(Map<String, Long> cacheUseTime) {
        this.cacheUseTime = cacheUseTime;
    }

}
