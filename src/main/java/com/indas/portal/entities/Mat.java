package com.indas.portal.entities;


import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


import javax.persistence.*;


@Entity
@Table(name = "sp_mat", schema = "dbo")
public class Mat implements BaseEntitie<Integer> {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "mat_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotNull
    private String name;

    public Mat() {
    }

    public Mat(Integer id, String name) {
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
