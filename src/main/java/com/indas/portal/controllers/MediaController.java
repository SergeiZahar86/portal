package com.indas.portal.controllers;

import com.indas.portal.media.MediaLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLClientInfoException;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaLoader mediaLoader;

    @GetMapping("/viewPhotos/{uidPart}/{idCar}")
    public ModelAndView loginPg(@PathVariable String uidPart, @PathVariable int idCar) {
        ModelAndView model = new ModelAndView("viewPhotos");
        model.addObject("uidPart", uidPart);
        model.addObject("idCar", idCar);
        return model;
    }

    @GetMapping(value = "/{uidPart}/{idCar}/{side}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getScreen(@PathVariable String uidPart, @PathVariable int idCar, @PathVariable int side) {
//            BufferedImage bi = grabScreen();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(bi, "png", baos);
//            byte[] bytes = baos.toByteArray();

//            int ran = rand.nextInt(list.size()-1);
//            System.out.println(ran);
//            return GET(list.get(ran));
        try {
            return mediaLoader.getPhoto(uidPart,idCar, side);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/video/{uidPart}")
    public void handleRequest (@PathVariable String uidPart, HttpServletResponse response) {
        mediaLoader.loadVideo(uidPart, response);
    }
}
