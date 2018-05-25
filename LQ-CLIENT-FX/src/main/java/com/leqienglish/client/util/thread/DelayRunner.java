/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.util.thread;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javafx.application.Platform;

/**
 * 延时提交，延时可叠加
 *
 * @author zhuqing
 */
public class DelayRunner {

    /**
     * 延时1000毫秒
     */
    public final static Long LONG = 1000L;
    /**
     * 延时300毫秒
     */
    public final static Long SHORTEST = 300L;
    /**
     * 延时500毫秒
     */
    public final static Long NORMAL = 500L;

    private static Timer timer;

    private final static Map<Object, Long> timerMap = new ConcurrentHashMap<>();
    private final static Map<Object, Runnable> timerRunner = new ConcurrentHashMap<>();
    private final static Map<Object, Runnable> timerRunnerInThread = new ConcurrentHashMap<>();

    /**
     * 取消所有的Timer
     */
    public final static void cancelAllTimer() {
        if (timer == null) {
            return;
        }
        timer.cancel();

    }

    /**
     * 是否已经执行
     *
     * @param node
     * @return
     */
    public synchronized static Boolean isRunning(Object node) {
        return timerMap.containsKey(node);
    }

    private static void initTimer() {
        if (timer != null) {
            return;
        }

        timer = new Timer();
        timer.schedule(createTimerTask(), 0L, 100L);
    }

    private static TimerTask createTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                for (Object node : timerMap.keySet()) {
                    Long during = timerMap.get(node);
                    during = during - 100;
                    if (during <= 0) {
                        if (timerRunner.containsKey(node)) {
                            runInApplication(timerRunner.get(node));
                            timerRunner.remove(node);
                        }
                        if (timerRunnerInThread.containsKey(node)) {

                            timerRunnerInThread.get(node).run();
                            timerRunnerInThread.remove(node);
                        }
                        timerMap.remove(node);
                    } else {
                        timerMap.put(node, during);
                    }
                }
            }
        };
    }

    /**
     *
     */
    public static void runInApplication(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }

    /**
     * 开始延伸
     *
     * @param node
     */
    public synchronized static void delayRunner(Object node) {
        delayRunner(node, NORMAL, null);
    }

    /**
     * 开始计时
     *
     * @param node
     * @param millSeconds
     */
    public synchronized static void delayRunner(Object node, Long millSeconds, Runnable runner) {
        if (timer == null) {
            initTimer();
        }
        timerRunner.put(node, runner);
        timerMap.put(node, millSeconds);
    }

    /**
     * 开始计时
     *
     * @param node
     * @param millSeconds
     */
    public synchronized static void delayRunnerInThread(Object node, Long millSeconds, Runnable runner) {

        if (timer == null) {
            initTimer();
        }
        timerMap.put(node, millSeconds);
        timerRunnerInThread.put(node, runner);
    }

    /**
     * 开始计时 , 默认500毫秒
     *
     * @param node
     */
    public synchronized static void delayRunner(Object node, Runnable runner) {
        delayRunner(node, NORMAL, runner);
    }

    public static void main(String[] args) {
        String s = UUID.randomUUID().toString();
        // Long dur = Long.valueOf((Math.random() * 10000 + 100) + "");
        final long start = System.currentTimeMillis();
        System.err.print("=====" + s);
        for (int i = 0; i < 10000; i++) {

            DelayRunner.delayRunner(s, 1000L, () -> {
                System.err.print(s);
                System.err.print(System.currentTimeMillis() - start);
            });
        }
    }
    
    
    /**
     * millSeconds 毫秒后在Application线程下执行
     *
     * @param runnable
     * @param millSeconds
     */
    public static synchronized void runLaterInApplicationThread(Runnable runnable, Long millSeconds) {
        DelayRunner.delayRunner(UUID.randomUUID().toString(), millSeconds, runnable);
    }

    /**
     * millSeconds 毫秒后执行
     *
     * @param runnable
     * @param millSeconds
     */
    public static synchronized void runLater(Runnable runnable, Long millSeconds) {
         DelayRunner.delayRunnerInThread(UUID.randomUUID().toString(), millSeconds, runnable);
    }


}
