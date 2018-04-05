/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bjgoodwill.hip.util.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Read {

    public static String read(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        String content = inputStream2String(in);
        in.close();
        return content;
    }

    public static String read(URL file) {
        String s = null;
        InputStream in = null;
        try {
            in = file.openStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (in == null) {
            System.err.println("文件不存在!");
        } else {
            try {
                return inputStream2String(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取FXMl内容
     *
     * @param path
     * @return
     */
    public static String readString(String path) {
        String text = null;
        InputStream is = Read.class.getResourceAsStream(path);
        try {
            text = inputStream2String(is);
        } catch (IOException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return text;
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n = 0; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n, "UTF-8"));
        }
        return out.toString();
    }
    
     public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int len = 0;

        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);
        }
        //关闭输入流  
        inStream.close();
        //把outStream里的数据写入内存  
        return outStream.toByteArray();
    }
}
