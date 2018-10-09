package com.rotaract.project.service;

import com.rotaract.project.domain.Club_admin;

import java.util.Optional;

/**
 * Created by user on 7/10/2018.
 */
public interface UsersPermissions {

    Optional<Club_admin> getUserClubAdminCredentials();
}
