package com.indas.portal.security.entities;

import com.indas.portal.entities.BaseEntitie;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table( name = "sp_users", schema = "dbo")
public class User implements BaseEntitie<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer id;
    @Column(name = "login",  nullable = false)
    private String login;
    @Column(name = "password",  nullable = false)
    private String password;
    @Column(name = "fio",  nullable = false)
    private String fio;
    @Column(name = "empl_id")
    private String emplId;
    @Column(name = "expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;
    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_grants", schema = "dbo",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roles;

    public User(Integer id, String login, String password, String fio, String emplId, Date expiration, List<Role> roles) {
        this.id = id;
        setLogin(login);
        setPassword(password);
        setFio(fio);
        this.emplId = emplId;
        this.expiration = expiration;
        this.roles = roles;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null || login.isEmpty()) {
            this.login = null;
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            this.password = null;
        }
        this.password = password;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        if (fio == null || fio.isEmpty()) {
            this.fio = null;
        }
        this.fio = fio;
    }

    public String getEmplId() {
        return emplId;
    }

    public void setEmplId(String emplId) {
        this.emplId = emplId;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
