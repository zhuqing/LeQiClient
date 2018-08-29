/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.version.cf;

import com.leqienglish.client.util.sourceitem.SourceItem;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Callback;
import xyz.tobebetter.entity.Consistent;

/**
 *
 * @author zhuleqi
 */
public class VersionDataSourceCallback implements Callback<Object,List<SourceItem>>{

    @Override
    public List<SourceItem> call(Object p) {
        List<SourceItem> sourceItems = new ArrayList<>();
        
        SourceItem sourceItem = new SourceItem();
        sourceItem.setValue(Consistent.ANDROID);
        sourceItem.setDisplay("Android");
        sourceItems.add(sourceItem);
        
        sourceItem = new SourceItem();
        sourceItem.setDisplay("IOS");
        sourceItem.setValue(Consistent.IOS);
        sourceItems.add(sourceItem);
        
        return sourceItems;
    }
    
}
