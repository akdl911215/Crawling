package ap.junghyun.crawling.picture.service;

import ap.junghyun.crawling.common.controller.Crawler;
import ap.junghyun.crawling.picture.domain.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface PictureService {

    List<Picture> saveAll(Crawler crawler) throws IOException;
    Page<Picture> findAll(final Pageable pageable);
}
