package com.indas.portal.service.dto;

import com.indas.portal.entities.Part;

import java.util.Date;

public class FoundPart {

    private String id;
    private String oper;
    private long startTimeUts;
    private long endTimeUts;

    public FoundPart() {
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

    public long getStartTimeUts() {
        return startTimeUts;
    }

    public void setStartTimeUts(long startTimeUts) {
        this.startTimeUts = startTimeUts;
    }

    public long getEndTimeUts() {
        return endTimeUts;
    }

    public void setEndTimeUts(long endTimeUts) {
        this.endTimeUts = endTimeUts;
    }
}
