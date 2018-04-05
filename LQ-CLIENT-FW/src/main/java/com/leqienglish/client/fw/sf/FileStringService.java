/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.sf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author duyi
 */
@Lazy
@Component("fileService")
public class FileStringService extends Service {

    @Cacheable(value = "file", key = "#filePath")
    public String getString(String filePath) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        InputStream in = new FileInputStream(file);
        String fileContent = inputStream2String(in);
        in.close();
        return fileContent;
    }

    @Cacheable(value = "file", key = "#filePath")
    public void getString(String filePath, String fileContent) throws FileNotFoundException, IOException {
        File file = new File(filePath);
        OutputStream os = new FileOutputStream(file);
        outPutStream2String(os, fileContent);
        os.close();
    }

    private String inputStream2String(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        in.close();
        return buffer.toString();
    }

    private void outPutStream2String(OutputStream os, String fileContent) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os));
        out.write(fileContent, 0, fileContent.length());
        out.close();
    }
}
