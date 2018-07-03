/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.util.tran;

import com.leqienglish.util.md5.MD5;
import com.leqienglish.util.task.FutureTaskUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 调用百度翻译接口的工具
 * {"from":"en","to":"zh","trans_result":[{"src":"apple","dst":"\u82f9\u679c"}]}
 *
 * @author zhuleqi
 */
public class TranslateBaiduUtil {

    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    public final static String FROM = "en";
    public final static String TO = "zh";

    private final static String APPID = "20180505000153561";
    private final static String SECURITY_KEY = "7SDapXJkYMl_k3WMej6C";

    private final static String SRC = "src";

    private final static String DST = "dst";
    private final static String TRANS_RESULT = "trans_result";
    
    public static void transResult(String query, final Consumer<List<BaiduTranslateEntity>> consumer) throws InterruptedException, ExecutionException {
        transResult(query,FROM,TO,consumer);
    }

    public static void transResult(String query, String from, String to, final Consumer<List<BaiduTranslateEntity>> consumer) throws InterruptedException, ExecutionException {
        String params = buildParams(query);
        String path = TRANS_API_HOST + "?" + params;
        FutureTaskUtil.run(() -> {

            try {
                //创建一个URL对象
                URL url = new URL(path);
                //创建一个HTTP链接
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setConnectTimeout(10000);
                urlConn.connect();

                if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    consumer.accept(null);
                    return "";
                }

                InputStream inputStream = urlConn.getInputStream();
                OutputStream os = new ByteArrayOutputStream(urlConn.getContentLength());
                int length;

                byte[] bytes = new byte[1024];
                while ((length = inputStream.read(bytes)) != -1) {
                    os.write(bytes, 0, length);

                }
                String result = os.toString();
                //关闭流
                inputStream.close();
                os.close();
                os.flush();
                consumer.accept(toTrans(result));
                return "";

            } catch (MalformedURLException ex) {
                Logger.getLogger(TranslateBaiduUtil.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TranslateBaiduUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             consumer.accept(null);
             return "";

        });
        
        
    }

    private static List<BaiduTranslateEntity> toTrans(String str) {
        System.err.println(str);
        JSONObject jsonObject = new JSONObject(str);

        JSONArray arr = jsonObject.getJSONArray(TRANS_RESULT);
        List<BaiduTranslateEntity> trans = new ArrayList<BaiduTranslateEntity>(arr.length());
        for (int i = 0; i < arr.length(); i++) {
            JSONObject json = arr.getJSONObject(i);
            BaiduTranslateEntity translateEntity = new BaiduTranslateEntity();
            translateEntity.setDst(json.getString(DST));
            translateEntity.setSrc(json.getString(SRC));

            trans.add(translateEntity);

        }
        return trans;
    }

    private static String buildParams(String query) {

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());

        // 签名
        String src = APPID + query + salt + SECURITY_KEY; // 加密前的原文

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("q=").append(query);
        stringBuffer.append("&").append("from=").append(FROM);
        stringBuffer.append("&").append("to=").append(TO);
        stringBuffer.append("&").append("appid=").append(APPID);
        stringBuffer.append("&").append("salt=").append(salt);
        stringBuffer.append("&").append("sign=").append(MD5.md5(src));
        return stringBuffer.toString();
    }

}
