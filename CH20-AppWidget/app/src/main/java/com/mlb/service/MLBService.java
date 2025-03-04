package com.mlb.service;

import android.util.Log;

import com.mlb.vo.MLB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MLBService {
    private String mlb_url;
    public MLBService(String mlb_url) {
        this.mlb_url = mlb_url;
    }
    public MLB getMLBByTeamId(int teamId) {
        MLB mlb = null;
        try {
            String urlString = mlb_url + teamId;
            URL url = new URL(urlString);
            URLConnection URLConn = url.openConnection();
            URLConn.setRequestProperty("User-agent", "IE/6.0");
            InputStream is = URLConn.getInputStream();

            // Parsing data --------------------------------------------
            Document doc = Jsoup.parse(is, "utf-8", urlString);
            // 取得隊名
            String teamName = doc.select("div.txt").first().text().trim();
            // 取得隊徽URL
            String teamLogoURL = doc.select("div.pic").first().select("img").attr("src").trim();
            // 取得更新時間
            String updatetime = doc.select("span.iupdatetime").first().text().trim();
            // 取得記錄
            String teamRecord = doc.select("div.status").first().text().trim();

            mlb = new MLB(teamName, teamLogoURL, updatetime, teamRecord);
            System.out.println(doc.toString());
            is.close();
        } catch (Exception e) {
            Log.i("mylog", e.toString());
            e.printStackTrace();
        }

        return mlb;
    }
}
