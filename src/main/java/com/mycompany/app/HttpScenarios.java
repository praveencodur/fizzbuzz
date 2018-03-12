package com.mycompany.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class HttpScenarios {
/*
    private String baseUrl = "http://www.google.com/search?q=mkyong";
    private AtomicLong atomicLong = new AtomicLong(10000);
    private String name;

    public HttpScenarios(String baseUrl, String name, int weight){
        super(weight);
        this.baseUrl = baseUrl;
        this.name =  name;
    }

    public void execute(){

        String url = baseUrl + atomicLong.getAndIncrement();

        try {
            long startTime = System.currentTimeMillis();
            int responseSize =  sendGet(url);
            long elapsed = System.currentTimeMillis() - startTime;
            Locust.getInstance().recordSuccess("http", getName(), elapsed, responseSize);
        }catch(Exception e){
            Locust.getInstance().recordFailure("http",getName(),elapsed,e.getLocalizedMessage());
        }

    }
    private int sendGet(String url) throws Exception {

        //String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        if(responseCode != 200){
            throw new RuntimeException("Response Code not 200");
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.length();
    }*/
}
