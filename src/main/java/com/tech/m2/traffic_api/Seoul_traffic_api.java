package com.tech.m2.traffic_api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Seoul_traffic_api {
    private final static String api_Key = "6841425a536d617338336462594277";

    public static String traffic_API(String service, int start_index, int end_index) {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" + api_Key); /*Service Key*/
            urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8"));
            urlBuilder.append("/" + service);
            urlBuilder.append("/" + start_index);
            urlBuilder.append("/" + end_index);
            String xml_code = urlConnection(urlBuilder);
            System.out.println(urlBuilder.toString());
            return xml_code;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String traffic_API(String service, int start_index, int end_index, int date) {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" + api_Key); /*Service Key*/
            urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8"));
            urlBuilder.append("/" + start_index);
            urlBuilder.append("/" + end_index);
            String xml_code = urlConnection(urlBuilder);
            ;
            return xml_code;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String urlConnection(StringBuilder urlBuilder) {
        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line + "\n");
            }
            rd.close();
            conn.disconnect();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
