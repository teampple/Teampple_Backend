package Backend.teampple.domain.invitations.repository;

import Backend.teampple.domain.invitations.entity.Invitation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {
    Invitation findByCode(String code);
}
