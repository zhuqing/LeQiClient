/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author duyi
 */
public class HipExecutors {

    private static ExecutorService executorService;

    public synchronized static ExecutorService getSingleThreadExecutor() {
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(2);
        }
        return executorService;
    }
}
