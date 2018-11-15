/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.word.shortword.sentence.cf;

import com.leqi.client.word.shortword.sentence.uf.ShortWordAndSentenceModel;
import com.leqi.client.word.shortword.uf.ShortWordModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.shortword.ShortWord;
import xyz.tobebetter.entity.english.shortword.ShortWordAndSentenceVO;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("DeleteShortWordAndSentenceCommand")
public class DeleteShortWordAndSentenceCommand extends Command {

    @Resource(name = "ShortWordAndSentenceModel")
    private ShortWordAndSentenceModel shortWordAndSentenceModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        ShortWordAndSentenceVO content = ( ShortWordAndSentenceVO) param.get(DATA);

        LQExceptionUtil.required(content != null, "ShortWord不能为null");

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        ShortWordAndSentenceVO content = ( ShortWordAndSentenceVO) param.get(DATA);

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("id", content.getId());

        this.restClient.delete("/shortWordAndSentence/delete", null, parameter, String.class);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {

        ShortWordAndSentenceVO content = ( ShortWordAndSentenceVO)param.get(DATA);
        AlertUtil.showInformation(AlertUtil.DELETE_SUCCESS);

        this.shortWordAndSentenceModel.getShortWordAndSentenceVOS().remove(content);

    }

}
