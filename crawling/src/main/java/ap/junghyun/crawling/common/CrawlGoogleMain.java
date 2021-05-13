package ap.junghyun.crawling.common;

import ap.junghyun.crawling.picture.domain.Picture;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class CrawlGoogleMain {
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; //드라이버 ID
    public static final String WEB_DRIVER_PATH = "C:\\pictureFile\\chromedriver.exe"; //드라이버 경로
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\pictureFile\\result.csv";
        try {
            System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        String url = "https://burst.shopify.com/yoga";
        driver.get(url);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Crawler crawler = new Crawler();
        Service service = new Service();
//        crawler.setUrl(url);
//        crawler.setCssQuery(cssQuery);
//        Document document = service.connectUrl(crawler.getUrl());
        //System.out.println(document);
//        Elements elements  = document.select(".cCont_goodsSet .goods_name a");
//        System.out.println(elements);
//        Elements elements = document.select(crawler.getCssQuery());
//        System.out.println(elements);
        List<Picture> list = new ArrayList<>();
        List<WebElement> el3 = driver.findElements(By.cssSelector("div"));
        try{
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"euc-kr"));
            for (int i = 0; i < el3.size(); i++) {
                Picture picture = new Picture();
                picture.setTitle(el3.get(i).getText());
                picture.setAddress(el3.get(i).getAttribute("href"));
                picture.setCategory(crawler.getCategory());
                System.out.println(picture.getAddress());
                list.add(picture);
            }
            if(list.isEmpty()){
                System.out.println("크롤링 된 값이 없습니다. !");
            }else{
                for(Picture f : list){
                    fw.write(f.toString()+",");
                    fw.newLine();
                }
            }
            fw.flush();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
class GoogleCrawlers {
    private String url;
    private String cssQuery;
    private String category;
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getCssQuery() {
        return cssQuery;
    }
    public void setCssQuery(String cssQuery) {
        this.cssQuery = cssQuery;
    }
    public static class Funding{
        private long tumblebuckId;
        private String category;
        private String title;
        private String address;
        public long getTumblebuckId() {
            return tumblebuckId;
        }
        public void setTumblebuckId(Long tumblebuckId) {
            this.tumblebuckId = tumblebuckId;
        }
        public String getCategory() {
            return category;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
    }
}
class GooglePicture {
    private long tumblebuckId;
    private String category;
    private String title;
    private String address;
    public long getTumblebuckId() {
        return tumblebuckId;
    }
    public void setTumblebuckId(long tumblebuckId) {
        this.tumblebuckId = tumblebuckId;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
class GoogleService{
    List<Picture> saveAll(Crawler crawler) throws IOException {
        Document document = connectUrl(crawler.getUrl());
        Elements elements = document.select(crawler.getCssQuery());
        List<Picture> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            Picture picture = new Picture();
            picture.setTitle(elements.get(i).text());
            picture.setAddress(elements.get(i).attr("href"));
            picture.setCategory(crawler.getCategory());
            list.add(picture);
        }
        return list;
    }
    Document connectUrl(String url) throws IOException {
        return Jsoup.connect(url) // 클래스 안에 이너클래스
                .method(Connection.Method.GET)
                .userAgent("Mozilla/5.0 (X11; Linux x86_64; rv:10.0) " +
                        "Gecko/20100101 Firefox/10.0 " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                        "Chrome/51.0.2704.106 Safari/537.36")
                .execute().parse();
    }
    List<?> scrapFunding(Crawler crawler) throws IOException {
        Document fundingdocument = connectUrl(crawler.getUrl()); // jsoup 불변객체, "https://news.daum.net/society"
        //List<>
        Elements fundingelements = fundingdocument.select(crawler.getCssQuery());
        //"div.sect-movie-chart>ol>li>div.box-image>strong"
        for (int i = 0; i < fundingelements.size(); i++) {
            Picture picture = new Picture();
            picture.setTitle(fundingelements.get(i).text());
            picture.setAddress(fundingelements.get(i).attr("href"));
            picture.setCategory(crawler.getCategory());
        }
//        return repository.count() > 0L ? 1L: 0L;
        return null;
    }
}
