package com.leqi.client.word.cf;

import com.leqi.client.book.sentence.word.uf.SentenceAndWordModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.util.exception.LQExceptionUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.word.Word;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("LQQueryWordsCommand")
public class LQQueryWordsCommand extends Command {
    

 

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        LQExceptionUtil.required(param.get(DATA)!=null  , "没有查询文本");
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String,String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("word", param.get(DATA).toString());
       Word[] words = this.restClient.get("english/word/search", paramMap, Word[].class);
       this.putParameters(DATA, words);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
     
        if(param.get(CONSUMER) != null){
            Consumer consumer = (Consumer) param.get(CONSUMER);
            consumer.accept(this.getParameters(DATA));
           
        }
     
    }
    
}
