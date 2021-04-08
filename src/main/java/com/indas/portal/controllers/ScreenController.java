package com.indas.portal.controllers;

import org.apache.commons.compress.utils.IOUtils;
import org.aspectj.util.LangUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;


@RestController
@RequestMapping("/screen")
public class ScreenController {

    @Value("${screen}")
    private String urlScreen;

    @GetMapping("")
    public ModelAndView loginPg() {

        ModelAndView model = new ModelAndView("screen");
        return model;
    }

    private Random rand = new Random();

    @GetMapping(value = "/1", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getScreen() throws IOException {
        try {
//            BufferedImage bi = grabScreen();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(bi, "png", baos);
//            byte[] bytes = baos.toByteArray();
//
//            int ran = rand.nextInt(list.size()-1);
//            System.out.println(ran);
//            return GET(list.get(ran));
            return GET(urlScreen);
        } catch (Exception e) {
            InputStream is = getClass().getResourceAsStream("/static/images/tmp.jpg");
            byte[] bytes = IOUtils.toByteArray(is);
            return bytes;
        }
        //return null;
    }

    public byte[] GET(String urlStr) throws IOException {

        URL url = new URL(urlStr);

        HttpURLConnection httpCon = null;
        boolean seccess = false;
        try {
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(false);
            httpCon.setRequestMethod("GET");
            seccess = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        if (seccess && httpCon != null) {
            int code = httpCon.getResponseCode();  // response code
            if (code == 200) {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try (InputStream inputStream = httpCon.getInputStream()) {
                    int n = 0;
                    byte[] buffer = new byte[1024];
                    while (-1 != (n = inputStream.read(buffer))) {
                        output.write(buffer, 0, n);
                    }
                }
                httpCon.disconnect();
                return output.toByteArray();
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getErrorStream(), "UTF-8"));
                StringBuilder strCurrentLine = new StringBuilder();
                strCurrentLine.append("Error get(" + urlStr + ")");
                String str;
                while ((str = br.readLine()) != null) {
                    strCurrentLine.append(str);
//                    System.out.println("Error get("+ urlStr +") = " + strCurrentLine);
                }
                IOException e = new IOException(strCurrentLine.toString());
                httpCon.disconnect();
                throw e;
            }
        }
        return null;
    }

    // Получение Изображения
//    private void testSetUrlImage() throws Exception{
//        URL url = new URL("https://memepedia.ru/wp-content/uploads/2019/07/chilipizdrik-1.png");
//
//        InputStream in = new BufferedInputStream(url.openStream());
//        OutputStream out = new BufferedOutputStream(new FileOutputStream("ImageInURl.jpg"));
//        for ( int i; (i = in.read()) != -1; ) {
//            out.write(i);
//        }
//        in.close();
//        out.close();
//    }
}
