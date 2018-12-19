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
@Component("DeleteSegmentAndShortWordCommand")
public class DeleteSegmentAndShortWordCommand extends Command {

    @Resource(name = "SegmentAndShortWordsModel")
    private SegmentAndShortWordsModel segmentAndShortWordsModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        LQExceptionUtil.required(param.get(DATA)!=null, "content不能为null");
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        SegmentAndShortWord segmentAndShortWord = (SegmentAndShortWord) param.get(DATA);
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("id", segmentAndShortWord.getId());


        this.restClient.delete("/segmentAndShortWord/delete", null  ,parameter, String.class);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation(AlertUtil.DELETE_SUCCESS);

        SegmentAndShortWord segmentAndShortWords = (SegmentAndShortWord) param.get(DATA);

        segmentAndShortWordsModel.getSegmentAndShortWords().remove(segmentAndShortWords);

    }

}
