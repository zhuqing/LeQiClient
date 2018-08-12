/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.catalog.cf;

import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.catalog.uf.CatalogModel;
import com.leqienglish.client.fw.cf.QueryCommand;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.Content;

/**
 * 根据catalogId查询文章列表
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveCatalogsCommand")
public class SaveCatalogsCommand extends QueryCommand {

    @Resource(name = "CatalogModel")
    private CatalogModel catalogModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {

        Catalog catalog = (Catalog) param.get(DATA);
        Catalog result = null;

        if (catalog.getId() != null) {
            result = this.restClient.put("/english/catalog/update", catalog, null, Catalog.class);
            this.putParameters(MODEL, "UPDATE");
        } else {
            result = this.restClient.post("/english/catalog/create", catalog, null, Catalog.class);
            this.putParameters(MODEL, "INSERT");
        }

        this.putParameters(DATA, result);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Catalog catalog = (Catalog) this.getParameters(DATA);
        if (catalog == null) {
            return;
        }

        if ("INSERT".equals(this.getParameters("MODEL"))) {
            this.catalogModel.getCatalogs().add(catalog);
        }

    }

}
