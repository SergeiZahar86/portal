package com.indas.portal.entities;

import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sp_contractor", schema = "dbo")
public class Contractor implements BaseEntitie<Integer> {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "contractor_id",  nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "shipper")
    private boolean shipper;

    @Column(name = "consignee")
    private boolean consignee;

    public Contractor() {
    }

    public Contractor(Integer id, String name, boolean shipper, boolean consignee) {
        this.id = id;
        setName(name);
        this.shipper = shipper;
        this.consignee = consignee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer contractorId) {
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

    public boolean isShipper() {
        return shipper;
    }

    public void setShipper(boolean shipper) {
        this.shipper = shipper;
    }

    public boolean isConsignee() {
        return consignee;
    }

    public void setConsignee(boolean consigner) {
        this.consignee = consigner;
    }
}
