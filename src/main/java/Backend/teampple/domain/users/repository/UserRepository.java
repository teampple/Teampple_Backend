package Backend.teampple.domain.users.repository;

import Backend.teampple.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);

    Optional<User> findByRefreshToken(String refreshToken);

    @Query("select u from User u join fetch u.userProfile where u.id = :id")
    Optional<User> getByIdWithUserProfile(Long id);

}
