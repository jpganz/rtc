package com.rotaract.project.service.impl;

import com.rotaract.project.domain.Club_admin;
import com.rotaract.project.security.AuthoritiesConstants;
import com.rotaract.project.service.UsersPermissions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by user on 7/10/2018.
 */
@Service
public class UserPermissionsImpl implements UsersPermissions {


    @Override
    public Optional<Club_admin> getUserClubAdminCredentials(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String name = auth.getName(); // current user
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
        if(isClubAdmin(authorities)){

        }else {

        }
        authorities.stream().filter(o -> o.getAuthority().equals(AuthoritiesConstants.CLUB_ADMIN)).findFirst().isPresent();
    return Optional.empty();
    }

    private boolean isClubAdmin(Collection<SimpleGrantedAuthority> authorities){
        return authorities.stream().filter(o -> o.getAuthority().equals(AuthoritiesConstants.CLUB_ADMIN)).findFirst().isPresent();
    }

    @Override
    public boolean isSuperAdmin(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            final String name = auth.getName(); // current user
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
            return authorities.stream().filter(o -> o.getAuthority().equals(AuthoritiesConstants.ADMIN)).findFirst().isPresent();
        }catch(Exception e){
            return false;
        }
    }
    /*
    *jhiHasAnyAuthority="'ROLE_ADMIN'"
     */
}
