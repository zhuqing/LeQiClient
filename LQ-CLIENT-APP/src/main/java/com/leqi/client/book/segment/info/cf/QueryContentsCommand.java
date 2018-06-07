/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.info.cf;

import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.fw.cf.QueryCommand;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryContentsCommand")
public class QueryContentsCommand extends QueryCommand {

    @Resource(name = "SegmentModel")
    private SegmentModel segmentModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("page", this.getPageNum() + "");
        parameter.add("pageSize", this.getPageSize() + "");

        if (param.get("parentId") != null) {
            parameter.add("parentId", (String) param.get("parentId"));
        }
        Content[] contents = this.restClient.get("/english/content/findContentByParentId", parameter, Content[].class);
        this.putParameters("datas", contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Content[] catalogs = (Content[]) this.getParameters("datas");

        segmentModel.getContents().setAll(catalogs);

    }

}
