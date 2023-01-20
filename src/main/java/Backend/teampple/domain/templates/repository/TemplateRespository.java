package Backend.teampple.domain.templates.repository;

import Backend.teampple.domain.templates.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TemplateRespository extends JpaRepository<Template, Long> {

    @Query("select t from Template t order by t.id")
    List<Template> findAllOrderById();
}
