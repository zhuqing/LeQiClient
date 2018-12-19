/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.cf;

import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.PageCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("QueryCatalogCommand")
public class QueryCatalogCommand extends PageCommand {

    @Resource(name = "BookModel")
    private BookModel bookModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        MultiValueMap<String, String> parameter = this.getPageMultiValueMap();
        parameter.add("type", (Integer) param.get("type") + "");

        if (param.get("parentId") != null) {
            parameter.add("parentId", (String) param.get("parentId"));
        }
        Catalog[] catalogs = this.restClient.get("/english/catalog/getCatalogByType",parameter, Catalog[].class);
        this.putParameters("datas", catalogs);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Catalog[] catalogs = (Catalog[]) this.getParameters("datas");

        Integer type = (Integer) param.get("type");
        switch (type) {
            case Consistent.BOOK_TYPE:
                bookModel.setBooks(Arrays.asList(catalogs));
                break;
        }

    }

}
