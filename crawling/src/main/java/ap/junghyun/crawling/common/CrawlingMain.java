package ap.junghyun.crawling.common;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CrawlingMain {
    public static void main(String[] args)throws IOException {
//        System.out.println("작동되나요?::::::::::");
//        String url = "https://news.naver.com/";
        String url = "https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=100";
//        System.out.println("여긴요?");
        String cssQuery = "div.classfy ul>li>a";
//        String cssQuery = "div.mtype_list_wide>ul>li>a>strong";
//        System.out.println("여긴가?");


        String filePath = "C:\\pictureFile\\junghyun.txt" ;
//        System.out.println("여기지?");
        Crawler crawler = new Crawler();
        Service service = new Service();
        crawler.setUrl(url);
        crawler.setCssQuery(cssQuery);
//        System.out.println("111111");
        Document document = service.connectUrl(crawler.getUrl());
        Elements elements = document.select(crawler.getCssQuery());
        List<Picture> list = new ArrayList<>();
//        System.out.println("222222");
        try {
//            System.out.println("Try 다");
            // FileOutputStream : 데이터를 파일에 바이트 스트림으로 저장하기 위해 사용한다.
            BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "euc-kr"));
//            System.out.println("Try 다 11111");
            for (int i = 0; i < elements.size(); i++) {
                Picture picture = new Picture();
//                System.out.println("Try 다 222");
                picture.setTitle(elements.get(i).text());
                picture.setAddress(elements.get(i).attr("href"));
//                System.out.println("Try 다333");
                picture.setCategory(crawler.getCategory());
                System.out.println(picture.toString());
                list.add(picture);
//                System.out.println("Try 다444");
            }
            if (list.isEmpty()) {
//                System.out.println("크롤링 된 값이 없습니다:::::::::: !");
            } else {
                for (Picture f : list) {
                    fw.write(f.toString() + ",");
                    fw.newLine();
//                    System.out.println("크롤링 된 값이 있습니다:::::::::: !");
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
class Crawler {
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
    

    public static class Picture{
        private long pictureId;
        private String category;
        private String title;
        private String address;
        public long getPictureId() {
            return pictureId;
        }
        public void setPictureId(Long pictureId) {
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
    }
}
class Picture {

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
class Service{
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
    List<?> scrapPicture(Crawler crawler) throws IOException {
        Document pictureDocument = connectUrl(crawler.getUrl());
        Elements pictureElements = pictureDocument.select(crawler.getCssQuery());

        for (int i = 0; i < pictureElements.size(); i++) {
            Picture picture = new Picture();
            picture.setTitle(pictureElements.get(i).text());
            picture.setAddress(pictureElements.get(i).attr("href"));
            picture.setCategory(crawler.getCategory());
        }

        return null;
    }

}

