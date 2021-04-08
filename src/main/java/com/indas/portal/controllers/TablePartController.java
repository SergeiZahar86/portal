package com.indas.portal.controllers;

import com.indas.portal.export.ExelExport;
import com.indas.portal.service.PortalService;
import com.indas.portal.service.criteria.FindPartCriteria;
import com.indas.portal.service.dto.FoundPart;
import com.indas.portal.service.dto.PartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/tablePart")
public class TablePartController {

    @Autowired
    private PortalService portalService;

    @Autowired
    private ExelExport exelExport;

    @GetMapping("")
    public ModelAndView partPage() {
        ModelAndView model = new ModelAndView("tablePart");
//        model.addObject("parts", findParts(new FindPartCriteria("9340A16E-C118-4B19-A1AA-02C65AEF92E2")));
//        model.addObject("part", findPart("9340A16E-C118-4B19-A1AA-02C65AEF92E2"));
        return model;
    }

    @GetMapping("/{part}")
    public PartDto findPart(@PathVariable String part) {
        return portalService.getPart(part);
    }

    @RequestMapping(value ="/find", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FoundPart> findParts(@RequestBody FindPartCriteria criteria) {
        return portalService.getParts(criteria);
    }

    @GetMapping(value ="/exel/{part}")
    public void getPartExel(@PathVariable String part, HttpServletResponse response) {

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=part_" + currentDateTime + ".xls";
        response.setHeader(headerKey, headerValue);

        exelExport.exportPart(portalService.getPart(part),response);
//        try {
//            exelExport.writeIntoExcel(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
