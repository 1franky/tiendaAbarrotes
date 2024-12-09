package mx.unam.dgtic.system.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.auth.model.UserInfo;
import mx.unam.dgtic.auth.model.UserInfoRole;
import mx.unam.dgtic.auth.repository.UserInfoRoleRepository;
import mx.unam.dgtic.system.entity.Roles;
import mx.unam.dgtic.system.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 03/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserInfoRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Roles> getRoles() {
        return userRepository.getRoles();
    }

    @Override
    public Optional<UserInfoRole> getRoleById(long id) {
        return roleRepository.findById(id);
    }

    @Override
    public UserInfo findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Page<UserInfo> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<UserInfo> searchByAllColumns(String search, Pageable pageable) {
        return userRepository.searchByAllColumns(search, pageable);
    }

    @Override
    public UserInfo findById(String id) {
        return null;
    }

    @Override
    public UserInfo save(UserInfo entity) throws Exception {
        if (entity.getUseId() == 0){
            entity.setUseId(null);
        }
        entity.setUsePasswd(passwordEncoder.encode(entity.getUsePasswd()));
        entity.setUseCreatedBy(1L);
        entity.setUseModifiedBy(1L);
        userRepository.save(entity);
        return entity;
    }

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    public UserInfo getEmpty() {
        return null;
    }
}