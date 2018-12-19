/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.shortword.cf;

import com.leqi.client.book.segment.shortword.uf.SegmentAndShortWordsModel;
import com.leqienglish.client.fw.cf.PageCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.shortword.SegmentAndShortWord;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QuerySegmentAndShortWordCommand")
public class QuerySegmentAndShortWordCommand extends PageCommand {

    @Resource(name = "SegmentAndShortWordsModel")
    private SegmentAndShortWordsModel segmentAndShortWordsModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("segmentId", (String) param.get(DATA));

        SegmentAndShortWord[] contents = this.restClient.get("/segmentAndShortWord/findBySegmentId", parameter, SegmentAndShortWord[].class);
        this.putParameters(DATA, contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        SegmentAndShortWord[] segmentAndShortWords = (SegmentAndShortWord[]) this.getParameters(DATA);

        segmentAndShortWordsModel.getSegmentAndShortWords().setAll(segmentAndShortWords);

    }

}
