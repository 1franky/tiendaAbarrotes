package mx.unam.dgtic.auth.service;

import mx.unam.dgtic.auth.dto.UserInfoRoleDTO;
import mx.unam.dgtic.auth.exception.UserInfoRoleNotFoundException;
import mx.unam.dgtic.auth.model.UserInfoRole;

import java.util.List;

public interface UserInfoRoleService {
    List<UserInfoRoleDTO> findAll();
    List<UserInfoRoleDTO> findAllOrderByUsrRoleName();
    UserInfoRoleDTO findById(Long id) throws UserInfoRoleNotFoundException;
    UserInfoRoleDTO save(UserInfoRoleDTO role);
    UserInfoRoleDTO convertEntityToDTO(UserInfoRole userInfo);
    UserInfoRole convertDTOtoEntity(UserInfoRoleDTO userInfo);
}
