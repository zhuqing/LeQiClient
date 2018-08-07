/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.word.cf;

import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.sf.FileService;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.file.FileUtil;
import com.leqienglish.util.tran.iciba.ICIBATranslateUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("SaveWordsCommand")
public class SaveWordsCommand extends Command {

    @Resource(name = "fileService")
    private FileService fileService;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    protected List<String> saveWord(List<Word> words) throws InterruptedException, Exception {

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        List<String> wordIds = new ArrayList<>();
        for (Word word : words) {
            if (word.getWord() == null || word.getWord().isEmpty()) {
                continue;
            }

            parameter.clear();
            parameter.add("word", word.getWord().toLowerCase());
            Word hasWord = this.restClient.get("english/word/findByWord", parameter, Word.class);
            if (hasWord != null) {
                wordIds.add(hasWord.getId());
                continue;
            }

            ICIBATranslateUtil.transResult(word.getWord(), (json) -> {

                Word newWord = Word.icibaJsontoWord(json);

                try {
                    changePath(newWord);
                    newWord = restClient.post("english/word/create", newWord, null, Word.class);
                    wordIds.add(newWord.getId());
                } catch (Exception ex) {
                    Logger.getLogger(SaveWordsCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        }

        return wordIds;
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        

    }

    private void changePath(Word word) throws Exception {

        String path = downAndUpload(word.getAmAudionPath(), word.getWord(),"am");
        word.setAmAudionPath(path);

        path = downAndUpload(word.getEnAudioPath(), word.getWord(),"en");
        word.setEnAudioPath(path);

        path = downAndUpload(word.getTtsAudioPath(), word.getWord(),"tts");
        word.setTtsAudioPath(path);

    }

    private String downAndUpload(String urlPath, String word,String type) throws IOException, Exception {
        if(urlPath == null || urlPath.isEmpty()){
            return null;
        }
        String localPath = FileUtil.wordFilelPath(word,type);
        if(!FileUtil.fileExit(localPath)){
             this.fileService.downLoad(urlPath, localPath, MediaType.ALL);

        }
        
        MultiValueMap<String, Object> audioMap = new LinkedMultiValueMap();
        audioMap.add("file", new FileSystemResource(new File(localPath)));

        MultiValueMap<String, String> param = new LinkedMultiValueMap();
        param.add("word", word);
        param.add("type", type);

        return this.restClient.upload("/file/uploadWordAudio", audioMap, param, String.class);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        
    }

}
