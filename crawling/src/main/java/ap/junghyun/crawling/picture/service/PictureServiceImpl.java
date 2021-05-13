package ap.junghyun.crawling.picture.service;

import ap.junghyun.crawling.common.controller.Crawler;
import ap.junghyun.crawling.common.service.AbstractService;
import ap.junghyun.crawling.common.service.CrawlerServiceImpl;
import ap.junghyun.crawling.picture.domain.Picture;
import ap.junghyun.crawling.picture.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PictureServiceImpl extends AbstractService<Picture> implements PictureService {
    private final PictureRepository pictureRepository;

    @Override
    public Long save(Picture picture) {
        return (pictureRepository.save(picture) != null) ? 1L : 0L;
    }

    @Override
    public Optional<Picture> findById(Long id) {
        return pictureRepository.findById(id);
    }

    @Override
    public List<Picture> findAll() {
        return pictureRepository.findAll();
    }

    @Override
    public Long count() {
        return pictureRepository.count();
    }

    @Override
    public Optional<Picture> getOne(Long id) {
        return Optional.of(pictureRepository.getOne(id));
    }

    @Override
    public Long delete(Picture picture) {
        pictureRepository.delete(picture);

        return (findById(picture.getPictureId()).orElse(null) == null) ? 1L : 0L;
    }

    @Override
    public Boolean existsById(long id) {
        return null;
    }

    @Override
    public Page<Picture> findAll(Pageable pageable) {
        return pictureRepository.findAll(pageable);
    }

    @Override
    public List<Picture> saveAll(Crawler crawler) throws IOException {
        Document document = new CrawlerServiceImpl().connectUrl(crawler.getUrl()); // "https://news.daum.net/society"
        pictureRepository.deleteAll();

        Elements elements = document.select(crawler.getCssQuery());
        // "div.cont_aside>ol>li>strong>a"

        List<Picture> newsList = new ArrayList<>();

        for (int i = 0; i < elements.size(); i++) {
            Picture picture = new Picture();

            picture.setTitle(elements.get(i).text());
            picture.setAddress(elements.get(i).attr("href"));
            picture.setCategory(crawler.getCategory());
            newsList.add(picture);
//            newsRepository.save(news);

        }

        return newsList;
    }
}
