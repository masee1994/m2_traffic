package com.tech.m2.traffic_api;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;



import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Seoul_traffic_api {
    private final static String api_Key = "6841425a536d617338336462594277";
    private final static String weather_api_Key = "62a6ff71e40cfec955c8d238ea994743";

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

    public static String traffic_API(String service, int start_index, int end_index, int value) {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
            urlBuilder.append("/" + api_Key); /*Service Key*/
            urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8"));
            urlBuilder.append("/" + start_index);
            urlBuilder.append("/" + end_index);
            urlBuilder.append("/" + value);
            String xml_code = urlConnection(urlBuilder);
            return xml_code;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String weather_API() {
        try {
            String city="seoul,kr";
            String language="kr";
            StringBuilder urlBuilder = new StringBuilder("http://api.openweathermap.org/data/2.5/weather"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("q", "UTF-8") + "=" + city);
            urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=" + language);
            urlBuilder.append("&" + URLEncoder.encode("appid", "UTF-8") + "=" + weather_api_Key);
            String xml_code = urlConnection(urlBuilder);
            return xml_code;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    http://api.openweathermap.org/data/2.5/weather?q=seoul,kr&lang=kr&APPID=62a6ff71e40cfec955c8d238ea994743

    public static String Traffic_crawling(){
        String data="";
        try {
            // 모든 SSL 인증서를 신뢰하도록 설정
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) { }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // HtmlUnit 설정
            WebClient webClient = new WebClient();
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setUseInsecureSSL(true);

            // 웹페이지 로드
            HtmlPage page = webClient.getPage("https://topis.seoul.go.kr");
            webClient.waitForBackgroundJavaScript(10_000);

            // 페이지 내용을 Jsoup으로 파싱
            String pageContent = page.asXml();
            Document doc = Jsoup.parse(pageContent);

            // 서울시 전체속도 추출
            Element seoulSpeedElement = doc.selectFirst("span#spdStat1");
            String seoulSpeed = seoulSpeedElement != null ? seoulSpeedElement.text() : "N/A";

            // 도심 전체속도 추출
            Element downtownSpeedElement = doc.selectFirst("span#spdStat2");
            String downtownSpeed = downtownSpeedElement != null ? downtownSpeedElement.text() : "N/A";

            // 출력
            System.out.println("----------------------------------------------");
            System.out.println("서울시 전체속도: " + seoulSpeed);
            System.out.println("도심 전체속도: " + downtownSpeed);
            System.out.println("----------------------------------------------");

            data = seoulSpeed + "&" + downtownSpeed;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return data;
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
