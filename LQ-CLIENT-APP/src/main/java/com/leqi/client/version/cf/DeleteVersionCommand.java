/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.version.cf;

import com.leqi.client.book.article.cf.*;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqi.client.book.uf.BookModel;
import com.leqi.client.version.uf.VersionModel;
import com.leqienglish.client.fw.cf.QueryCommand;
import com.leqienglish.client.util.alert.AlertUtil;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.version.Version;

/**
 * 根据catalogId查询文章列表
 *
 * @author zhuqing
 */
@Lazy
@Component("DeleteVersionCommand")
public class DeleteVersionCommand extends QueryCommand {

    @Resource(name = "VersionModel")
    private VersionModel versionModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {

        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        Version content = (Version) param.get(DATA);

        parameter.add("id", content.getId());

        this.restClient.delete("/version/delete", null,parameter, String.class);
        this.putParameters(DATA, DATA);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Object data = this.getParameters(DATA);
        if (data != null) {
            AlertUtil.showInformation(AlertUtil.DELETE_SUCCESS);
            versionModel.getVersionList().remove(param.get(DATA));
            return;
        }

    }

}
