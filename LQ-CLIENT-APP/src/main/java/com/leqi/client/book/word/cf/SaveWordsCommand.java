/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.word.cf;

import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.tran.iciba.ICIBATranslateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.context.annotation.Lazy;
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
@Component("SaveWordsCommand")
public class SaveWordsCommand extends Command {

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        List<Word> words = (List<Word>) param.get(DATA);
        String contentId = (String) param.get(ID);
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
                    newWord = restClient.post("english/word/create", newWord, null, Word.class);
                    wordIds.add(newWord.getId());
                } catch (Exception ex) {
                    Logger.getLogger(SaveWordsCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        }

        wordIds.stream().map((wordId) -> {
            WordAndContent wc = new WordAndContent();
            wc.setContentId(contentId);
            wc.setWordId(wordId);
            return wc;
        }).forEach((wc) -> {
            try {
                restClient.post("english/wordandcontent/create", wc, null, WordAndContent.class);
            } catch (Exception ex) {
                Logger.getLogger(SaveWordsCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation("保存完成");
    }

}
