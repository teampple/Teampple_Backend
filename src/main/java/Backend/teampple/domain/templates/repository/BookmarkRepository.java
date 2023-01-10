package Backend.teampple.domain.templates.repository;

import Backend.teampple.domain.templates.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
