package ap.junghyun.crawling.common;

import lombok.extern.java.Log;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Log
public class CrawlingImageMain {
    public static void main(String[] args)throws IOException {
        String url = "https://burst.shopify.com/";
        String cateStr = "city";
        String cssQuery = "div.js-masonry-grid a>div>img";
//        String cssQuery2 = "div.js-masonry-grid a>div>img";
        String cssQuery2 = "div.js-masonry-grid div.photo-tile>p";

        String filePath = "C:\\pictureFile\\1.csv";
        CrawlerImage crawlerImage = new CrawlerImage();
        ServiceImage serviceImage = new ServiceImage();
        crawlerImage.setUrl(url + cateStr); // String url 할당
        crawlerImage.setCssQuery(cssQuery); // String cssQuery 할당
        crawlerImage.setCssQuery2(cssQuery2); // String cssQuery 할당
        Document document = serviceImage.connectUrl(crawlerImage.getUrl());
        Elements elements = document.select(crawlerImage.getCssQuery());
        Elements elements2 = document.select(crawlerImage.getCssQuery2());
        List<PictureImage> list = new ArrayList<>();
        try {
            System.out.println("진입했나?");

//            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "euc-kr"));
            DataOutputStream fw = new DataOutputStream(new FileOutputStream(filePath, true));

            System.out.println("imge");
            System.out.println("진입했나?2");
            for (int i = 0; i < elements2.size(); i++) {
                PictureImage pictureImage = new PictureImage();
//                pictureImage.setTitle(elements.get(i).text());
                pictureImage.setTitle(elements2.get(i).text());
                pictureImage.setAddress(elements.get(i).attr("src"));
                pictureImage.setCategory(cateStr);

                System.out.println(pictureImage.toString());
                list.add(pictureImage);

            }
            if (list.isEmpty()) {
                System.out.println("크롤링 된 값이 없습니다:::::::::: !");
            } else {
                for (PictureImage f : list) {
//                    fw.write(f.toString() + ",");
//                    fw.newLine();
                    System.out.println(",");
                    byte[] arr = f.toString().getBytes("UTF-8");
                    fw.write(arr);
                }
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("Exceoption 이다");
            e.printStackTrace();
        }
    }
}
class CrawlerImage {
    private String url;
    private String cssQuery;
    private String cssQuery2;
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
    public String getCssQuery2() {
        return cssQuery2;
    }
    public void setCssQuery(String cssQuery) {
        this.cssQuery = cssQuery;
    }
    public void setCssQuery2(String cssQuery2) {
        this.cssQuery2 = cssQuery2;
    }


}
class PictureImage {

    private long pictureId;
    private String category;
    private String title;
    private String address;

    public long getPictureId() {
        return pictureId;
    }

    public void setPictureId(long pictureId) {
        this.pictureId = pictureId;
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
    public String toString(){
        return category+","+title+","+address;
    }
}
class ServiceImage{
    List<PictureImage> saveAll(CrawlerImage crawlerImage) throws IOException {
        Document document = new ServiceImage().connectUrl(crawlerImage.getUrl());
        Elements elements = document.select(crawlerImage.getCssQuery());
        Elements elements2 = document.select(crawlerImage.getCssQuery2());
        List<PictureImage> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            PictureImage pictureImage = new PictureImage();
            pictureImage.setTitle(elements.get(i).text());
            pictureImage.setTitle(elements2.get(i).text());
            pictureImage.setAddress(elements.get(i).attr("src"));
            pictureImage.setAddress(elements2.get(i).attr("src"));
            pictureImage.setCategory(crawlerImage.getCategory());
            pictureImage.setCategory(crawlerImage.getCssQuery2());
            list.add(pictureImage);
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
    List<?> scrapPicture(CrawlerImage crawlerImage) throws IOException {
        Document pictureDocument = connectUrl(crawlerImage.getUrl());
        Elements pictureElements = pictureDocument.select(crawlerImage.getCssQuery());
        Elements pictureElements2 = pictureDocument.select(crawlerImage.getCssQuery2());

        for (int i = 0; i < pictureElements.size(); i++) {
            PictureImage pictureImage = new PictureImage();
            pictureImage.setTitle(pictureElements.get(i).text());
            pictureImage.setTitle(pictureElements2.get(i).text());
            pictureImage.setAddress(pictureElements.get(i).attr("src"));
            pictureImage.setAddress(pictureElements2.get(i).attr("src"));
            pictureImage.setCategory(crawlerImage.getCategory());
            pictureImage.setCategory(crawlerImage.getCssQuery2());
        }

        return null;
    }

}

