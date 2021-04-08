package com.indas.portal.service.criteria;

import java.util.Date;

public class FindPartCriteria {

    public String id;
    public String start;
    public String end;
    public String oper;

    public FindPartCriteria(String id) {
        this.id = id;
    }

    public FindPartCriteria() {
    }

    public String getId() {
        return id;
    }

    public Date getStart() {
        if (start == null) {
            return null;
        }
        return new Date(Long.parseLong(start));
    }

    public Date getEnd() {
        if (end == null) {
            return null;
        }
        return new Date(Long.parseLong(end));
    }

    public String getOper() {
        return oper;
    }
}
