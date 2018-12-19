/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.word.cf;

import com.leqi.client.book.segment.word.uf.SegmentWordsModel;
import com.leqienglish.client.fw.cf.PageCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.segment.WordAndSegment;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QuerySegmentWrodsCommand")
public class QuerySegmentWrodsCommand extends PageCommand {

    @Resource(name = "SegmentWordsModel")
    private SegmentWordsModel segmentWordsModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("segmentId", (String) param.get(ID));

        WordAndSegment[] contents = this.restClient.get("english/wordandsegment/findBySegmentId", parameter, WordAndSegment[].class);
        this.putParameters("datas", contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        WordAndSegment[] wordAndSegments = (WordAndSegment[]) this.getParameters("datas");
        segmentWordsModel.getWordAndSegments().addAll(wordAndSegments);

    }

}
