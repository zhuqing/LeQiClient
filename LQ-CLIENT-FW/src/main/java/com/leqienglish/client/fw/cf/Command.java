/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.cf;

import com.leqienglish.client.fw.LogFacade;
import com.leqienglish.client.fw.app.AbstractApplication;
import com.leqienglish.client.fw.root.uf.RootModel;

import com.leqienglish.client.fw.uf.FXMLModel;
import com.leqienglish.client.fx.util.concurrent.HipExecutors;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javax.annotation.Resource;
import org.controlsfx.dialog.ExceptionDialog;
import org.springframework.util.StopWatch;

/**
 *
 * @author duyi
 */
public abstract class Command extends LogFacade {

    private Integer pageNum;

    private Integer pageSize;

    protected boolean wait = true;

    private FXMLModel fxmlModel;

   

    @Resource(name = "rootModel")
    private RootModel rootModel;

    private final EventHandler lockScence = new EventHandler() {
        @Override
        public void handle(Event event) {
            event.consume();
        }
    };

    /**
     * Hip所使用的线程池
     */
    private static ExecutorService threadPool;

    private final Map<String, Object> parameters = new HashMap<>();

    protected abstract void getAppData() throws Exception;

    protected abstract void getServiceData() throws Exception;

    protected abstract void computer() throws Exception;

    protected abstract void doView() throws Exception;

    private void init() {
        parameters.clear();
    }

    public final void doCommand() {
        doCommand(null);
    }

    public final void doCommand(Scene scene) {
        StopWatch stopWatch = new StopWatch();
        init();
        try {
            stopWatch.start(this.getClass() + ".getAppData()");
            getAppData();
            stopWatch.stop();
            waitShow(Boolean.TRUE, scene);
            Task<Object> task = new Task<Object>() {
                @Override
                protected Object call() throws Exception {
                    stopWatch.start(Command.this.getClass() + ".getServiceData()");
                    getServiceData();
                    stopWatch.stop();
                    stopWatch.start(Command.this.getClass() + ".computer()");
                    computer();
                    stopWatch.stop();
                    return null;
                }
            };
            task.runningProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (oldValue && !newValue) {
                        waitShow(Boolean.FALSE, scene);
                        try {
                            stopWatch.start(Command.this.getClass() + ".doView()");
                            doView();
                            stopWatch.stop();
                        } catch (Exception ex) {
                            if (!"Can't start StopWatch: it's already running".equals(ex.getMessage())) {
                                showExceptionDialog(ex, scene);
                                getLogger().error(this.getClass() + ":doView:Err", ex);
                            }
                        }
                        getLogger().info(stopWatch.prettyPrint());
                    }
                }
            });
            task.exceptionProperty().addListener(new ChangeListener<Throwable>() {
                @Override
                public void changed(ObservableValue<? extends Throwable> observable, Throwable oldValue, Throwable newValue) {
                    newValue.printStackTrace();
                    showExceptionDialog(newValue, scene);
                    getLogger().info(stopWatch.prettyPrint());
                }
            });
            getThreadPool().execute(task);
        } catch (Exception ex) {
            ex.printStackTrace();
            waitShow(Boolean.FALSE, scene);
            getLogger().error(":doCommand:ERR", ex);
        }
    }

    private void showExceptionDialog(Throwable throwable, Scene scene) {
        ExceptionDialog exceptionDialog = new ExceptionDialog(throwable);
        exceptionDialog.setTitle("出错了!!!");
        exceptionDialog.setHeaderText(throwable.getMessage());
        exceptionDialog.setContentText("");
        DialogPane dialogPane = exceptionDialog.getDialogPane();
        Label label = (Label) dialogPane.getContent();
        label.setVisible(false);
        exceptionDialog.showAndWait();
        waitShow(Boolean.FALSE, scene);
    }

    private void waitShow(Boolean show, Scene scene) {
        if (wait) {
            if (scene == null) {
                rootModel.setWaitShow(show);
            } else {
                if (show) {
                    scene.addEventFilter(EventType.ROOT, lockScence);
                } else {
                    scene.removeEventFilter(EventType.ROOT, lockScence);
                }
            }
        }
    }

    public static ExecutorService getThreadPool() {
        if (threadPool == null) {
            threadPool = HipExecutors.getSingleThreadExecutor();
        }
        return threadPool;
    }

    protected FXMLModel getModel(String key) {
        return (FXMLModel) AbstractApplication.getContext().getBean(key);
    }

    protected Map<String, Object> getParameters() {
        return parameters;
    }

    protected Object getParameters(String key) {
        return parameters.get(key);
    }

    protected void putParameters(Map<String, Object> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            this.parameters.put(key, value);
        }
    }

    protected void putParameters(String key, Object obj) {
        this.parameters.put(key, obj);
    }


    public FXMLModel getFxmlModel() {
        return fxmlModel;
    }

    public void setFxmlModel(FXMLModel fxmlModel) {
        this.fxmlModel = fxmlModel;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
