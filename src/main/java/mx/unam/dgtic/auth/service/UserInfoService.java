package mx.unam.dgtic.auth.service;

import mx.unam.dgtic.auth.dto.UserInfoDTO;
import mx.unam.dgtic.auth.exception.UserInfoNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {
    List<UserInfoDTO> findAll();
    UserInfoDTO findById(Long id) throws UserInfoNotFoundException;
    UserInfoDTO save(UserInfoDTO userAdmin) throws UserInfoNotFoundException;
    Optional<UserInfoDTO> findByUseEmail(String email) throws UserInfoNotFoundException;
}
