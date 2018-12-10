package com.leqi.client.book.sentence.tool.cf;

import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.sentence.Sentence;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuleqi on 2018/12/5.
 */
@Lazy
@Component("SentenceToolCommand")

public class SentenceToolCommand extends Command{
    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        LQExceptionUtil.required(param.get(DATA)!=null  , "没有文本");
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        String data = (String) param.get(DATA);

        String[] dataArr = data.split("\\n");
        List<String> listValues = toList(dataArr);
        if (listValues.size()%2 !=0){
            throw new Exception(listValues.toString());
        }

        ShortWord shortWord = findShortWord(listValues);
        List<Sentence> sentences = this.createSentence(listValues);
        this.addRelation(shortWord,sentences);
    }

    private List<Sentence> createSentence(List<String> datas) throws Exception {
        List<Sentence> sentences = new ArrayList<>();
        for(int i = 2 ; i < datas.size() ; i = i+2){
            String en = datas.get(i);
            String ch = datas.get(i+1);
            Sentence sentence = new Sentence();
            sentence.setEnglish(removeNumber(en.trim()));
            sentence.setChinese(ch.trim());

            sentence =  this.restClient.post("sentence/create", sentence, null, Sentence.class);


            sentences.add(sentence);

        }

        return  sentences;
    }

    private String removeNumber(String str){
        int index = str.indexOf(".");
        if(index <0){
            return str;
        }
        if(index<3){
            str = str.substring(index+1).trim();
        }
        return str;
    }

    private void addRelation(ShortWord shortWord , List<Sentence> sentences){
        for(Sentence sentence : sentences){
            ShortWordAndSentence shortWordAndSentence = new ShortWordAndSentence();
            shortWordAndSentence.setSentenceId(sentence.getId());
            shortWordAndSentence.setShortWordId(shortWord.getId());
            try {
                this.restClient.post("/shortWordAndSentence/create", shortWordAndSentence, null, ShortWordAndSentence.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private ShortWord findShortWord(List<String> datas) throws Exception {
        String eng = datas.get(0);
        String info = datas.get(1);

        MultiValueMap<String, String> textMap = new LinkedMultiValueMap();
        textMap.add("word", eng.trim());


        ShortWord[]   shortWords = this.restClient.get("/shortWord/findByWord", textMap,  ShortWord[].class);

        if(shortWords == null ||shortWords.length ==0){
            throw new Exception("没找到短语");
        }

        if(shortWords.length >1){
            throw new Exception("找到多个短语");
        }



        return shortWords[0];
    }

    private List<String> toList(String[] strArr){
        List<String> list = new ArrayList<>();
        for(String item : strArr){
            if(item == null || item.isEmpty()){
                continue;
            }

            list.add(item);
        }
        return  list;
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation("完成");
    }
}
