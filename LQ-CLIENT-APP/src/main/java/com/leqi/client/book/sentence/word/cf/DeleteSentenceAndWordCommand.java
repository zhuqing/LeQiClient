/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.word.cf;

import com.leqi.client.book.sentence.cf.*;
import com.leqi.client.book.sentence.uf.SentenceModel;
import com.leqi.client.book.sentence.word.uf.SentenceAndWordModel;
import com.leqi.client.book.word.cf.*;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.sf.FileService;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import com.leqienglish.util.file.FileUtil;
import com.leqienglish.util.tran.iciba.ICIBATranslateUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.word.Word;
import xyz.tobebetter.entity.content.WordAndContent;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.english.sentence.SentenceAndWord;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("DeleteSentenceAndWordCommand")
public class DeleteSentenceAndWordCommand extends Command {
  @Resource(name = "SentenceAndWordModel")
    private SentenceAndWordModel sentenceAndWordModel;
    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
      
         LQExceptionUtil.required(param.get(DATA) != null, "没有要删除的数据");
        // LQExceptionUtil.required(content.getId() == null, "content已经保存过了");

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        SentenceAndWord sentence = (SentenceAndWord) param.get(DATA);
        
        if(sentence == null){
            return;
        }
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap();
        parameter.add("id", sentence.getId());
       String id = this.restClient.delete("sentence/delete", null, parameter, String.class);
       
       this.putParameters(DATA, id);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        SentenceAndWord sentence = (SentenceAndWord) param.get(DATA);
       
        String id  = (String) this.getParameters(DATA);
        if(id != null){
             AlertUtil.showInformation(AlertUtil.DELETE_SUCCESS);
        }
        this.sentenceAndWordModel.getSentenceAndWords().remove(sentence);
    }

}
