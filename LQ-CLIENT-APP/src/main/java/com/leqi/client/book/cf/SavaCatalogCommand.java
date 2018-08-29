/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.cf;

import com.google.common.base.Objects;
import com.leqi.client.book.info.uf.BookInfoModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.fw.cf.Command;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.util.exception.LQExceptionUtil;
import com.leqienglish.util.file.FileUtil;
import java.io.File;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SavaCatalogCommand")
public class SavaCatalogCommand extends Command {

    @Resource(name = "BookModel")
    private BookModel bookModel;

    @Override
    protected void run(Map<String, Object> param) throws Exception {
        Catalog catalog = (Catalog) param.get(DATA);
        if (this.getParameters("value") != null) {
            String path = this.restClient.upload("file/uploadImage", (MultiValueMap<String, Object>) this.getParameters("value"), null, String.class);
            catalog.setImagePath(path);
        }

        if (catalog.getId() == null) {
            catalog = this.restClient.post("/english/catalog/create", catalog, null, Catalog.class);
            this.putParameters(MESSAGE, AlertUtil.SAVE_SUCCESS);
        } else {
            catalog = this.restClient.put("/english/catalog/update", catalog, null, Catalog.class);
            this.putParameters(MESSAGE, AlertUtil.UPDATE_SUCCESS);
        }

        this.putParameters(DATA, catalog);

    }

    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        AlertUtil.showInformation((String) this.getParameters(MESSAGE));
    }

    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        Catalog catalog = (Catalog) param.get(DATA);

        LQExceptionUtil.required(catalog != null, "catalog不能为null");
        if (FileUtil.getInstence().fileExit(catalog.getImagePath())) {
            MultiValueMap<String, Object> value = new LinkedMultiValueMap();

            value.add("file", new FileSystemResource(new File(catalog.getImagePath())));
            //catalog.setImagePath(null);
            this.putParameters("value", value);
        }

    }

}
