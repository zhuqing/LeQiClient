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
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Content;

/**
 * 根据catalogId查询文章列表
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryArticlesCommand")
public class QueryArticlesCommand extends QueryCommand {

    @Resource(name = "ArticleModel")
    private ArticleModel articleModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("page", this.getPageNum() + "");
        parameter.add("pageSize", this.getPageSize() + "");

        if (param.get(ID) != null) {
            parameter.add("catalogId", (String) param.get(ID));
        }
        Content[] contents = this.restClient.get("/english/content/findCotentByCatalogId", parameter, Content[].class);
        this.putParameters("datas", contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Content[] contents = (Content[]) this.getParameters("datas");
        if (contents == null || contents.length == 0) {
            articleModel.getArticles().clear();
            return;
        }
        articleModel.getArticles().setAll(contents);

    }

}
