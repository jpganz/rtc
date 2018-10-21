package com.rotaract.project.service.impl;

import com.rotaract.project.domain.Club_admin;
import com.rotaract.project.domain.UserGrantedRole;
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
            //return authorities.stream().filter(o -> o.getAuthority().equals(AuthoritiesConstants.ADMIN)).findFirst().isPresent();
            for(SimpleGrantedAuthority authority:authorities){
                if(authority.getAuthority().equals("ADMIN")){
                    return true;
                }
            }
        }catch(Exception e){
            return false;
        }
        return false;
    }

    @Override
    public boolean isClubAdmin(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            final String name = auth.getName(); // current user
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
            //return authorities.stream().filter(o -> o.getAuthority().equals(AuthoritiesConstants.CLUB_ADMIN)).findFirst().isPresent();
            for(SimpleGrantedAuthority authority:authorities){
                if(authority.getAuthority().equals("CLUB_ADMIN")){
                    return true;
                }
            }
        }catch(Exception e){
            return false;
        }
        return false;
    }

    @Override
    public UserGrantedRole getUserRoles(){
        final UserGrantedRole grantedRole = new UserGrantedRole();
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            final String name = auth.getName(); // current user
            grantedRole.setUserName(name);
            Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
            //return authorities.stream().filter(o -> o.getAuthority().equals(AuthoritiesConstants.CLUB_ADMIN)).findFirst().isPresent();
            for(SimpleGrantedAuthority authority:authorities){
                if(authority.getAuthority().equals("CLUB_ADMIN")){
                    grantedRole.setClubAdmin(true);
                }else if(authority.getAuthority().equals("ROLE_ADMIN")){
                    grantedRole.setAdmin(true);
                }else if(authority.getAuthority().equals("ROLE_USER")){
                    grantedRole.setUser(true);
                }
            }
            return grantedRole;
        }catch(Exception e){

        }
        return null;
    }

    /*
    *jhiHasAnyAuthority="'ROLE_ADMIN'"
     */
}
