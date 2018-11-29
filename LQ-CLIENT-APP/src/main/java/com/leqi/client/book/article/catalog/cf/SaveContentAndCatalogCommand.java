/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.catalog.cf;


import com.leqi.client.book.article.catalog.uf.CatalogAndArticleModel;
import com.leqi.client.book.article.uf.ArticleModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.text.TextUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.content.ContentAndCatalog;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveContentAndCatalogCommand")
public class SaveContentAndCatalogCommand extends Command {

    @Resource(name = "CatalogAndArticleModel")
    private CatalogAndArticleModel catalogAndArticleModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
       

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        ContentAndCatalog ccs = (ContentAndCatalog) param.get(DATA);


      ContentAndCatalog newcc = this.restClient.post("/contentAndCatalog/create", ccs, null, ContentAndCatalog.class);


        newcc.setCatalogName(ccs.getCatalogName());

        this.putParameters(DATA, newcc);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        ContentAndCatalog ccs = (ContentAndCatalog) this.getParameters(DATA);
        catalogAndArticleModel.getContentAndCatalogs().add(ccs);
        AlertUtil.showInformation(AlertUtil.SAVE_SUCCESS);

    }

}
