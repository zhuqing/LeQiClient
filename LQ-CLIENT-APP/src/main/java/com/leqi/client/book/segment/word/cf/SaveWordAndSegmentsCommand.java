/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.word.cf;

import com.google.common.base.Objects;
import com.leqi.client.book.segment.word.uf.SegmentWordsModel;
import com.leqi.client.book.word.cf.SaveWordsCommand;
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

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveWordAndSegmentsCommand")
public class SaveWordAndSegmentsCommand extends SaveWordsCommand {

    @Resource(name = "SegmentWordsModel")
    private SegmentWordsModel segmentWordsModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        List<Word> words = (List<Word>) param.get(DATA);

        Content article = segmentWordsModel.getArticle();
        Segment segment = segmentWordsModel.getSegment();
        AudioPlayPoint audioPlayPoint = segmentWordsModel.getAudioPlayPoint();
        List<String> wordIds = this.saveWord(words);

        List<WordAndSegment> savedWses = new ArrayList<>();

        for (int i = 0; i < wordIds.size(); i++) {
            WordAndSegment ws = new WordAndSegment();
            ws.setAudioPath(article.getAudioPath());
            ws.setEndTime(audioPlayPoint.getEndTime());
            ws.setContentId(article.getId());
            ws.setContentTitle(article.getTitle());
            ws.setStartTime(audioPlayPoint.getStartTime());
            ws.setScentenceIndex(audioPlayPoint.getIndex());
            ws.setScentence(audioPlayPoint.getEnText()+Consistent.SLIP_EN_AND_CH+audioPlayPoint.getChText());
            ws.setWord(words.get(i).getWord());
            ws.setWordId(wordIds.get(i));
            ws.setSegmentId(segment.getId());

          
            if (hasSave(ws)) {
                continue;
            }
            WordAndSegment savedWs = this.restClient.post("english/wordandsegment/create", ws, null, WordAndSegment.class);
            savedWses.add(savedWs);
        }
        this.putParameters(DATA, savedWses);
    }
    
    private boolean hasSave( WordAndSegment ws){
       return this.segmentWordsModel.getWordAndSegments().stream().filter((w)->Objects.equal(w.getWordId(), ws.getWordId())&&Objects.equal(w.getSegmentId(), ws.getSegmentId())&&Objects.equal(w.getScentenceIndex(), ws.getScentenceIndex()))
                .count() != 0;
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        List<WordAndSegment> savedWses = (List<WordAndSegment>) this.getParameters(DATA);
        segmentWordsModel.getWordAndSegments().addAll(savedWses);
    }

}
