/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.cf;

import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.fw.cf.PageCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Segment;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QuerySegmentsCommand")
public class QuerySegmentsCommand extends PageCommand {

    @Resource(name = "SegmentModel")
    private SegmentModel segmentModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("contentId", (String) param.get("contentId"));

        Segment[] contents = this.restClient.get("/segment/findByContentId", parameter, Segment[].class);
        this.putParameters("datas", contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Segment[] catalogs = (Segment[]) this.getParameters("datas");

        segmentModel.getSegments().setAll(catalogs);

    }

}
