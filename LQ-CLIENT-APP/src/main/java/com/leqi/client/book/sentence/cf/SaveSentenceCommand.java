/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.sentence.cf;

import com.leqi.client.book.sentence.uf.SentenceModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import com.leqienglish.util.file.FileUtil;
import java.io.File;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.sentence.Sentence;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("SaveSentenceCommand")
public class SaveSentenceCommand extends Command {

    @Resource(name = "SentenceModel")
    private SentenceModel sentenceModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        Sentence sentence = sentenceModel.getCurrentSentence();
        LQExceptionUtil.required(sentence != null, "当前编辑的不能为null");
        // LQExceptionUtil.required(content.getId() == null, "content已经保存过了");

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        Sentence sentence = sentenceModel.getCurrentSentence();
        
        //上传音频文件
        if (FileUtil.getInstence().fileExit(sentence.getAudioPath())) {
            MultiValueMap<String, Object> audioMap = new LinkedMultiValueMap();
            audioMap.add("file", new FileSystemResource(new File(sentence.getAudioPath())));

            String path = this.restClient.upload("/file/uploadAudio", audioMap, null, String.class);
            sentence.setAudioPath(path);
        }

        //上传图片文件
        if (FileUtil.getInstence().fileExit(sentence.getImagePath())) {
            MultiValueMap<String, Object> audioMap = new LinkedMultiValueMap();
            audioMap.add("file", new FileSystemResource(new File(sentence.getImagePath())));
            String path = this.restClient.upload("file/uploadImage", audioMap, null, String.class);
            sentence.setImagePath(path);
        }
        
        if(sentence.getId()!=null){
           sentence =  this.restClient.put("sentence/update", sentence, null, Sentence.class);
           this.putParameters(INSERT, false);
        }else{
           sentence =  this.restClient.post("sentence/create", sentence, null, Sentence.class);
           this.putParameters(INSERT, true);
        }
       
        this.putParameters(DATA, sentence);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {

        Boolean isInsert = (Boolean) this.getParameters(INSERT);
        Sentence sentence = (Sentence) this.getParameters(DATA);
        if(isInsert){
            this.sentenceModel.getSentences().add(sentence);
            AlertUtil.showInformation(AlertUtil.SAVE_SUCCESS);
        }else{
            Sentence[] arr = this.sentenceModel.getSentences().toArray(new Sentence[0]);
            this.sentenceModel.getSentences().clear();
            this.sentenceModel.getSentences().setAll(arr);
            AlertUtil.showInformation(AlertUtil.UPDATE_SUCCESS);
        }
        
    }

}
