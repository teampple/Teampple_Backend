package Backend.teampple.domain.files;

import Backend.teampple.domain.files.entity.File;
import Backend.teampple.domain.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<File, Long> {
    List<File> findAllByTaskOrderByUpdatedAt(Task task);
}
