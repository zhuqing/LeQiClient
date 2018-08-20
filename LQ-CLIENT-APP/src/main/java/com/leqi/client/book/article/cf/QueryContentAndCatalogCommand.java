/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.cf;

import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.segment.uf.SegmentModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.QueryCommand;
import com.leqienglish.util.text.TextUtil;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.content.ContentAndCatalog;

/**
 * 根据catalogId查询文章列表
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryContentAndCatalogCommand")
public class QueryContentAndCatalogCommand extends QueryCommand {

    @Resource(name = "ArticleModel")
    private ArticleModel articleModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        Content content = (Content) param.get(DATA);

        if (content == null || TextUtil.isNullOrEmpty(content.getId())) {
            this.putParameters(DATA, null);
            return;
        }
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("contentId", content.getId());

        ContentAndCatalog[] contents = this.restClient.get("/contentAndCatalog/findByContentId", parameter, ContentAndCatalog[].class);
        this.putParameters(DATA, contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        ContentAndCatalog[] contents = (ContentAndCatalog[]) this.getParameters(DATA);
        if (contents == null || contents.length == 0) {
            articleModel.getContentAndCatalogs().clear();
            return;
        }
        articleModel.getContentAndCatalogs().setAll(contents);

    }

}
