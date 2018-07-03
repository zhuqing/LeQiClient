/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.control.navigation;

import com.leqienglish.client.control.button.LQButton;
import com.leqienglish.client.util.sourceitem.SourceItem;
import com.leqienglish.client.util.thread.DelayRunner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author zhuleqi
 */
public class LQBreadCrumbBar<T extends SourceItem> extends HBox{
    
    private final List<LQButton> buttons = new ArrayList<>();
    
    private final Map<SourceItem , Button> buttonsMap  = new HashMap<>();
    
    private ObjectProperty<SourceItem> selected;
    
    private final ChangeListener<SourceItem> selectedItemChangeListener = new ChangeListener<SourceItem>(){
        @Override
        public void changed(ObservableValue<? extends SourceItem> observable, SourceItem oldValue, SourceItem newValue) {
            Button selectButton = buttonsMap.get(newValue);
            
            if(selectButton == null){
                return;
            }
            int index = buttons.indexOf(selectButton);
            if(index <0){
                return;
            }
            
            List<LQButton> removedButtons = buttons.subList(index+1, buttons.size());
            
            buttons.removeAll(removedButtons);
           

            
        }
    };
    
    public LQBreadCrumbBar(){
       
    }
    
    
    
    public void add(SourceItem item){
       LQButton lqButton = new LQButton(item.getDisplay()+">>");
       lqButton.setUserData(item);
       buttons.add(lqButton);
       buttonsMap.put(item, lqButton);
       this.setSelected(item);
       this.getChildren().add(lqButton);
       
       lqButton.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event) {
               Button button = (Button) event.getSource();
               
               SourceItem clickItem = (SourceItem) button.getUserData();
               setSelected(clickItem);
           }
       });
    }
    
    public void clear(){
        buttons.clear();
        this.getChildren().clear();
    }

    /**
     * @return the selected
     */
    public SourceItem getSelected() {
        return selectedProperty().getValue();
    }

      /**
     * @return the selected
     */
    public ObjectProperty<SourceItem> selectedProperty() {
        if(selected == null){
            selected = new SimpleObjectProperty<SourceItem>();
            selected.addListener(selectedItemChangeListener);
        }
        return selected;
    }
    /**
     * @param selected the selected to set
     */
    public void setSelected(SourceItem selected) {
        this.selectedProperty().setValue(selected); 
    }
}
