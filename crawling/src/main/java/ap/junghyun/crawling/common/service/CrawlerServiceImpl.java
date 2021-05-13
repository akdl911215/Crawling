package ap.junghyun.crawling.common.service;

import ap.junghyun.crawling.common.controller.Crawler;
import ap.junghyun.crawling.picture.domain.Picture;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class CrawlerServiceImpl implements CrawlerService{

    public static Document connectUrl(String url) throws IOException {
        return Jsoup
                .connect(url)
                .method(Connection.Method.GET)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64; rv:10.0) " +
                        "Gecko/20100101 Firefox/10.0 " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/51.0.2704.106 Safari/537.36")
                .execute().parse();
    }


    @Override
    public List<?> scrapPicture(Crawler cralwer) throws IOException {
        Document document = connectUrl(cralwer.getUrl()); // "https://news.daum.net/society"
//        List<Cralwer.Bugs>

        Elements elements = document.select(cralwer.getCssQuery());
        // "div.cont_aside>ol>li>strong>a"

        for (int i = 0; i < elements.size(); i++) {
            Picture picture = new Picture();

            picture.setTitle(elements.get(i).text());
            picture.setAddress(elements.get(i).attr("href"));
            picture.setCategory(cralwer.getCategory());

        }
//        return newsRepository.count() > 0L ? 1L : 0L;
        return null;
    }

}
