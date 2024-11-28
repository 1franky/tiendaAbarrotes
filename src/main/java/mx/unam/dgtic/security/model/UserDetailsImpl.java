package mx.unam.dgtic.security.model;

import mx.unam.dgtic.auth.entity.User;
import mx.unam.dgtic.auth.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private String id;
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private User userInfo;

    public UserDetailsImpl(User userInfo) {
        this.userInfo = userInfo;
    }

    public UserDetailsImpl(String id, String name, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getUserRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(), user.getName(), user.getEmail(), authorities);

//        List<GrantedAuthority> authorities = user.getUseInfoRoles().stream().map(role ->
//                new SimpleGrantedAuthority(role.getUsrRoleName())
//        ).collect(Collectors.toList());
//        return new UserDetailsImpl(
//                user.getUseId(),
//                user.getFullName(),
//                user.getUseEmail(),
//                authorities
//        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (userInfo.getUserRoles() == null || userInfo.getUserRoles().isEmpty()){
            return Collections.emptyList();
        }

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        for (UserRole role : userInfo.getUserRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
        }

//        if (null == userInfo.getUseInfoRoles()) {
//            return Collections.emptySet();
//        }
//        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (UserInfoRole role : userInfo.getUseInfoRoles()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getUsrRoleName()));
//        }
        return grantedAuthorities;
    }

    /**
     * getUsername
     * @return username
     */
    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    /**
     * getPassword (OTP)
     * @return password
     */
    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    /**
     * getName
     * @return name
     */
    public String getName() {
        return userInfo.getName();
    }

    /**
     * getEmail
     * @return email
     */
    public String getEmail() {
        return userInfo.getEmail();
    }

    /**
     * isEnabled
     * @return if user is enabled
     */
    @Override
    public boolean isEnabled() {
        return userInfo.getIsActive();
    }

    /**
     * isAccountNonLocked
     * @return if user is locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * isAccountNonExpired
     * @return if account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * isCredentialsNonExpired
     * @return if credential is not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
