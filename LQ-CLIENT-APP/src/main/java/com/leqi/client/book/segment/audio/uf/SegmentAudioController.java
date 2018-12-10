/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.book.segment.audio.uf;

import com.leqi.client.book.segment.uf.*;
import com.leqi.client.book.content.uf.ContentModel;
import com.leqi.client.book.segment.cf.DeleteSegmentCommand;
import com.leqi.client.book.segment.cf.UpdateContentStatusCommand;
import com.leqi.client.book.segment.cf.QuerySegmentsCommand;
import com.leqi.client.book.segment.cf.UpdateSegmentStatusCommand;
import com.leqi.client.book.segment.check.uf.SegmentCheckModel;
import com.leqi.client.book.segment.cf.SaveSegmentCommand;
import com.leqi.client.book.segment.info.uf.SegmentInfoModel;
import com.leqi.client.book.uf.BookModel;
import com.leqienglish.client.comman.cf.DownLoadFileCommand;

import com.leqienglish.client.control.button.LQButton;
import com.leqienglish.client.control.form.LQFormView;
import com.leqienglish.client.control.timestemp.TimeStemp;
import com.leqienglish.client.control.view.table.LQTableView;
import com.leqienglish.client.control.view.table.row.LQTableRow;
import com.leqienglish.client.fw.cf.Command;
import static com.leqienglish.client.fw.cf.Command.ID;

import com.leqienglish.client.fw.sf.RestClient;
import com.leqienglish.client.fw.sf.Service;
import com.leqienglish.client.fw.uf.FXMLController;
import com.leqienglish.client.util.alert.AlertUtil;
import com.leqienglish.client.util.sourceitem.SourceItemUtil;
import com.leqienglish.util.file.FileUtil;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import xyz.tobebetter.entity.Consistent;
import xyz.tobebetter.entity.english.Content;
import xyz.tobebetter.entity.english.Segment;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("SegmentAudioController")
public class SegmentAudioController extends FXMLController<SegmentAudioModel> {


    @FXML
    private CheckBox isSupportChinease;
    
 
    @FXML
    private TimeStemp timeStemp;

    @Resource(name = "SaveSegmentCommand")
    private SaveSegmentCommand saveSegmentCommand;

    @Resource(name = "DeleteSegmentCommand")
    private DeleteSegmentCommand deleteSegmentCommand;

    @Resource(name = "BookRootModel")
    private ContentModel contentModel;

    @Resource(name = "SegmentCheckModel")
    private SegmentCheckModel segmentCheckModel;
    @Resource(name = "RestClient")
    private RestClient restClient;


    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      

        JavaFxObservable.nullableValuesOf(this.getModel().segmentProperty())
                .subscribe(op -> segmentChange(op.orElse(null)));
        segmentChange(getModel().getSegment());
        
        isSupportChinease.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                timeStemp.setSuportChineaseChinease(t1);
            }
        });

     

    }

    private void segmentChange(Segment segment) {
        try {
            Content article = this.getModel().getArticle();
            String filePath = getFilePath(segment,article);
            
            File file = new File(filePath);
            timeStemp.setAudioPath(file.toURI().toString());
            timeStemp.setSourceText(segment.getContent());
           
        } catch (Exception ex) {
            Logger.getLogger(SegmentAudioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getFilePath(Segment segment,Content content) throws Exception{
          String filePath = null;
             if(segment.getAudioPath() != null){
                 filePath = FileUtil.getInstence().toLocalFilePath(segment.getAudioPath());
             }
             
             if(filePath == null){
  
                 filePath = FileUtil.getInstence().toLocalFilePath(content.getAudioPath());
             }

           File file =   new File(filePath);
             if(!file.exists()){
                this.restClient.downLoad("file/download?path="+segment.getAudioPath(), filePath, new Consumer<Double>() {
                    @Override
                    public void accept(Double aDouble) {

                    }
                });
             }
             return filePath;
    }

   

    /**
     * 发布按钮点击事件
     *
     * @param event
     */
    @FXML
    public void save(ActionEvent event) {
       this.getModel().getSegment().setContent(this.timeStemp.getTargetText());
       
        Map<String, Object> map = new HashMap<>();
        map.put(Command.DATA, this.getModel().getSegment());
        timeStemp.setPlaying(Boolean.FALSE);
        
        this.saveSegmentCommand.doCommand(map);
       
    }

   
    

    @FXML
    public void refresh(ActionEvent event) {

    }

  

    @FXML
    public void check(ActionEvent event) {
        Button button = (Button) event.getSource();
        Segment segment = (Segment) button.getUserData();
        segmentCheckModel.setSegment(segment);
        this.segmentCheckModel.setArticle(this.getModel().getArticle());
        contentModel.setAddBreadCrumb(SourceItemUtil.create(segment.getTitle(), "SegmentCheckModel"));
    }

   


    
}
