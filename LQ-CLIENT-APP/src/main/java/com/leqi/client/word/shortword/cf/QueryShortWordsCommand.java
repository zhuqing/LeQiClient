/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortword.cf;

import com.leqi.client.version.cf.*;
import com.leqi.client.book.article.cf.*;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.uf.BookModel;
import com.leqi.client.version.uf.VersionModel;
import com.leqi.client.word.shortword.uf.ShortWordModel;
import com.leqienglish.client.fw.cf.Command;
import static com.leqienglish.client.fw.cf.Command.MESSAGE;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import com.leqienglish.util.file.FileUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Resource;


import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.version.Version;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryShortWordsCommand")
public class QueryShortWordsCommand extends Command {

    @Resource(name = "ShortWordModel")
    private ShortWordModel shortWordModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        String text = (String) param.get(DATA);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("text", text);

        ShortWord[] words = this.restClient.get("/shortWord/findByText", parameter, ShortWord[].class);

        this.putParameters(DATA, words);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {

        ShortWord[] arr = (ShortWord[]) this.getParameters(DATA);
        if(param.get(CONSUMER)!=null){
            Consumer consumer = (Consumer) param.get(CONSUMER);
            consumer.accept(arr);
        }

        this.shortWordModel.getWords().setAll(arr);

    }

}
