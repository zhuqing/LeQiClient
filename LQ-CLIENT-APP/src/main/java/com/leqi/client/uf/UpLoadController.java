/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqi.client.uf;

import com.leqienglish.client.control.timestemp.TimeStemp;
import com.leqienglish.client.fw.root.uf.RootModel;
import com.leqienglish.client.fw.sf.UpLoadFileService;
import static com.leqienglish.client.fw.sf.UpLoadFileService.AUDIO_PATH;
import static com.leqienglish.client.fw.sf.UpLoadFileService.IMAGE_PATH;
import com.leqienglish.client.fw.uf.FXMLController;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javax.annotation.Resource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("UpLoadController")
public class UpLoadController extends FXMLController<UpLoadModel> {

    @FXML
    private Button findAudio;

    @FXML
    private Button upload;
    @FXML
    private TimeStemp timeStemp;

    @Resource(name = "UpLoadFileService")
    private UpLoadFileService upLoadFile;

    @Override
    public void refresh() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findAudio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("audio", "*.mp3"));
                File file = fileChooser.showOpenDialog(findAudio.getScene().getWindow());
                if (file == null) {
                    return;
                }

                timeStemp.setAudioPath(file.toURI().toString());
            }
        });

        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    upLoadFile.uploadFile("http://localhost:8080/english/content/uploadContent", new Callback<Object, Part[]>() {
                        @Override
                        public Part[] call(Object param) {

                            try {
                                File file = new File(URI.create(timeStemp.getAudioPath()).getPath());
                                Part[] parts = {
                                    new FilePart(IMAGE_PATH, file),
                                    new FilePart(AUDIO_PATH, file),
                                    new StringPart("content", timeStemp.getTargetText(), Charset.forName("utf-8").displayName())
                                };

                                return parts;
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(UpLoadController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            return null;
                        }
                    });
                    // upLoadFile.testUserInfoSync(timeStemp.getAudioPath(),timeStemp.getTargetText());
                } catch (Exception ex) {
                    Logger.getLogger(UpLoadController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
