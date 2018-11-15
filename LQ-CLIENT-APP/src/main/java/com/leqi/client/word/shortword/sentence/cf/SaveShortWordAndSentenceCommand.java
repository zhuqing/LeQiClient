/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortword.sentence.cf;

import com.leqi.client.word.shortword.sentence.uf.ShortWordAndSentenceModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentence;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentenceVO;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveShortWordAndSentenceCommand")
public class SaveShortWordAndSentenceCommand extends Command {

    @Resource(name = "ShortWordAndSentenceModel")
    private ShortWordAndSentenceModel shortWordAndSentenceModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        ShortWordAndSentence content = (ShortWordAndSentence) param.get(DATA);

        LQExceptionUtil.required(content != null, "ShortWord不能为null");
       
     
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        ShortWordAndSentenceVO content = (ShortWordAndSentenceVO) param.get(DATA);


       ShortWordAndSentence data = null;

        if (content.getId() == null) {
            data = this.restClient.post("/shortWordAndSentence/create", content, null, ShortWordAndSentence.class);
           this.putParameters(INSERT, true);
        } else {
            data = this.restClient.put("/shortWordAndSentence/update", content, null, ShortWordAndSentence.class);
           this.putParameters(INSERT, false);
        }

        content.setId(data.getId());
        this.putParameters(DATA, content);
    }
    
   

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Boolean isInsert =  (Boolean) this.getParameters(INSERT);
         ShortWordAndSentenceVO shortWordAndSentence = ( ShortWordAndSentenceVO) this.getParameters(DATA);
        if(isInsert){
             AlertUtil.showInformation(AlertUtil.SAVE_SUCCESS);
              this.shortWordAndSentenceModel.getShortWordAndSentenceVOS().add(shortWordAndSentence);
        }else{
             AlertUtil.showInformation(AlertUtil.UPDATE_SUCCESS);
            ShortWordAndSentenceVO[] arr = this.shortWordAndSentenceModel.getShortWordAndSentenceVOS().toArray(new ShortWordAndSentenceVO[0]);
             this.shortWordAndSentenceModel.getShortWordAndSentenceVOS().clear();
             this.shortWordAndSentenceModel.getShortWordAndSentenceVOS().setAll(arr);
        }
        
       

        
       

    }

}
