package com.indas.portal.entities;

import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sp_cause", schema = "dbo")
public class Cause implements BaseEntitie<Integer> {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "cause_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    public Cause() {
    }

    public Cause(Integer id, String name) {
        this.id = id;
        setName(name);
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
}
