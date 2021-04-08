package com.indas.portal.security.spring.override;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class User extends org.springframework.security.core.userdetails.User {

    private String fio;

    public User(String fio, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.fio = fio;
    }

    public User(String fio, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
