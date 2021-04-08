package com.indas.portal.security.service;

import com.indas.portal.security.dto.RoleDto;
import com.indas.portal.security.repositories.RoleRepository;
import com.indas.portal.service.Maper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Maper maper;

    public List<RoleDto> getAll(){
        return maper.mapRoles(roleRepository.findAll());
    }

}
