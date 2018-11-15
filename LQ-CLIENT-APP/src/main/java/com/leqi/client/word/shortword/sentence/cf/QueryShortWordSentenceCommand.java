/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortword.sentence.cf;


import com.leqi.client.word.shortword.sentence.uf.ShortWordAndSentenceModel;
import com.leqi.client.word.shortword.uf.ShortWordModel;
import com.leqienglish.client.fw.cf.Command;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentence;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentenceVO;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryShortWordSentenceCommand")
public class QueryShortWordSentenceCommand extends Command {

    @Resource(name = "ShortWordAndSentenceModel")
    private ShortWordAndSentenceModel shortWordAndSentenceModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        ShortWord shortWord = (ShortWord) param.get(DATA);

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
       ShortWord shortWord = (ShortWord) param.get(DATA);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("shortWordId", shortWord.getId());

        ShortWordAndSentenceVO[] words = this.restClient.get("/shortWordAndSentence/findByShortWordId", parameter, ShortWordAndSentenceVO[].class);

        this.putParameters(DATA, words);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {

        ShortWordAndSentenceVO[] arr = (ShortWordAndSentenceVO[]) this.getParameters(DATA);

        this.shortWordAndSentenceModel.getShortWordAndSentenceVOS().setAll(arr);

    }

}
