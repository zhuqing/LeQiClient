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
@Component("QueryVersionsCommand")
public class QueryVersionsCommand extends QueryCommand {

    @Resource(name = "VersionModel")
    private VersionModel versionModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
       
       
        Version[] contents = this.restClient.get("/version/findAll", null, Version[].class);
        this.putParameters(DATA, contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Version[] contents = (Version[]) this.getParameters(DATA);
        if (contents == null || contents.length == 0) {
            versionModel.getVersionList().clear();
            return;
        }
        versionModel.getVersionList().setAll(contents);

    }

}
