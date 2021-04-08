package com.indas.portal.service.dto;

import com.indas.portal.entities.Cause;
import com.indas.portal.entities.Contractor;
import com.indas.portal.entities.Mat;

public class CarDto {
    public int id;
    public String partId;
    public String num;
    public String attCode;
    public Float tara;
    public Float taraE;
    public Integer zoneE;
    public CauseDto cause;
    public Float maxWheight;
    public long attTimeUts;
    public ContractorDto shipper;
    public ContractorDto consignee;
    public MatDto mat;
    public Float leftTruck;
    public Float rightTruck;
    public Float brutto;
    public long weighingTimeUts;
}
