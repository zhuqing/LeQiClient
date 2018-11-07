/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.word.cf;

import com.leqi.client.book.segment.word.cf.*;
import com.google.common.base.Objects;
import com.leqi.client.book.segment.word.uf.SegmentWordsModel;
import com.leqi.client.book.sentence.word.uf.SentenceAndWordModel;
import com.leqi.client.book.word.cf.SaveWordsCommand;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.util.exception.LQExceptionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;
import xyz.tobebetter.entity.word.Word;
import xyz.tobebetter.entity.english.play.AudioPlayPoint;
import xyz.tobebetter.entity.english.segment.WordAndSegment;
import xyz.tobebetter.entity.english.sentence.SentenceAndWord;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveSentenceAndWordCommand")
public class SaveSentenceAndWordCommand extends Command {

    @Resource(name = "SentenceAndWordModel")
    private SentenceAndWordModel sentenceAndWordModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        LQExceptionUtil.required(param.get(DATA)!=null, "没有要保持的数据");
                
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        SentenceAndWord  sentenceAndWord =  (SentenceAndWord) param.get(DATA);

        sentenceAndWord = this.restClient.post("sentenceAndWord/create", sentenceAndWord, null , SentenceAndWord.class);

      
        this.putParameters(DATA, sentenceAndWord);
    }
    
   

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
         SentenceAndWord  sentenceAndWord =  (SentenceAndWord)this.getParameters(DATA);
         if(sentenceAndWord == null){
             return;
         }
        sentenceAndWordModel.getSentenceAndWords().add(0, sentenceAndWord);
    }

}
