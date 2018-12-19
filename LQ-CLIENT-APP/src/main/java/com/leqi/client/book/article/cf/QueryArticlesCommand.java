/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.cf;

import com.leqi.client.book.article.uf.ArticleModel;
import com.leqienglish.client.fw.cf.PageCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Content;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 根据catalogId查询文章列表
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryArticlesCommand")
public class QueryArticlesCommand extends PageCommand {

    @Resource(name = "ArticleModel")
    private ArticleModel articleModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = this.getPageMultiValueMap();
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
