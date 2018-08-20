/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.catalog.cf;

import com.leqi.client.catalog.uf.CatalogModel;
import static com.leqienglish.client.fw.cf.Command.DATA;
import com.leqienglish.client.fw.cf.QueryCommand;
import com.leqienglish.client.util.alert.AlertUtil;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.tobebetter.entity.english.Catalog;
import xyz.tobebetter.entity.word.Word;

/**
 * 根据catalogId查询文章列表
 *
 * @author zhuqing
 */
@Lazy
@Component("DeleteCatalogsCommand")
public class DeleteCatalogsCommand extends QueryCommand {
    
    @Resource(name = "CatalogModel")
    private CatalogModel catalogModel;
    
    @Override
    protected void getAppData(Map<String, Object> param) throws Exception {
        
    }
    
    @Override
    protected void run(Map<String, Object> param) throws Exception {
        
        Catalog catalog = (Catalog) param.get(DATA);
        
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        
        parameter.add("id", catalog.getId());
        
        String c = this.restClient.delete("/english/catalog/delete", catalog, parameter, String.class);
        if (c == null) {
            return;
        }
        
        this.putParameters(DATA, catalog);
    }
    
    @Override
    protected void doView(Map<String, Object> param) throws Exception {
        Catalog catalog = (Catalog) this.getParameters(DATA);
        if (catalog == null) {
            AlertUtil.showError("删除失败");
            return;
        }
        this.catalogModel.getCatalogs().remove(catalog);
        
    }
    
}
