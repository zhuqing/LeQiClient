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
import java.util.Map;
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
@Component("DeleteShortWordCommand")
public class DeleteShortWordCommand extends Command {

    @Resource(name = "ShortWordModel")
    private ShortWordModel shortWordModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        ShortWord content = (ShortWord) param.get(DATA);

        LQExceptionUtil.required(content != null, "ShortWord不能为null");

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        ShortWord content = (ShortWord) param.get(DATA);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("id", content.getId());

        this.restClient.delete("/shortWord/delete", null, parameter, String.class);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {

        ShortWord content = (ShortWord)param.get(DATA);
        AlertUtil.showInformation(AlertUtil.DELETE_SUCCESS);

        this.shortWordModel.getWords().remove(content);

    }

}
