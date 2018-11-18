package com.leqi.client.word.shortwordAndWord.cf;

import com.leqi.client.word.shortword.uf.ShortWordModel;
import com.leqi.client.word.shortwordAndWord.uf.ShortWordAndWordModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.util.exception.LQExceptionUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndWordVO;
import xyz.tobebetter.entity.word.Word;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
@Component("QueryShortWordAndWordsCommand")
public class QueryShortWordAndWordsCommand extends Command {


    @Resource(name = "ShortWordAndWordModel")
    private ShortWordAndWordModel shortWordAndWordModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        LQExceptionUtil.required(param.get(DATA)!=null  , "没有查询文本");
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        ShortWord shortWord = (ShortWord) param.get(DATA);

        MultiValueMap<String,String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("shortWordId", shortWord.getId());
       Word[] words = this.restClient.get("english/word/findByShortWordId", paramMap, Word[].class);

        List<ShortWordAndWordVO> shortWordAndWordVOList = new ArrayList<ShortWordAndWordVO>(words.length);
        for(Word word : words){
            ShortWordAndWordVO shortWordAndWordVO = new ShortWordAndWordVO();
            shortWordAndWordVO.setShortWord(shortWord.getWord());
            shortWordAndWordVO.setShortWordId(shortWord.getId());
            shortWordAndWordVO.setWord(word.getWord());
            shortWordAndWordVO.setWordId(word.getId());
            shortWordAndWordVOList.add(shortWordAndWordVO);
        }

       this.putParameters(DATA, shortWordAndWordVOList);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
     
        if(param.get(CONSUMER) != null){
            Consumer consumer = (Consumer) param.get(CONSUMER);
            consumer.accept(this.getParameters(DATA));
           
        }
     
    }
    
}
