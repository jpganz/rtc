package com.rotaract.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class UserGrantedRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private boolean isUser;
    private boolean isAdmin;
    private boolean isClubAdmin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isClubAdmin() {
        return isClubAdmin;
    }

    public void setClubAdmin(boolean clubAdmin) {
        isClubAdmin = clubAdmin;
    }
}
