/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.word.cf;

import com.leqi.client.book.word.uf.WordModel;
import com.leqienglish.client.fw.cf.Command;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("QueryWordsCommand")
public class QueryWordsCommand extends Command {

    @Resource(name = "WordModel")
    private WordModel wordModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("contentId", (String) param.get(ID));

        Word[] words = this.restClient.get("english/word/findByContentId", parameter, Word[].class);

        param.put(DATA, words);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Word[] words = (Word[]) param.get(DATA);
        wordModel.getWords().setAll(words);
    }

}
