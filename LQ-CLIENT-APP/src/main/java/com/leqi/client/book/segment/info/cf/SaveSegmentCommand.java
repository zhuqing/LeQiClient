/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.info.cf;

import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.util.exception.LQExceptionUtil;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Segment;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveContentCommand")
public class SaveSegmentCommand extends Command {

    @Resource(name = "SegmentInfoModel")
    private SegmentInfoModel segmentInfoModel;

    @Resource(name = "SegmentModel")
    private SegmentModel segmentModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        Segment content = (Segment) param.get(DATA);

        LQExceptionUtil.required(content != null, "content不能为null");
        LQExceptionUtil.required(content.getId() == null, "content已经保存过了");
        //LQExceptionUtil.required(content.getAudioPath() != null, "没有音频文件");

       
    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        Segment content = segmentInfoModel.getSegment();
        content = this.restClient.post("/segment/create", content, null, Segment.class);
        this.putParameters(DATA, content);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Segment segment = (Segment) this.getParameters(DATA);
        segmentInfoModel.setSegment(segment);
        segmentModel.getSegments().add(segment);
    }

}
