package com.example.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpClientUtil {

    public static String doPost(String url) {

        HttpPost httpPost = new HttpPost(url);

        return getResult(httpPost);
    }

    public static String doGet(String url) {

        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String doGetAndCookie(String url, String cookies) {

        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.addHeader(new BasicHeader("Cookie", cookies));
        return getResult(httpGet);
    }

    public static String getResult(HttpRequestBase req) {
        // 创建Httpclient对象
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            // 执行请求
            CloseableHttpResponse response = httpclient.execute(req);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
