/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author zhuqing
 */
public class FutureTaskUtil {

    /**
     * 调用futureTask 返回调用后的结果
     *
     * @param <T>
     * @param <R>
     * @param threadNumber
     * @param datas
     * @param consumer
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static <T, R> void call(int threadNumber, final List<T> datas, final Consumer<T> consumer) throws InterruptedException, ExecutionException {

        List<FutureTask<R>> taskPool = new ArrayList<>(threadNumber);

        int endIndex = Math.min(threadNumber, datas.size());

        for (int i = 0; i < endIndex; i++) {
            taskPool.add((FutureTask<R>) createFutureTaskAndRun(i, datas, consumer));
        }

        int startIndex = endIndex;

        while (true) {
            //记录池中的任务完成的数据
            int finishedCount = 0;
            for (int index = 0; index < taskPool.size(); index++) {
                FutureTask<R> task = taskPool.get(index);
                if (task == null) {
                    finishedCount++;
                    continue;
                }

                //所有的数据都已经放入task中
                if (startIndex >= datas.size()) {
                    task.get();
                    taskPool.set(index, null);
                    continue;
                }

                //任务是否执行完成
                if (task.isDone()) {
                    taskPool.set(index, (FutureTask<R>) createFutureTaskAndRun(startIndex, datas, consumer));
                    startIndex++;
                    continue;
                }
            }

            if (finishedCount >= taskPool.size()) {
                break;
            }
        }
    }

    /**
     * 创建FutureTask 并运行线程
     *
     * @param <T>
     * @param <R>
     * @param index
     * @param datas
     * @param consumer
     * @return
     */
    private static <T, R> FutureTask<R> createFutureTaskAndRun(final int index, final List<T> datas, final Consumer<T> consumer) {
        if (index >= datas.size()) {
            return null;
        }
        FutureTask<R> task = new FutureTask<>(new Callable() {
            @Override
            public Object call() throws Exception {
                consumer.accept(datas.get(index));
                return null;
            }
        });

        //创建线程并启动
        new Thread(task).start();

        return task;
    }

    private static <T, R> FutureTask<R> createFutureTaskAndRun(final int index, final List<T> datas, final Function<T, R> function) {
        if (index >= datas.size()) {
            return null;
        }
        FutureTask task = new FutureTask<>(new Callable() {
            @Override
            public Object call() throws Exception {

                return function.apply(datas.get(index));
            }
        });

        //创建线程并启动
        new Thread(task).start();

        return task;
    }

    /**
     * 调用futureTask 返回调用后的结果
     *
     * @param <T>
     * @param <R>
     * @param threadNumber
     * @param datas
     * @param function
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static <T, R> List<R> call(int threadNumber, final List<T> datas, final Function<T, R> function) throws InterruptedException, ExecutionException {

        List<R> reasultData = new ArrayList<>(datas.size());
        List<FutureTask<R>> taskPool = new ArrayList<>(threadNumber);

        int endIndex = Math.min(threadNumber, datas.size());

        for (int i = 0; i < endIndex; i++) {
            taskPool.add(createFutureTaskAndRun(i, datas, function));
        }

        int startIndex = endIndex;

        while (true) {
            //记录池中的任务完成的数据
            int finishedCount = 0;
            for (int index = 0; index < taskPool.size(); index++) {
                FutureTask<R> task = taskPool.get(index);
                if (task == null) {
                    finishedCount++;
                    continue;
                }

                //所有的数据都已经放入task中
                if (startIndex >= datas.size()) {
                    reasultData.add(task.get());
                    taskPool.set(index, null);
                    continue;
                }

                //任务是否执行完成
                if (task.isDone()) {
                    reasultData.add(task.get());
                    taskPool.set(index, createFutureTaskAndRun(startIndex, datas, function));
                    startIndex++;
                    continue;
                }
            }

            if (finishedCount >= taskPool.size()) {
                break;
            }
        }

        return reasultData;
    }

  
    /**
     * 异步执行callable
     *
     * @param <T>
     * @param callable
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public  static <T> T run(Callable runnable) throws InterruptedException, ExecutionException {
        FutureTask<T> task = new FutureTask<T>(runnable);
        Executors.newCachedThreadPool().execute(task);
        return task.get();
    }
    

}
