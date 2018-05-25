/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.sf;

import javafx.util.Callback;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuqing
 */
@Lazy
@Component("UpLoadFileService")
public class UpLoadFileService {

    public final static String IMAGE_PATH = "imageFileName";
    public final static String AUDIO_PATH = "audioFileName";

    public void uploadFile(String path, Callback<Object, Part[]> partsCallback) throws Exception {

        if (path == null || partsCallback == null) {
            return;
        }

        PostMethod postMethod = new PostMethod(path);
        try {

            MultipartRequestEntity mre = new MultipartRequestEntity(partsCallback.call(null),
                    postMethod.getParams());
            postMethod.setRequestEntity(mre);
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
            int status = client.executeMethod(postMethod);
            System.err.println(status);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            postMethod.releaseConnection();
        }
    }
}
