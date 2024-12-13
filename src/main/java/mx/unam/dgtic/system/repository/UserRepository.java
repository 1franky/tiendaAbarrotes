package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.auth.model.UserInfo;
import mx.unam.dgtic.system.entity.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 03/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT role.usr_id AS id , role.usr_role_name AS nombre " +
                    "FROM sec_role role;"
    )
    List<Roles> getRoles();

    @Query(
            nativeQuery = true,
            value = "SELECT role.usr_id AS id , role.usr_role_name AS nombre " +
                    "FROM sec_role role " +
                    "WHERE role.usr_id = :id;"
    )
    Roles getRoleById(@Param("id") Long id);

    @Query("SELECT u " +
            "FROM UserInfo u " +
            "LEFT JOIN u.useInfoRoles r " +
            "WHERE " +
            "LOWER(u.useFirstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.useLastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.useEmail) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(u.useIdStatus AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', u.useCreatedDate, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', u.useModifiedDate, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(r.usrRoleName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<UserInfo> searchByAllColumns(@Param("search") String search, Pageable pageable);


}
