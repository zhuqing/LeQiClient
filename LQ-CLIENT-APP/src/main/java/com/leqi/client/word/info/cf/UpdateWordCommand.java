/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.info.cf;

import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import java.util.Map;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.word.Word;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("UpdateWordCommand")
public class UpdateWordCommand extends Command {



    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
      
        this.restClient.put("english/word/update", param.get(DATA),null, Word.class);

      

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation("更新成功！！");
       
    }

}
