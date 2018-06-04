/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.info.cf;

import com.google.common.base.Objects;
import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import java.io.File;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Catalog;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SavaCatalogCommand")
public class SavaCatalogCommand extends Command {

    /**
     *
     */
    @Resource(name = "BookInfoModel")
    private BookInfoModel bookInfoModel;

    @Resource(name = "BookModel")
    private BookModel bookModel;

    @Override
    protected void run(Map<String, Object> param) throws Exception {

        String path = this.restClient.upload("/english/catalog/upload", (MultiValueMap<String, Object>) this.getParameters("value"), null, String.class);
        Catalog catalog = bookInfoModel.getCatalog();
        catalog.setImagePath(path);
        Catalog newCatalog = this.restClient.post("/english/catalog/create", catalog, null, Catalog.class);
        this.putParameters("data", newCatalog);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        if (this.getParameters("data") == null) {
            AlertUtil.showError("保存失败！");
        } else {
            Catalog newCatalog = (Catalog) this.getParameters("data");
            if (Objects.equal(newCatalog.getType(), Catalog.BOOK_TYPE)) {
                this.bookModel.getBooks().add(newCatalog);
            }
//            else if (Objects.equal(newCatalog.getType(), Catalog.CHAPTER_TYPE)) {
//                this.bookModel.getArticles().add(newCatalog);
//            }
        }
    }

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        Catalog catalog = bookInfoModel.getCatalog();

        if (catalog == null) {
            throw new Exception("catalog不能为null");
        }
        MultiValueMap<String, Object> value = new LinkedMultiValueMap();
        value.add("catalog", "catalog");
        value.add("file", new FileSystemResource(new File(catalog.getImagePath())));
        //catalog.setImagePath(null);
        this.putParameters("value", value);
    }

}
