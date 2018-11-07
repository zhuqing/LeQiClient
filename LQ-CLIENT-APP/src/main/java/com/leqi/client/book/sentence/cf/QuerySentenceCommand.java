/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.cf;

import com.leqi.client.book.sentence.uf.SentenceModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.util.exception.LQExceptionUtil;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.sentence.Sentence;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("QuerySentenceCommand")
public class QuerySentenceCommand extends Command {

    @Resource(name = "SentenceModel")
    private SentenceModel sentenceModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

        LQExceptionUtil.required(param.get(DATA) != null, "没有查询文本");
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
       
        MultiValueMap<String, String> textMap = new LinkedMultiValueMap();
        textMap.add("text", param.get(DATA).toString());
        
        Sentence[] arr = this.restClient.get("sentence/findByText", textMap, Sentence[].class);

        this.putParameters(DATA, arr);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {

     
        Sentence[] sentences = (Sentence[]) this.getParameters(DATA);
        if (sentences!=null) {

            this.sentenceModel.getSentences().setAll(sentences);
          
        }

    }

}
