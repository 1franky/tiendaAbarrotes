package mx.unam.dgtic.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 28/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserInfoNotFoundException extends Exception {

    public UserInfoNotFoundException(Long id) {
        super("User Info with id " + id + " is NOT found");
    }

    public UserInfoNotFoundException(String email) {
        super("User Email already exists! " + email);
    }

}