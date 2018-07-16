/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leqienglish.client.fw.sf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import xyz.tobebetter.entity.Message;

/**
 * rest客户端
 *
 * @author zhuqing
 */
@Lazy
@Component("RestClient")
public class RestClient {

    private static RestTemplate restTemplate;

//    private String serverPath = "http://47.104.98.43:80/ToBeBetter";
    private String serverPath = "http://127.0.0.1:8080";
    protected final ObjectMapper mapper = new ObjectMapper();

    static {
        // 长连接保持30秒
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        // 总连接数
        pollingConnectionManager.setMaxTotal(1000);
        // 同路由的并发数
        pollingConnectionManager.setDefaultMaxPerRoute(1000);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        // 重试次数，默认是3次，没有开启
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
        // 保持长连接配置，需要在头添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

//        RequestConfig.Builder builder = RequestConfig.custom();
//        builder.setConnectionRequestTimeout(200);
//        builder.setConnectTimeout(5000);
//        builder.setSocketTimeout(5000);
//
//        RequestConfig requestConfig = builder.build();
//        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        headers.add(new BasicHeader("Connection", "Keep-Alive"));

        httpClientBuilder.setDefaultHeaders(headers);

        HttpClient httpClient = httpClientBuilder.build();

        // httpClient连接配置，底层是配置RequestConfig
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(5000000);
        // 数据读取超时时间，即SocketTimeout
        clientHttpRequestFactory.setReadTimeout(5000000);
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(200);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        // clientHttpRequestFactory.setBufferRequestBody(false);

        // 添加内容转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        // messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

//        LOGGER.info("RestClient初始化完成");
    }

    public <T> T post(String path, Object obj, MultiValueMap<String, String> parameter, Class<T> claz) throws Exception {
        return excute(HttpMethod.POST, path, obj, parameter, claz);
    }

    public <T> T put(String path, Object obj, MultiValueMap<String, String> parameter, Class<T> claz) throws Exception {
        return excute(HttpMethod.PUT, path, obj, parameter, claz);
    }

    public <T> T delete(String path, Object obj, MultiValueMap<String, String> parameter, Class<T> claz) throws Exception {
        return excute(HttpMethod.DELETE, path, obj, parameter, claz);
    }

    public <T> T upload(String path, MultiValueMap<String, Object> value, MultiValueMap<String, String> parameter, Class<T> claz) throws Exception {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverPath + "/" + path).queryParams(parameter);

        HttpEntity entity = new HttpEntity(value, initHeaders());
        //  entity.getHeaders().
        ResponseEntity resEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, Message.class, new HashMap());
        Message resultMessage = (Message) resEntity.getBody();

        if (Objects.equal(resultMessage.getStatus(), Message.ERROR)) {
            throw new Exception(resultMessage.getMessage());
        }

        return mapper.readValue(resultMessage.getData(), claz);
    }

    public <T> T get(String path, MultiValueMap<String, String> parameter, Class<T> claz) throws Exception {
        return excute(HttpMethod.GET, path, null, parameter, claz);
    }

    private <T> T excute(HttpMethod method, String path, Object obj, MultiValueMap<String, String> parameter, Class<T> claz) throws Exception {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(serverPath + "/" + path).queryParams(parameter);
        HttpEntity entity = new HttpEntity(obj, initHeaders());
        ResponseEntity resEntity = restTemplate.exchange(builder.toUriString(), method, entity, Message.class);
        Message resultMessage = (Message) resEntity.getBody();

        if (Objects.equal(resultMessage.getStatus(), Message.ERROR)) {
            throw new Exception(resultMessage.getMessage());
        }
        if (claz == null) {
            return null;
        }

        return mapper.readValue(resultMessage.getData(), claz);
    }

    private HttpHeaders initHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }

    /**
     * 下载文件
     *
     * @param path
     * @param filePath
     * @param hasdownload
     * @throws MalformedURLException
     * @throws IOException
     * @throws Exception
     */
    public void downLoad(String path, String filePath, Consumer<Double> hasdownload) throws MalformedURLException, IOException, Exception {
        //创建一个URL对象
        URL url = new URL(serverPath + "/" + path);
        //创建一个HTTP链接
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setConnectTimeout(300000);
        urlConn.connect();

        if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new Exception(urlConn.getResponseMessage());
        }

        InputStream inputStream = urlConn.getInputStream();
        int totalLength = inputStream.available();
        OutputStream os = new FileOutputStream(filePath);
        int length;
        int lengtsh = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            os.write(bytes, 0, length);
            //获取当前进度值
            lengtsh += length;
            System.err.print("lengtsh:" + lengtsh + ",totalLength:" + totalLength);
            //把值传给handler
            if (hasdownload != null) {
                hasdownload.accept(lengtsh * 1.0 / totalLength);
            }
        }
        //关闭流
        inputStream.close();
        os.close();
        os.flush();

    }

}
