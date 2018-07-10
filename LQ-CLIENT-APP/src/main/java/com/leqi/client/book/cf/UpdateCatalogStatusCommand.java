/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.cf;

import com.google.common.base.Objects;
import com.leqi.client.book.uf.BookModel;
import com.leqi.client.common.cf.updatestatus.UpdateStatusByIdCommand;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.english.Catalog;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("UpdateCatalogStatusCommand")
public class UpdateCatalogStatusCommand extends UpdateStatusByIdCommand {

    @Resource(name = "BookModel")
    private BookModel bookModel;

    @Override
    protected String getUpdatePath() {
        return "english/catalog/updateStatusById";
    }

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {

    }

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        String id = (String) param.get(ID);
        int status = (int) param.get(DATA);

        this.updateStatus(id, status+"");
        Catalog book = bookModel.getBooks().stream().filter(c->Objects.equal(c.getId(), id)).findFirst().orElse(null);
        book.setStatus(status);
        
    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
           Catalog[] cs = bookModel.getBooks().toArray(new Catalog[0]);
           bookModel.getBooks().clear();
           this.bookModel.getBooks().setAll(cs);
    }

}
