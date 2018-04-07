/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.lock;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 锁工具
 *
 * @author zhuqing
 */
public class LockUtil {

    private static final Set lockSet = new ConcurrentSkipListSet();

    /**
     * 加锁
     *
     * @param <T>
     * @param lo
     */
    public static <T> void lock(T lo) {
        lockSet.add(lo.hashCode());
    }

    /**
     * 解锁
     *
     * @param <T>
     * @param lo
     */
    public static <T> void unLock(T lo) {
        lockSet.remove(lo.hashCode());
    }

    /**
     * 是否已加锁
     *
     * @param <T>
     * @param lo
     * @return
     */
    public static <T> boolean isLocked(T lo) {
        return lockSet.contains(lo.hashCode());
    }
}
