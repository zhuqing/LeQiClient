/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.cf;


import com.leqi.client.book.article.uf.ArticleModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.text.TextUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.content.ContentAndCatalog;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SaveContentAndCatalogCommand")
public class SaveContentAndCatalogCommand extends Command {

    @Resource(name = "ArticleModel")
    private ArticleModel articleModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
       

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        List<ContentAndCatalog> ccs = (List<ContentAndCatalog>) param.get(DATA);

        for(int i = 0 ; i < ccs.size() ; i++){
            ContentAndCatalog cc = ccs.get(i);
            if(!TextUtil.isNullOrEmpty(cc.getId())){
                continue;
            }
            ContentAndCatalog newcc = this.restClient.post("/contentAndCatalog/create", cc, null, ContentAndCatalog.class);
            
            ccs.set(i, newcc);

        }
       

        this.putParameters(DATA, ccs);
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        List<ContentAndCatalog> ccs = (List<ContentAndCatalog>) this.getParameters(DATA);
        articleModel.getContentAndCatalogs().setAll(ccs);
        AlertUtil.showError(AlertUtil.SAVE_SUCCESS);

    }

}
