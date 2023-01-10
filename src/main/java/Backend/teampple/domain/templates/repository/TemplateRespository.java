package Backend.teampple.domain.templates.repository;

import Backend.teampple.domain.templates.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRespository extends JpaRepository<Template, Long> {
}
