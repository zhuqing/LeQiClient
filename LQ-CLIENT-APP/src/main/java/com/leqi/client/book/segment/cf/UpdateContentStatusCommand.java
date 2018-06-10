/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.cf;

import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import java.util.Map;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Message;
import xyz.tobebetter.entity.english.Content;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("UpdateContentStatusCommand")
public class UpdateContentStatusCommand extends Command {

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("id", param.get(ID) + "");
        parameter.add("status", (String) param.get(DATA));

        Message message = this.restClient.put("/english/content/updateStatus", null, parameter, Message.class);

        if (message.getStatus() == Message.ERROR) {
            throw new Exception("更新失败，" + message.getMessage());
        }
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation("更新成功");

        SegmentModel segmentModel = (SegmentModel) param.get(Command.MODEL);
        Content article = segmentModel.getArticle();
        segmentModel.setArticle(null);
        segmentModel.setArticle(article);
    }

}
