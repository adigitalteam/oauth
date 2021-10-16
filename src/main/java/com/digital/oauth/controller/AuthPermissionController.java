package com.digital.oauth.controller;


import com.digital.oauth.dto.AuthPermissionCreateRequestDTO;
import com.digital.oauth.dto.AuthPermissionUpdateRequestDTO;
import com.digital.oauth.entity.AuthPermission;
import com.digital.oauth.entity.UserPermission;
import com.digital.oauth.exceptions.AppException;
import com.digital.oauth.repository.AuthPermissionRepository;
import com.digital.oauth.service.AuthPermissionService;
import com.digital.oauth.specification.authPermission.AuthPermissionPage;
import com.digital.oauth.specification.authPermission.AuthPermissionSearchCriteria;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/auth-permission")
public class AuthPermissionController {

    @Autowired
    AuthPermissionRepository authPermissionRepository;

    @Autowired
    AuthPermissionService authPermissionService;

    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthPermission create(@Valid @RequestBody AuthPermissionCreateRequestDTO createRequestDTO) {
        AuthPermission authPermission = new AuthPermission();
        authPermission.setPermissionName(createRequestDTO.getPermissionName());
        authPermission.setDescription(createRequestDTO.getDescription());
        return authPermissionRepository.save(authPermission);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthPermission update(@PathVariable Long id, @Valid @RequestBody AuthPermissionUpdateRequestDTO updateRequestDTO) throws AppException {

        Optional<AuthPermission> optionalAuthPermission = authPermissionRepository.findById(id);
        if (optionalAuthPermission.isEmpty()) {
            throw new AppException("Permission not found");
        }
        AuthPermission authPermission = optionalAuthPermission.get();
        authPermission.setDescription(updateRequestDTO.getDescription());
        return authPermissionRepository.save(authPermission);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Page<AuthPermission> list(AuthPermissionPage authPermissionPage,
                                     AuthPermissionSearchCriteria authPermissionSearchCriteria) {
        return authPermissionService.getList(authPermissionPage, authPermissionSearchCriteria);
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthPermission delete(@PathVariable Long id) throws AppException {
        Optional<AuthPermission> optionalAuthPermission = authPermissionRepository.findById(id);
        if (optionalAuthPermission.isEmpty()) {
            throw new AppException("Permission not found");
        }
        AuthPermission authPermission = optionalAuthPermission.get();
        authPermissionRepository.delete(authPermission);
        return authPermission;
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @PostMapping("/{user}/{name}")
    @ResponseStatus(HttpStatus.OK)
    public UserPermission grant(@PathVariable Long user, @PathVariable String name) throws AppException {
        return authPermissionService.grant(user,name);
    }


    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @DeleteMapping("/{user}/{name}")
    @ResponseStatus(HttpStatus.OK)
    public UserPermission revoke(@PathVariable Long user, @PathVariable String name) throws AppException {
        return authPermissionService.revoke(user,name);
    }


}
