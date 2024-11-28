package mx.unam.dgtic.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.auth.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Security - UserDetailsServiceImpl.loadUserByUsername {}", username);
        mx.unam.dgtic.auth.entity.User userInfo = Optional.ofNullable(userRepository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database " + username));
        String userName = userInfo.getEmail();
        String password = userInfo.getPassword();

        List<GrantedAuthority> authorities = userInfo.getUserRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toList());

//        List<GrantedAuthority> authorities = userInfo.getUseInfoRoles().stream().map(role ->
//                new SimpleGrantedAuthority(role.getUsrRoleName())).collect(Collectors.toList());
        return new User(userName, password, authorities);
    }
}
