/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.shortword.cf;

import com.leqi.client.book.segment.shortword.uf.SegmentAndShortWordsModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.shortword.SegmentAndShortWord;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveSegmentAndShortWordCommand")
public class SaveSegmentAndShortWordCommand extends Command {

    @Resource(name = "SegmentAndShortWordsModel")
    private SegmentAndShortWordsModel segmentAndShortWordsModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        LQExceptionUtil.required(param.get(DATA)!=null, "content不能为null");
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        SegmentAndShortWord segmentAndShortWord = (SegmentAndShortWord) param.get(DATA);


        SegmentAndShortWord contents = this.restClient.post("/segmentAndShortWord/create", segmentAndShortWord,null, SegmentAndShortWord.class);
        this.putParameters(DATA, contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation(AlertUtil.SAVE_SUCCESS);

        SegmentAndShortWord segmentAndShortWords = (SegmentAndShortWord) this.getParameters(DATA);

        segmentAndShortWordsModel.getSegmentAndShortWords().add(segmentAndShortWords);

    }

}
