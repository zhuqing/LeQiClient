/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.common.cf;

import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.util.exception.LQExceptionUtil;
import com.leqienglish.util.file.FileUtil;
import java.io.File;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("DownLoadFileCommand")
public class DownLoadFileCommand extends Command {

    public static final String FILE_PATH = "FILE_PATH";
    public static final String REAL_FILE_PATH = "REAL_FILE_PATH";

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        String filePath = (String) param.get(FILE_PATH);

        LQExceptionUtil.required(!(filePath == null || filePath.isEmpty()), "文件路径不能为null");
       
        filePath = FileUtil.toLocalFilePath(filePath);
        FileUtil.createDir(filePath);
        param.put(REAL_FILE_PATH, filePath);

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        String realFilePath = (String) param.get(REAL_FILE_PATH);
        if(FileUtil.hasFileOrCreate(realFilePath)){
            return;
        }
        String filePath = (String) param.get(FILE_PATH);
        this.restClient.downLoad("file/download?path=" + filePath, realFilePath, new Consumer<Double>() {
            @Override
            public void accept(Double t) {
                System.err.println("=" + t);
            }
        });
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
