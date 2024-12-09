package mx.unam.dgtic.system.service.user;

import mx.unam.dgtic.auth.model.UserInfo;
import mx.unam.dgtic.auth.model.UserInfoRole;
import mx.unam.dgtic.system.entity.Roles;
import mx.unam.dgtic.system.service.BaseService;

import java.util.List;
import java.util.Optional;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 03/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

public interface UserService extends BaseService<UserInfo> {

    List<Roles> getRoles();
    UserInfo findById(Long id);
    Optional<UserInfoRole> getRoleById(long id);

}
