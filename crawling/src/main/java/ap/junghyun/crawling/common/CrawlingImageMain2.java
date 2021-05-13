package ap.junghyun.crawling.common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CrawlingImageMain2 {
    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("https://burst.shopify.com/").get();
        System.out.println("doc ::::::::::::" + doc);
        String folder = doc.attr("data-photo-title");
        System.out.println("folder :::::::::" + folder);
        Element element = doc.select("div.js-masonry-grid").get(0);
        System.out.println("element" + element);
        Elements img = element.select("div.js-masonry-grid");
        System.out.println("img" + img.size());
        int page = 0;
        for (Element e : img) {
            String url = e.getElementsByAttribute(":src").attr("src");

            URL imgUrl = new URL(url);
            BufferedImage jpg = ImageIO.read(imgUrl);
            File file = new File("C:\\pictureFile" + folder + "\\" + page + ".jpg");
            ImageIO.write(jpg, "jpg", file);
            page += 1;
        }
    }
}
