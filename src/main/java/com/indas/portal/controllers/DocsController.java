package com.indas.portal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DocsController {
//    @Autowired
//    DocService docService;

//    @GetMapping("/")
//    public RedirectView redirectWithUsingRedirectView(
//            RedirectAttributes attributes) {
//        return new RedirectView("mnemoscheme");
//    }

    @GetMapping("/documents")
//    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PORTAL_ALL') or hasRole('ROLE_PORTAL_READ')")
    public ModelAndView getMainPage(){
        ModelAndView model = new ModelAndView("docspage");
        Map<String, String> docList = new HashMap<>();

        try{
            docList = Collections.emptyMap();
        }catch(Exception e0){
            e0.printStackTrace();
        }

        model.addObject("docs", docList);
        return model;
    }

//    @PostMapping("/documents/getparams")
//    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PORTAL_ALL') or hasRole('ROLE_PORTAL_READ')")
//    public String getHtmlParams(@RequestParam String bean) throws Exception {
//        String ret = docService.getHTMLParam(bean);
//
//        return ret;
//    }
//
//    @PostMapping("/documents/getpdf")
//    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PORTAL_ALL') or hasRole('ROLE_PORTAL_READ')")
//    public byte[] getPdfDoc(@RequestParam String docName, @RequestParam String docargs) throws Exception {
//        ObjectMapper om = new ObjectMapper();
//        Map<String, String> params = om.readValue(docargs, Map.class);
//
//        byte[] doc = docService.getPDFDoc(docName, params);
//        return doc;
//    }
//
//    @RequestMapping("/documents/getexcel")
//    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_PORTAL_ALL') or hasRole('ROLE_PORTAL_READ')")
//    public @ResponseBody
//    HttpEntity<byte[]> getExcel(@RequestParam String docName, @RequestParam String docargs) throws Exception {
//        ObjectMapper om = new ObjectMapper();
//        Map<String, String> params = om.readValue(docargs, Map.class);
//
//        byte[] doc = docService.getExcelDoc(docName, params);
//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
//        header.set("Content-Disposition", "attachment; filename=\""+docName+".xls\"");
//        header.setContentLength(doc.length);
//
//        return new HttpEntity<byte[]>(doc, header);
//    }



}
