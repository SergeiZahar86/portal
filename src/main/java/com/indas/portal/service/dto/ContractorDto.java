package com.indas.portal.service.dto;

public class ContractorDto {

    public Integer id;
    public String name;
    public boolean shipper;
    public boolean consignee;

    public ContractorDto(String name, Integer id, boolean shipper, boolean consignee) {
        this.name = name;
        this.id = id;
        this.shipper = shipper;
        this.consignee = consignee;
    }
}
