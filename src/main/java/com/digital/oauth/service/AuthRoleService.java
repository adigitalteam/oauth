package com.digital.oauth.service;

import com.digital.oauth.entity.AuthRole;
import com.digital.oauth.entity.AuthUser;
import com.digital.oauth.entity.UserRole;
import com.digital.oauth.exceptions.AppException;
import com.digital.oauth.repository.AuthRoleRepository;
import com.digital.oauth.repository.UserRepository;
import com.digital.oauth.repository.UserRoleRepository;
import com.digital.oauth.specification.authRole.AuthRoleCriteriaRepository;
import com.digital.oauth.specification.authRole.AuthRolePage;
import com.digital.oauth.specification.authRole.AuthRoleSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthRoleService {
    @Autowired
    AuthRoleRepository authRoleRepository;

    @Autowired
    AuthRoleCriteriaRepository authRoleCriteriaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;


    public Page<AuthRole> getList(AuthRolePage authRolePage,
                                  AuthRoleSearchCriteria authRoleSearchCriteria) {
        return authRoleCriteriaRepository.findAllWithFilters(authRolePage, authRoleSearchCriteria);
    }

    public UserRole grant(Long user_id, String roleName) throws AppException {

        Optional<AuthRole> authRoleOptional = authRoleRepository.findAuthRoleByRoleName(roleName);
        if (authRoleOptional.isEmpty()) {
            throw new AppException("Auth Role not found");
        }

        AuthRole authRole = authRoleOptional.get();

        Optional<AuthUser> authUserOptional = userRepository.findById(user_id);
        if (authUserOptional.isEmpty()) {
            throw new AppException("User not found");
        }

        AuthUser user = authUserOptional.get();

        Optional<UserRole> userRoleOptional = userRoleRepository.findUserRoleByUserIdAndAndRoleId(user.getId(), authRole.getId());

        if (userRoleOptional.isPresent()) {
            throw new AppException("User is granted early");
        }

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(authRole.getId());

        return userRoleRepository.save(userRole);
    }

    public UserRole revoke(Long user_id, String roleName) throws AppException {

        Optional<AuthRole> authRoleOptional = authRoleRepository.findAuthRoleByRoleName(roleName);
        if (authRoleOptional.isEmpty()) {
            throw new AppException("Auth Permission not found");
        }

        AuthRole authRole = authRoleOptional.get();

        Optional<AuthUser> authUserOptional = userRepository.findById(user_id);
        if (authUserOptional.isEmpty()) {
            throw new AppException("User not found");
        }

        AuthUser user = authUserOptional.get();

        Optional<UserRole> userRoleOptional = userRoleRepository.findUserRoleByUserIdAndAndRoleId(user.getId(), authRole.getId());

        if (userRoleOptional.isEmpty()) {
            throw new AppException("User is revoke early");
        }

        UserRole userRole = userRoleOptional.get();
        userRoleRepository.delete(userRole);
        return userRole;
    }

}
