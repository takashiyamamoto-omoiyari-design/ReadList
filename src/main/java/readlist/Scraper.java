package readlist;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
    public static void main(String[] args) {
        String baseUrl = "https://tenshoku.mynavi.jp/list/o1A/";
        String firstPageUrl = baseUrl + "?jobsearchType=6&searchType=1";

        try {
            // 最初のページをスクレイピング
            scrapePage(firstPageUrl);

            // 2ページ目から5ページ目までをスクレイピング
            for (int page = 2; page <= 5; page++) {
                String pageUrl = baseUrl + "pg" + page + "/?jobsearchType=6&searchType=1";
                scrapePage(pageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scrapePage(String url) throws IOException {
        // URLからHTMLを取得
        Document document = Jsoup.connect(url).get();

        // 仕事内容の要素を取得
        Elements jobElements = document.select(".cassetteRecruit__content");

        // 仕事内容を表示
        for (Element jobElement : jobElements) {
            String jobTitle = jobElement.select("h3").first().text().trim();
            String jobDescription = jobElement.select(".cassetteRecruit__attribute").first().text().trim();
            String link = jobElement.select("a").first().attr("href");
            String linkText = jobElement.select("a").first().text().trim();

            // 勤務地の要素を取得
            Element locationElement = document.select("html > body > div:nth-of-type(1) > div:nth-of-type(3) > form > div > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(1) > table > tbody > tr:nth-of-type(3) > td").first();
            String location = (locationElement != null) ? locationElement.text().trim() : "";

            // 初年度年収の要素を取得
            Element incomeElement = document.select("html > body > div:nth-of-type(1) > div:nth-of-type(3) > form > div > div:nth-of-type(2) > div > div:nth-of-type(2) > div:nth-of-type(1) > table > tbody > tr:nth-of-type(4) > td").first();
            String annualIncome = (incomeElement != null) ? incomeElement.text().trim() : "";

            System.out.println("仕事タイトル: " + jobTitle);
            System.out.println("仕事内容: " + jobDescription);
            System.out.println("リンク: " + link);
            System.out.println("リンクテキスト: " + linkText);
            System.out.println("勤務地: " + location);
            System.out.println("給与: " + annualIncome);
            System.out.println("--------------------");
        }
    }
}
