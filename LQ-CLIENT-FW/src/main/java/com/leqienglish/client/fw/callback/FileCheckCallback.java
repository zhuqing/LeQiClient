/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.callback;

import com.leqienglish.client.comman.cf.DownLoadFileCommand;
import com.leqienglish.client.fw.app.AbstractApplication;
import com.leqienglish.util.file.FileUtil;
import com.leqienglish.util.task.FutureTaskUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Callback;

/**
 *
 * @author zhuleqi
 */
public class FileCheckCallback implements Callback<String, String> {

    @Override
    public String call(String p) {
        if (p == null || p.isEmpty()) {
            return p;
        }
        if (p.contains(FileUtil.APP_NAME)) {
            return p;
        }

        try {
            String localFilePath = FileUtil.toLocalFilePath(p);
            File localFile = new File(localFilePath);
            if (localFile.exists()) {
                return localFilePath;
            }

            DownLoadFileCommand downLoadFileCommand = (DownLoadFileCommand) AbstractApplication.getContext().getBean("DownLoadFileCommand");
            FutureTaskUtil.run(() -> {
                Map<String, Object> map = new HashMap<>();
                map.put(DownLoadFileCommand.FILE_PATH, p);
                downLoadFileCommand.doCommand(map);
                return null;
            });
            return localFilePath;
        } catch (Exception ex) {
            Logger.getLogger(FileCheckCallback.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;
    }

}
