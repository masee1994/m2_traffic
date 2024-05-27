package com.tech.m2.traffic_api;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class TopisCrawling {
    static {
        // SSL 인증서 검증 비활성화
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {}
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Hostname verification 비활성화
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Map<String, String> getTrafficStats() {
        System.setProperty("webdriver.chrome.driver", "/Users/hanseulma/Documents/chromedriver-mac-arm64/chromedriver");

        // ChromeOptions 설정
        ChromeOptions options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("headless");

        // WebDriver 인스턴스를 생성합니다.
        WebDriver driver = new ChromeDriver(options);

        Map<String, String> trafficStats = new HashMap<>();

        try {
            // URL로 이동합니다.
            driver.get("https://topis.seoul.go.kr/");

            // 페이지가 완전히 로드될 때까지 기다립니다.
            Thread.sleep(5000); // 필요한 경우 더 긴 시간 대기

            // id가 spdStat1Det, spdStat1, spdStat2Det, spdStat2인 요소를 찾습니다.
            String[] ids = {"spdStat1Det", "spdStat1", "spdStat2Det", "spdStat2"};
            for (String id : ids) {
                WebElement element = driver.findElement(By.id(id));
                String text = element != null ? element.getText() : "N/A";
                trafficStats.put(id, text);
                System.out.println(id + " : " + text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // WebDriver를 종료합니다.
            driver.quit();
        }

        return trafficStats;
    }

//    public static void main(String[] args) {
//        TopisCrawling service = new TopisCrawling();
//        Map<String, String> stats = service.getTrafficStats();
//
//        System.out.println("Traffic Stats:");
//        for (Map.Entry<String, String> entry : stats.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
//    }

}
