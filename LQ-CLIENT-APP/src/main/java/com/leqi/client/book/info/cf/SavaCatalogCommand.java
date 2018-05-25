/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.info.cf;

import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import java.io.File;
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

    @Override
    protected void run(Object... args) throws Exception {

        String path = this.restClient.upload("/english/catalog/upload", (MultiValueMap<String, Object>) this.getParameters("value"), null, String.class);
        Catalog catalog = bookInfoModel.getCatalog();
        catalog.setImagePath(path);
        Catalog newCatalog = this.restClient.post("/english/catalog/create", catalog, null, Catalog.class);
        this.putParameters("newCatalog", newCatalog);

    }

    @Override
    protected void doView() throws Exception {
        if (this.getParameters("newCatalog") == null) {
            AlertUtil.showError("保存失败！");
        }
    }

    @Override
    protected void getAppData() throws Exception {
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
