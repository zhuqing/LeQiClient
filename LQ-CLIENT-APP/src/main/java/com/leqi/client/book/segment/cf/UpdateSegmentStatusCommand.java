/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.cf;

import com.google.common.base.Objects;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqi.client.common.cf.updatestatus.UpdateStatusByIdCommand;
import com.leqienglish.client.fw.cf.Command;
import static com.leqienglish.client.fw.cf.Command.DATA;
import static com.leqienglish.client.fw.cf.Command.ID;
import com.leqienglish.client.util.alert.AlertUtil;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Message;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("UpdateSegmentStatusCommand")
public class UpdateSegmentStatusCommand extends UpdateStatusByIdCommand {

    @Resource(name = "SegmentModel")
    private SegmentModel segmentModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        String id = (String) param.get(ID);
        int status = (int) param.get(DATA);

        this.updateStatus(id, status + "");
        Segment book = segmentModel.getSegments().stream().filter(c -> Objects.equal(c.getId(), id)).findFirst().orElse(null);
        book.setStatus(status);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Segment[] cs = segmentModel.getSegments().toArray(new Segment[0]);
        segmentModel.getSegments().clear();
        this.segmentModel.getSegments().setAll(cs);
    }

    @Override
    protected String getUpdatePath() {
        return "/segment/updateStatusById";
    }

}
