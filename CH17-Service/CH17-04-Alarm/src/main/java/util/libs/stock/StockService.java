package util.libs.stock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class StockService {

    public Stock getStock(String s) {
        String source = "";
        try {
            String urlString = "https://tw.stock.yahoo.com/q/q?s=" + s;
            URL url = new URL(urlString);
            URLConnection URLConn = url.openConnection();
            URLConn.setRequestProperty("User-agent","IE/6.0");
            InputStream is = URLConn.getInputStream();
            // Parsing data --------------------------------------------
            Document doc = Jsoup.parse(is, "Big5", urlString);
            source = doc.select("td[nowrap]").text().trim();
            System.out.println(source);
            is.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return makeStock(source.trim());
    }

    private Stock makeStock(String source) {
        String[] data = source.split(" ");
        Stock stock = new Stock();
        stock.set時間(data[1]);
        stock.set成交(data[2]);
        stock.set買進(data[3]);
        stock.set賣出(data[4]);
        stock.set漲跌(data[5]);
        stock.set張數(data[6]);
        stock.set昨收(data[7]);
        stock.set開盤(data[8]);
        stock.set最高(data[9]);
        stock.set最低(data[10]);
        return stock;
    }
}
