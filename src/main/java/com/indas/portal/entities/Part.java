package com.indas.portal.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_part", schema = "dbo")
public class Part implements BaseEntitie<String> {

    @Id
    @Column(name = "part_id", nullable = false)
    private String id;

    @NotNull
    @Column(name = "oper", nullable = false)
    private String oper;

    @OneToMany(mappedBy = "partId", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Car> cars;

    @NotNull
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    public Part() {
    }

    public Part(String id, String oper, Date startTime, Date endTime) {
        this.id = id;
        this.oper = oper;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getStartTimeString(SimpleDateFormat dateFormat) {
        return dateFormat.format(startTime);
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeString(SimpleDateFormat dateFormat) {
        return dateFormat.format(endTime);
    }
}
