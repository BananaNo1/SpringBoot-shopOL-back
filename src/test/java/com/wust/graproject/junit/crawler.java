package com.wust.graproject.junit;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * @ClassName crawler
 * @Description TODO
 * @Author leis
 * @Date 2019/1/4 17:01
 * @Version 1.0
 **/
public class crawler {

    @Test
    public void computer() throws IOException {
        String url = "https://list.jd.com/list.html?cat=670%2C671%2C1105&go=0";

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        // Document document = Jsoup.connect(url).get();
        // Elements elementsByClass = document.getElementsByClass("gl-item");

      /*  for (Element element : elementsByClass) {
            String text = element.select("div[class=p-price]").select("strong").select("i").text();
            System.out.println(text);
        }*/
    }
}
