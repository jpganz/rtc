package com.rotaract.project.service;

import com.rotaract.project.domain.Club_admin;
import com.rotaract.project.domain.UserGrantedRole;

import java.util.Optional;

/**
 * Created by user on 7/10/2018.
 */
public interface UsersPermissions {

    Optional<Club_admin> getUserClubAdminCredentials();

    boolean isSuperAdmin();

    boolean isClubAdmin();

    UserGrantedRole getUserRoles();
}
