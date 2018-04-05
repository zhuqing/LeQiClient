/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.config;

/**
 *
 * @author duyi
 */
public class AppConfig {

    private Double width = 0D;

    private Double high = 0D;

    private int cpuThread = 2;

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public int getCpuThread() {
        return cpuThread;
    }

    public void setCpuThread(int cpuThread) {
        this.cpuThread = cpuThread;
    }

}
