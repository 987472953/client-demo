package com.example.test;

import com.example.Client;

public class ClientTest {
    private static Client client = new Client();
    public static void main(String[] args) {
        uploadTest1();
    }

    public static void uploadTest1(){
        String s = client.saveFile("/Users/dengyiqing/ad.xlsx");
        System.out.println(s);

    }
}
