package ap.junghyun.crawling.picture.repository;

import ap.junghyun.crawling.picture.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
