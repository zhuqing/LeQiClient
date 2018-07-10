/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.cf;

import com.leqi.client.book.article.uf.ArticleModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
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
@Component("DeleteCatalogCommand")
public class DeleteCatalogCommand extends Command {

    @Resource(name = "BookModel")
    private BookModel bookModel;

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        Catalog catalog = (Catalog) param.get(DATA);
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("id", catalog.getId());
        String id= this.restClient.delete("english/catalog/delete", null, parameter, String.class);
        
        this.putParameters(DATA, id);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        String id = (String) this.getParameters(DATA);
        if(id!=null){
            AlertUtil.showInformation(AlertUtil.DELETE_SUCCESS);
            bookModel.getBooks().remove(param.get(DATA));
        }
    }

}
