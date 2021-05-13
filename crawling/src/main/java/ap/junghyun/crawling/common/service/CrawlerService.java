package ap.junghyun.crawling.common.service;

import ap.junghyun.crawling.common.controller.Crawler;

import java.io.IOException;
import java.util.List;

public interface CrawlerService {
    List<?> scrapPicture(Crawler cralwer) throws IOException;
}
