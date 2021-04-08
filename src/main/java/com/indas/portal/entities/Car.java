package com.indas.portal.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_car", schema = "dbo")
@IdClass(CarId.class)
public class Car implements BaseEntitie<Integer> {

    @Id
    @Column(name = "car_id", nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", referencedColumnName = "part_id")
    @Id
    private Part partId;

    @Column(name = "num")
    private String num;

    @NotNull
    @Column(name = "att_code", nullable = false)
    private Integer attCode;

    @Column(name = "tara")
    private Float tara;

    @Column(name = "tara_e")
    private Float taraE;

    @Column(name = "zone_e")
    private Integer zoneE;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cause_id", referencedColumnName = "cause_id")
    private Cause cause;

    @Column(name = "att_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date attTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shipper", referencedColumnName = "contractor_id")
    private Contractor shipper;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "consignee", referencedColumnName = "contractor_id")
    private Contractor consignee;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "mat", referencedColumnName = "mat_id")
    private Mat mat;

    @Column(name = "left_truck")
    private Float leftTruck;

    @Column(name = "right_truck")
    private Float rightTruck;

    @Column(name = "brutto")
    private Float brutto;

    @Column(name = "weighing_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date weighingTime;

    @Column(name = "max_wheight")
    private Float maxWheight;

    public Car() {
    }

    public Car(int id, Part partId, String num, Integer attCode, Float tara, Float taraE, Integer zoneE, Cause cause, Date attTime, Contractor shipper, Contractor consignee, Mat mat, Float leftTruck, Float rightTruck, Float brutto, Date weighingTime, Float maxWheight) {
        this.id = id;
        this.partId = partId;
        this.num = num;
        this.attCode = attCode;
        this.tara = tara;
        this.taraE = taraE;
        this.zoneE = zoneE;
        this.cause = cause;
        this.attTime = attTime;
        this.shipper = shipper;
        this.consignee = consignee;
        this.mat = mat;
        this.leftTruck = leftTruck;
        this.rightTruck = rightTruck;
        this.brutto = brutto;
        this.weighingTime = weighingTime;
        this.maxWheight = maxWheight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Part getPartId() {
        return partId;
    }

    public void setPartId(Part partId) {
        this.partId = partId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Integer getAttCode() {
        return attCode;
    }

    public void setAttCode(Integer attCode) {
        this.attCode = attCode;
    }

    public Float getTara() {
        return tara;
    }

    public void setTara(Float tara) {
        this.tara = tara;
    }

    public Float getTaraE() {
        return taraE;
    }

    public void setTaraE(Float taraE) {
        this.taraE = taraE;
    }

    public Integer getZoneE() {
        return zoneE;
    }

    public void setZoneE(Integer zoneE) {
        this.zoneE = zoneE;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }

    public Date getAttTime() {
        return attTime;
    }

    public void setAttTime(Date attTime) {
        this.attTime = attTime;
    }

    public Contractor getShipper() {
        return shipper;
    }

    public void setShipper(Contractor shipper) {
        this.shipper = shipper;
    }

    public Contractor getConsignee() {
        return consignee;
    }

    public void setConsignee(Contractor consigner) {
        this.consignee = consigner;
    }

    public Mat getMat() {
        return mat;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }

    public Float getLeftTruck() {
        return leftTruck;
    }

    public void setLeftTruck(Float leftTruck) {
        this.leftTruck = leftTruck;
    }

    public Float getRightTruck() {
        return rightTruck;
    }

    public void setRightTruck(Float rightTruck) {
        this.rightTruck = rightTruck;
    }

    public Float getBrutto() {
        return brutto;
    }

    public void setBrutto(Float brutto) {
        this.brutto = brutto;
    }

    public Date getWeighingTime() {
        return weighingTime;
    }

    public void setWeighingTime(Date weighingTime) {
        this.weighingTime = weighingTime;
    }

    public Float getMaxWheight() {
        return maxWheight;
    }

    public void setMaxWheight(Float maxWheight) {
        this.maxWheight = maxWheight;
    }
}
