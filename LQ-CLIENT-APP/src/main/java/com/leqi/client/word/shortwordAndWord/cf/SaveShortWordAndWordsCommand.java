package com.leqi.client.word.shortwordAndWord.cf;

import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.util.exception.LQExceptionUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.shortword.ShortWordAndWord;
import xyz.tobebetter.entity.word.Word;

import java.util.Map;
import java.util.function.Consumer;

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
@Component("SaveShortWordAndWordsCommand")
public class SaveShortWordAndWordsCommand extends Command {
    

 

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        LQExceptionUtil.required(param.get(DATA)!=null  , "没有数据！！");
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        ShortWordAndWord shortWordAndWord = (ShortWordAndWord) param.get(DATA);

       shortWordAndWord = this.restClient.post("shortWordAndWord/create",shortWordAndWord, null, ShortWordAndWord.class);
       this.putParameters(DATA, shortWordAndWord);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
     
        if(param.get(CONSUMER) != null){
            Consumer consumer = (Consumer) param.get(CONSUMER);
            consumer.accept(this.getParameters(DATA));
           
        }
     
    }
    
}
