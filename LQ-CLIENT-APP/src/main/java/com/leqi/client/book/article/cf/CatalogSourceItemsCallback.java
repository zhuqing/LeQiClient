/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.article.cf;

import com.leqienglish.client.fw.sf.RestClient;
import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.util.Callback;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Catalog;

/**
 *
 * @author zhuleqi
 */
@Lazy
@Component("CatalogSourceItemsCallback")
public class CatalogSourceItemsCallback implements Callback<Object, List<SourceItem>> {

    @Resource(name = "RestClient")
    protected RestClient restClient;

    private static List<SourceItem> sourceItems;

    @Override
    public List<SourceItem> call(Object p) {
        if (sourceItems != null) {
            return sourceItems;
        }

        synchronized (CatalogSourceItemsCallback.class) {
            if (sourceItems != null) {
                return sourceItems;
            }
            sourceItems = createSourceItems();
            return sourceItems;
        }
    }

    private List<SourceItem> createSourceItems() {
        try {
            MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();

            parameter.add("type", Consistent.CATALOG_TYPE + "");
            Catalog[] catalogs = this.restClient.get("/english/catalog/getAllLunchedCatalogsByType", parameter, Catalog[].class);
            if (catalogs == null || catalogs.length == 0) {
                return Collections.EMPTY_LIST;
            }

            List<SourceItem> sourceItems = new ArrayList<>();
            for (Catalog catalog : catalogs) {
                SourceItem sourceItem = new SourceItem();
                sourceItem.setDisplay(catalog.getTitle());
                sourceItem.setValue(catalog.getId());
                sourceItems.add(sourceItem);
            }

            return sourceItems;

        } catch (Exception ex) {
            Logger.getLogger(CatalogSourceItemsCallback.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Collections.EMPTY_LIST;
    }

}
