package mx.unam.dgtic.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.auth.entity.User;
import mx.unam.dgtic.auth.exception.UserInfoNotFoundException;
import mx.unam.dgtic.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 28/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) throws UserInfoNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UserInfoNotFoundException(email);
        }
        return user;
    }

}