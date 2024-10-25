package mx.unam.dgtic.repository;

import mx.unam.dgtic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByTipoUser(Integer tipoUser);

    @Query(name = "User.findByTipoUserAndName")
    List<User> findByTipoUserAndName(Integer tipoUser, String name);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.image W" +
            "HERE u.id = :userId")
    Optional<User> findUserWithImage(String userId);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.image " +
            "WHERE u.tipoUser = 1")
    List<User> findAdminUsersWithImages();

}