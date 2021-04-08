package com.indas.portal.service;

import com.indas.portal.entities.*;
import com.indas.portal.security.dto.RoleDto;
import com.indas.portal.security.dto.UserDto;
import com.indas.portal.security.entities.Role;
import com.indas.portal.security.entities.User;
import com.indas.portal.service.dto.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Maper {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public CarDto mapCar(Car car){
        CarDto dto = new CarDto();
        dto.id = car.getId();
        dto.partId = car.getPartId().getId();
        dto.num = car.getNum();
        dto.attCode = mapAttCode(car.getAttCode());
        dto.tara = car.getTara();
        dto.taraE = car.getTaraE();
        dto.zoneE = car.getZoneE();
        dto.cause = map(car.getCause());
        dto.maxWheight = car.getMaxWheight();
        dto.attTimeUts = parsDate(car.getAttTime());
        dto.shipper = map(car.getShipper());
        dto.consignee = map(car.getConsignee());
        dto.mat = map(car.getMat());

        dto.leftTruck = car.getLeftTruck();
        dto.rightTruck = car.getRightTruck();
        dto.brutto = car.getBrutto();
        dto.weighingTimeUts = parsDate(car.getWeighingTime());

        return dto;
    }

    public String mapAttCode(Integer attCode){
        switch (attCode){
            case 0: return "Аттестован";
            case 1: return "Не аттестован";
            case 2: return "Условно аттестован";
            default: return String.valueOf(attCode);
        }
    }

    public CauseDto map(Cause cause){
        if (cause == null) {
            return null ;
        }
        return new CauseDto(cause.getName(), cause.getId());
    }

    public ContractorDto map(Contractor contractor){
        if (contractor == null) {
            return null ;
        }
        return new ContractorDto(contractor.getName(), contractor.getId(), contractor.isShipper(), contractor.isConsignee());
    }

    public MatDto map(Mat mat){
        if (mat == null) {
            return null ;
        }
        return new MatDto(mat.getName(), mat.getId());
    }

    public Cause map(CauseDto cause){
        if (cause == null) {
            return null ;
        }
        return new Cause(cause.id, cause.name);
    }

    public Mat map(MatDto mat){
        if (mat == null) {
            return null ;
        }
        return new Mat(mat.id, mat.name);
    }

    public Contractor map(ContractorDto contractor){
        if (contractor == null) {
            return null ;
        }
        return new Contractor(contractor.id, contractor.name, contractor.shipper, contractor.consignee);
    }


    public PartDto map(Part part){
        PartDto dto = new PartDto();
        dto.id = part.getId();
        dto.endTimeUts = parsDate(part.getEndTime());
        dto.startTimeUts = parsDate(part.getStartTime());
        dto.oper = part.getOper();
        dto.cars = part.getCars().stream().map(this::mapCar).collect(Collectors.toList());
        return dto;
    }

    public List<FoundPart> mapParts(List<Part> parts) {
        return parts.stream().map(this::mapPartToFoundPart).collect(Collectors.toList());
    }

    public FoundPart mapPartToFoundPart(Part part) {
        FoundPart foundPart = new FoundPart();
        foundPart.setId(part.getId());
        foundPart.setOper(part.getOper());
        foundPart.setStartTimeUts(parsDate(part.getStartTime()));
        foundPart.setEndTimeUts(parsDate(part.getEndTime()));
        return foundPart;
    }

    private long parsDate(Date date){
        long strUts = 0;
        if (date != null) {
            strUts = date.getTime();
        }
        return strUts;
    }

    public List<UserDto> mapUsers(List<User> all) {
        return all.stream().map(this::map).collect(Collectors.toList());
    }

    public UserDto map(User user) {
        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.fio = user.getFio();
        userDto.login = user.getLogin();
        userDto.expiration = parsDate(user.getExpiration());
        userDto.emplId = user.getEmplId();
        userDto.roles = user.getRoles() != null ?
                user.getRoles().stream()
                        .map(this::map)
                        .collect(Collectors.toList()) : null;
        return userDto;
    }

    public RoleDto map(Role role){
        RoleDto dto = new RoleDto();
        dto.id = role.getId();
        dto.name = role.getName();
        dto.description = role.getDescription();
        return dto;
    }

    public List<RoleDto> mapRoles(List<Role> all) {
        return all.stream().map(this::map).collect(Collectors.toList());
    }

    public Role map(RoleDto dto){
        Role role = new Role();
        role.setId(dto.id);
        role.setName(dto.name);
        role.setDescription(dto.description);
        return role;
    }

    public List<Role> mapDto(List<RoleDto> all) {
        return all.stream().map(this::map).collect(Collectors.toList());
    }

    public User map(UserDto userDto) {
        return new User(
                userDto.id,
                userDto.login,
                userDto.password,
                userDto.fio,
                userDto.emplId,
                userDto.expiration != null ? new Date(userDto.expiration) : null,
                userDto.roles != null ? mapDto(userDto.roles) : null
        );
    }
}
