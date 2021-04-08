package com.indas.portal.security.entities;

import com.indas.portal.entities.BaseEntitie;

import javax.persistence.*;

@Entity
@Table(name = "sp_role" , schema = "dbo")
public class Role implements BaseEntitie<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role",  nullable = false)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public Role(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            name = null;
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
