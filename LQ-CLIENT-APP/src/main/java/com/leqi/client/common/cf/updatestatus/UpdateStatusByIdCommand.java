/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.common.cf.updatestatus;

import com.leqi.client.book.segment.cf.*;
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
@Component("UpdateStatusByIdCommand")
public class UpdateStatusByIdCommand extends Command {

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("id", param.get(ID) + "");
        parameter.add("status", (String) param.get(DATA));
        
        
      

        this.restClient.put(param.get(PATH)+"", null, parameter, Message.class);
//
//        if (message.getStatus() == Message.ERROR) {
//            throw new Exception("更新失败，" + message.getMessage());
//        }
       
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        
    }

}
