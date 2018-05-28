/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.info.cf;

import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.fw.cf.QueryCommand;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Catalog;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryCatalogCommand")
public class QueryCatalogCommand extends QueryCommand {

    @Resource(name = "BookModel")
    private BookModel bookModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

        parameter.add("page", this.getPageNum() + "");
        parameter.add("pageSize", this.getPageSize() + "");

        Catalog catalog = new Catalog();
        catalog.setType((Integer) param.get("type"));
        if (param.get("parentId") != null) {
            catalog.setParentId((String) param.get("parentId"));
        }
        Catalog[] catalogs = this.restClient.get("/english/catalog/getCatalogByType", catalog, parameter, Catalog[].class);
        this.putParameters("datas", catalogs);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Catalog[] catalogs = (Catalog[]) this.getParameters("datas");

        Integer type = (Integer) param.get("type");
        switch (type) {
            case Catalog.BOOK_TYPE:
                bookModel.setBooks(Arrays.asList(catalogs));
                break;
            case Catalog.CHAPTER_TYPE:
                bookModel.setArticles(Arrays.asList(catalogs));
                break;
        }

    }

}
