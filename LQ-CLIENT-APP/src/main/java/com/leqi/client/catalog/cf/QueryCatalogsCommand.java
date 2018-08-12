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
@Component("QueryCatalogsCommand")
public class QueryCatalogsCommand extends QueryCommand {

    @Resource(name = "CatalogModel")
    private CatalogModel catalogModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("type", Consistent.CATALOG_TYPE+"");

        Catalog[] contents = this.restClient.get("/english/catalog/findAllCatalogByType", parameter, Catalog[].class);
        this.putParameters("datas", contents);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Catalog[] contents = (Catalog[]) this.getParameters("datas");
        if (contents == null || contents.length == 0) {
            catalogModel.getCatalogs().clear();
            return;
        }
        this.catalogModel.setCatalogs(Arrays.asList(contents));

    }

}
