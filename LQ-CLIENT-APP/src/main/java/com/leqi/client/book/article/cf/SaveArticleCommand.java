/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.cf;

import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.util.exception.LQExceptionUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveArticleCommand")
public class SaveArticleCommand extends Command {

     @Resource(name = "BookModel")
    private BookModel bookModel;
     
     
     @Resource(name = "ArticleModel")
    private ArticleModel articleModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        Content content = (Content) param.get(DATA);

        LQExceptionUtil.required(content != null, "content不能为null");
        LQExceptionUtil.required(content.getId() == null, "content已经保存过了");
        //LQExceptionUtil.required(content.getAudioPath() != null, "没有音频文件");

        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            MultiValueMap<String, Object> value = new LinkedMultiValueMap();
            value.add("file", new FileSystemResource(new File(content.getImagePath())));
            this.putParameters("image", value);
        }

        MultiValueMap<String, Object> audioMap = new LinkedMultiValueMap();
        audioMap.add("file", new FileSystemResource(new File(content.getAudioPath())));
        this.putParameters("audio", audioMap);
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        Content content = (Content) param.get(DATA);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("type", "mp3");

        String path = this.restClient.upload("/file/uploadAudio", (MultiValueMap<String, Object>) this.getParameters("audio"), null,String.class);
        content.setAudioPath(path);
        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            paramMap.put("type", "jpg");
            path = this.restClient.upload("/file/uploadImage", (MultiValueMap<String, Object>) this.getParameters("image"), null, String.class);
            content.setImagePath(path);
        }else{
            content.setImagePath("");
        }

        content = this.restClient.post("/english/content/create", content, null, Content.class);
        this.putParameters(DATA, content);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Content content = (Content) this.getParameters(DATA);
        bookModel.getArticles().add(content);
        articleModel.setContent(content);

    }

}
