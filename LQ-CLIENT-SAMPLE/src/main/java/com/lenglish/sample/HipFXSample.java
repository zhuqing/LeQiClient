package com.lenglish.sample;

import com.lenglish.sample.entity.Person;
import fxsampler.SampleBase;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.scenicview.ScenicView;

public abstract class HipFXSample extends SampleBase {

    public static String DOCUMENT_BASE = "";
    private static Boolean firstReload = true;
    /**
     * 项目信息
     */
    private static final ProjectInfo projectInfo = new ProjectInfo();

    /**
     * 项目名称
     *
     * @return
     */
    @Override
    public String getProjectName() {
        try {
//            HipProperties.initHipProperties();
        } catch (Exception ex) {
            Logger.getLogger(HipFXSample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Person person = new Person();
        person.setId("1");
        person.setAge(32);
        person.setName("江小鱼");

        return "HipApp-client-component";
    }

    /**
     * 版本号
     *
     * @return
     */
    @Override
    public String getProjectVersion() {
        return projectInfo.getVersion();
    }

    /**
     * 源码地址
     *
     * @return
     */
    @Override
    public String getSampleSourceURL() {
        return "D:\\work\\space\\HIS\\code\\client\\HIP-CLIENT-SAMPLE\\src\\main\\java\\" + getClass().getName().replace('.', '\\') + ".java";
    }

    @Override
    public String getControlStylesheetURL() {
        return "";
    }

    /**
     * 项目信息实体类
     */
    private static class ProjectInfo {

        private String version;

        public ProjectInfo() {
            InputStream s = getClass().getClassLoader().getResourceAsStream(
                    "META-INF/MANIFEST.MF");

            try {
                Manifest manifest = new Manifest(s);
                Attributes attr = manifest.getMainAttributes();
                version = attr.getValue("Implementation-Version");
            } catch (Throwable e) {
                System.out.println("Unable to load project version for ControlsFX "
                        + "samples project as the manifest file can't be read "
                        + "or the Implementation-Version attribute is unavailable.");
                version = "1.2";
            }
        }

        public String getVersion() {
            return version;
        }
    }

    @Override
    public Node getPanel(Stage stage) {
        DOCUMENT_BASE = this.getHostServices().getDocumentBase();
        DOCUMENT_BASE = DOCUMENT_BASE.substring(0, DOCUMENT_BASE.length() - 1);
        if (firstReload) {
            String filepath = this.getClass().getResource("/css/HipAPP.css").toString();
            stage.getScene().getStylesheets().add(filepath);
          //  ScenicView.show(stage.getScene());
            firstReload = false;
        }

        return null;
    }

}
