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
import xyz.tobebetter.entity.english.Word;
import xyz.tobebetter.entity.english.WordAndContent;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("SaveWordAndContentCommand")
public class SaveWordAndContentCommand extends SaveWordsCommand {

    @Resource(name = "fileService")
    private FileService fileService;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        List<Word> words = (List<Word>) param.get(DATA);
        String contentId = (String) param.get(ID);
        List<String> wordIds = saveWord(words);
        wordIds.stream().map((wordId) -> {
            WordAndContent wc = new WordAndContent();
            wc.setContentId(contentId);
            wc.setWordId(wordId);
            return wc;
        }).forEach((wc) -> {
            try {
                restClient.post("english/wordandcontent/create", wc, null, WordAndContent.class);
            } catch (Exception ex) {
                Logger.getLogger(SaveWordAndContentCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }


    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation("保存完成");
    }

}
