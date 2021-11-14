package com.example;

import com.example.util.HttpClientUtil;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author dengyiqing <dengyiqing@kuaishou.com>
 * Created on 2021-11-12
 */
public class Client {

    public String saveFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }
        InputStream ins;
        DataInputStream in;
        ByteArrayOutputStream outStream = null;
        DataOutputStream ds;
        String boundary = "********";
        try {
            URL url = new URL("http://127.0.0.1:8080/file/upload");
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlConnection;

            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.connect();
            ds = new DataOutputStream(connection.getOutputStream());
            in = new DataInputStream(new FileInputStream(file));
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + "\";filename=\"" + file.getName()
                    + "\"\r\n");
            int bytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytes = in.read(buffer)) != -1) {
                ds.write(buffer, 0, bytes);
            }
            ds.writeBytes("\r\n--" + boundary + "--\r\n");
            ds.flush();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ins = connection.getInputStream();
                outStream = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int count = -1;
                while ((count = ins.read(data, 0, 1024)) != -1) {
                    outStream.write(data, 0, count);
                }
                String result = outStream.toString("UTF-8");
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getFile(String uuid) {
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/file/file_info?uuid=" + uuid);
        return HttpClientUtil.getResult(httpGet);
    }

    public String downloadFile(String uuid) {
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/file/download?uuid=" + uuid);
        return HttpClientUtil.getResult(httpGet);
    }

}
