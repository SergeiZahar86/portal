package com.indas.portal.controllers;

import com.indas.portal.service.CatalogsService;
import com.indas.portal.service.dto.CauseDto;
import com.indas.portal.service.dto.ContractorDto;
import com.indas.portal.service.dto.MatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalogs")
@PreAuthorize("hasRole('ROLE_portalAdmin')")
public class CatalogsController {

    @Autowired
    private CatalogsService catalogsService;

    @RequestMapping("")
    public ModelAndView welcome() {
        ModelAndView model = new ModelAndView("catalogs");
        return model;
    }

    @RequestMapping(value = "/causes", method = RequestMethod.GET)
    public List<CauseDto> getAllCause(){
        return catalogsService.getAllCause();
    }

    @RequestMapping(value = "/contractors", method = RequestMethod.GET)
    public List<ContractorDto> getAllContractor(){
        return catalogsService.getAllContractor();
    }

    @RequestMapping(value = "/mats", method = RequestMethod.GET)
    public List<MatDto> getAllMat(){
        return catalogsService.getAllMat();
    }

    @RequestMapping(value = "/save/mat", method = RequestMethod.PUT)
    public MatDto save( @RequestBody MatDto mat){
        return catalogsService.save(mat);
    }

    @RequestMapping(value = "/save/cause", method = RequestMethod.PUT)
    public CauseDto save( @RequestBody CauseDto cause){
        return catalogsService.save(cause);
    }

    @RequestMapping(value = "/save/contractor", method = RequestMethod.PUT)
    public ContractorDto save( @RequestBody ContractorDto contractor){
        return catalogsService.save(contractor);
    }
}
