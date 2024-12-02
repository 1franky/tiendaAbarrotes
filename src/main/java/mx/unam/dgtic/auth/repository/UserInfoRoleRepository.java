package mx.unam.dgtic.auth.repository;

import mx.unam.dgtic.auth.model.UserInfoRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserInfoRoleRepository extends JpaRepository<UserInfoRole, Long> {
    UserInfoRole findByUsrRoleName(String role);
    List<UserInfoRole> findAllByOrderByUsrIdAsc();
    List<UserInfoRole> findAllByOrderByUsrRoleNameAsc();
}
