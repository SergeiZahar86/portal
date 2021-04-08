package com.indas.portal.rest;

import com.indas.portal.service.PortalService;
import com.indas.portal.service.dto.PartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/portal/api")
public class PortalRest {

    @Autowired
    private PortalService portalService;

//    @RequestMapping(value = "/part/{id}", method = GET)
//    private PartDto getPart(@PathVariable("id") String guid){
//        return portalService.getPart(guid);
//    }
//
//    @RequestMapping(value = "/parts", method = GET)
//    private List<String> getPart(){
//        return portalService.findAllPartIds();
//    }

// https://tproger.ru/translations/how-to-read-write-excel-file-java-poi-example/

}
