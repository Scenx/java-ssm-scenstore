package com.scen.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * @author Scen
 * @date 2018/4/3 13:28
 */
public class HttpClientTest {
    @Test
    public void doGet() throws Exception {
//        创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        创建一个get对象
        HttpGet get = new HttpGet("http://www.ncwuhz.cn");
//        执行请求
        CloseableHttpResponse response = httpClient.execute(get);
//        取响应结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity, "UTF-8");
        System.out.println(s);
//        关闭httpclient
        response.close();
        httpClient.close();
    }
}
