/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.word.cf;

import com.google.common.base.Objects;
import com.leqi.client.book.word.uf.WordModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Word;
import xyz.tobebetter.entity.english.WordAndContent;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("DeleteWordAndContentCommand")
public class DeleteWordAndContentCommand extends Command {

    @Resource(name = "WordModel")
    private WordModel wordModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        Word word = (Word) param.get(DATA);
        parameter.add("wordId", word.getId());
        parameter.add("contentId", wordModel.getArticle().getId());

         this.restClient.delete("english/wordandcontent/deleteByWordIdAndContentId", null, parameter, String.class);

        param.put(ENTITY, "words");

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        String words = (String) param.get(ENTITY);
        Word word = (Word) param.get(DATA);
        if (words != null) {
            wordModel.getWords().remove(word);
            AlertUtil.showInformation("删除成功");
        }
    }

}
