/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.cf;

import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.fw.cf.QueryCommand;
import com.leqienglish.client.util.alert.AlertUtil;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("DeleteSegmentCommand")
public class DeleteSegmentCommand extends QueryCommand {

    @Resource(name = "SegmentModel")
    private SegmentModel segmentModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("id", (String) param.get(ID));

        Segment segment = this.restClient.get("/segment/delete", parameter, Segment.class);
        this.putParameters(SUCCESS, segment);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        if (this.getParameters(SUCCESS) != null) {
            AlertUtil.showInformation("删除成功");
            Object o = this.segmentModel.getArticle();

            segmentModel.setArticle(null);
            segmentModel.setArticle((Content) o);
        }else{
             AlertUtil.showInformation("删除失败");
        }

    }

}
