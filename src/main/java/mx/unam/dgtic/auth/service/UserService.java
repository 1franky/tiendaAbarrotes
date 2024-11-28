package mx.unam.dgtic.auth.service;

import mx.unam.dgtic.auth.entity.User;
import mx.unam.dgtic.auth.exception.UserInfoNotFoundException;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 26/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

public interface UserService {

    User findByEmail(String email) throws UserInfoNotFoundException;

}