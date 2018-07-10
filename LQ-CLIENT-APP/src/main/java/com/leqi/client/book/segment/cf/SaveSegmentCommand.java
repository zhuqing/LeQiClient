/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.cf;

import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
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
@Component("SaveSegmentCommand")
public class SaveSegmentCommand extends Command {

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        Segment content = (Segment) param.get(DATA);

        LQExceptionUtil.required(content != null, "content不能为null");
        // LQExceptionUtil.required(content.getId() == null, "content已经保存过了");

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        Segment content = (Segment) param.get(DATA);
        if (content.getId() == null) {
            content = this.restClient.post("/segment/create", content, null, Segment.class);
            this.putParameters(MESSAGE, AlertUtil.SAVE_SUCCESS);
        } else {
            content = this.restClient.put("/segment/update", content, null, Segment.class);
            this.putParameters(MESSAGE, AlertUtil.UPDATE_SUCCESS);
        }

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation((String) this.getParameters(MESSAGE));
    }

}
