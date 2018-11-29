/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.catalog.cf;

import com.leqi.client.book.article.catalog.uf.CatalogAndArticleModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.english.content.ContentAndCatalog;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("DeleteCatalogAndContentCommand")
public class DeleteCatalogAndContentCommand extends Command {

    @Resource(name = "CatalogAndArticleModel")
    private CatalogAndArticleModel catalogAndArticleModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        ContentAndCatalog catalog = (ContentAndCatalog) param.get(DATA);
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("id", catalog.getId());
        String id= this.restClient.delete("contentAndCatalog/delete", null, parameter, String.class);
        
        this.putParameters(DATA, id);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        String id = (String) this.getParameters(DATA);
        if(id!=null){
            AlertUtil.showInformation(AlertUtil.DELETE_SUCCESS);
            catalogAndArticleModel.getContentAndCatalogs().remove(param.get(DATA));
        }
    }

}
