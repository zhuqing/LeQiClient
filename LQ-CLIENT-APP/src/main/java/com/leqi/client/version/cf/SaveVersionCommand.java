/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.version.cf;

import com.leqi.client.book.article.cf.*;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.uf.BookModel;
import com.leqi.client.version.uf.VersionModel;
import com.leqienglish.client.fw.cf.Command;
import static com.leqienglish.client.fw.cf.Command.MESSAGE;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import com.leqienglish.util.file.FileUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.version.Version;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveVersionCommand")
public class SaveVersionCommand extends Command {

    @Resource(name = "VersionModel")
    private VersionModel versionModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        Version content = (Version) param.get(DATA);

        LQExceptionUtil.required(content != null, "content不能为null");
       
        if (FileUtil.getInstence().fileExit(content.getFilePath())) {
            MultiValueMap<String, Object> value = new LinkedMultiValueMap();
            value.add("file", new FileSystemResource(new File(content.getFilePath())));
            this.putParameters("file", value);
        }

      

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        Version content = (Version) param.get(DATA);

        if (this.getParameters("file") != null) {
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
            paramMap.add("type", getType(content));
            String path = this.restClient.upload("/file/uploadVersionFile", (MultiValueMap<String, Object>) this.getParameters("file"), paramMap, String.class);
            content.setFilePath(path);
        }

      

        if (content.getId() == null) {
            content = this.restClient.post("/version/create", content, null, Version.class);
            this.putParameters(MESSAGE, AlertUtil.SAVE_SUCCESS);
        } else {
            content = this.restClient.put("/version/update", content, null, Version.class);
            this.putParameters(MESSAGE, AlertUtil.UPDATE_SUCCESS);
        }

        this.putParameters(DATA, content);
    }
    
    private String getType(Version version){
        switch(version.getType()){
            case Consistent.ANDROID:
                return FileUtil.VERSION_ADNROID_TYPE;
            case Consistent.IOS:
                return FileUtil.VERSION_IOS_TYPE;
        }
        
        return FileUtil.VERSION_DES_TYPE;
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation((String) this.getParameters(MESSAGE));
        Version content = (Version) this.getParameters(DATA);

        versionModel.getVersionList().add(0, content);

    }

}
