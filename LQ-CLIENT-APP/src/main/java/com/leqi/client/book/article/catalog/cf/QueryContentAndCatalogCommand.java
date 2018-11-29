/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.catalog.cf;

import com.leqi.client.book.article.catalog.uf.CatalogAndArticleModel;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqienglish.client.fw.cf.QueryCommand;
import com.leqienglish.util.text.TextUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.content.ContentAndCatalog;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 根据catalogId查询文章列表
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryContentAndCatalogCommand")
public class QueryContentAndCatalogCommand extends QueryCommand {

    @Resource(name = "CatalogAndArticleModel")
    private CatalogAndArticleModel catalogAndArticleModel;

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

        ContentAndCatalog[] contentAndCatalogs = this.restClient.get("/contentAndCatalog/findByContentId", parameter, ContentAndCatalog[].class);
        Catalog[] catalogs = this.restClient.get("/english/catalog/findByContentId", parameter, Catalog[].class);

        for(ContentAndCatalog contentAndCatalog : contentAndCatalogs){
            for(Catalog catalog : catalogs){
                if(Objects.equals(catalog.getId(),contentAndCatalog.getCatalogId())){
                    contentAndCatalog.setCatalogName(catalog.getTitle());
                    break;
                }
            }
        }


        this.putParameters(DATA, contentAndCatalogs);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        ContentAndCatalog[] contents = (ContentAndCatalog[]) this.getParameters(DATA);
        if (contents == null || contents.length == 0) {
            catalogAndArticleModel.getContentAndCatalogs().clear();
            return;
        }
       catalogAndArticleModel.getContentAndCatalogs().setAll(contents);

    }

}
