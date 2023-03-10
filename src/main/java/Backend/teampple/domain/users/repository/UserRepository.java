package Backend.teampple.domain.users.repository;

import Backend.teampple.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);

    Optional<User> findByAuthKey(String authKey);

    boolean existsByAuthKey(String authKey);

    @Query("select u from User u where u = :user")
    Optional<User> findByUserWithUserProfile(@Param("user") User user);

    @Query("select u from User u join fetch u.userProfile where u.id = :id")
    Optional<User> findByIdWithUserProfile(@Param("id") Long id);

    @Query("select u from User u join fetch u.userProfile where u.authKey = :id")
    Optional<User> findByKakaoIdWithUserProfile(@Param("id") String id);
}
