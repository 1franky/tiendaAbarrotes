package mx.unam.dgtic.security.service;

import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.auth.model.UserInfo;
import mx.unam.dgtic.auth.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserDetailsServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Security - UserDetailsServiceImpl.loadUserByUsername {}", username);
        UserInfo userInfo = Optional.ofNullable(userInfoRepository.findByUseEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database"));
        String userName = userInfo.getUseEmail();
        String password = userInfo.getUsePasswd();
        List<GrantedAuthority> authorities = userInfo.getUseInfoRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getUsrRoleName())).collect(Collectors.toList());
        return new User(userName, password, authorities);
    }
}
