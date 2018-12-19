/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.word.cf;

import com.leqi.client.book.segment.word.uf.SegmentWordsModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
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
@Component("DeleteSegmentWrodsCommand")
public class DeleteSegmentWrodsCommand extends Command {

    @Resource(name = "SegmentWordsModel")
    private SegmentWordsModel segmentWordsModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        WordAndSegment was = (WordAndSegment) param.get(DATA);
        parameter.add("segmentId", was.getSegmentId());
        parameter.add("wordId", was.getWordId());
        parameter.add("index", was.getScentenceIndex() + "");

        this.restClient.delete("english/wordandsegment/deleteByWordIdAndSegemntIdAndIndex", null, parameter, String.class);
        this.putParameters(DATA, "ok");
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        String result = (String) this.getParameters(DATA);
        if (result != null) {
            AlertUtil.showInformation("删除成功");
            segmentWordsModel.getWordAndSegments().remove((WordAndSegment) param.get(DATA));
        }

    }

}
