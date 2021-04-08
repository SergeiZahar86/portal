package com.indas.portal.box;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class GateWay {

    public static String one_request = "list";
    public static String USER__AGENT = "pup";

//    public void defaultRe() {
//        JSONObject getResult = new JSONObject(sendGet(URL_SER + one_request));
//
//        for (Map.Entry<String, Object> entry : getResult.toMap().entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//            String jsonString = new JSONObject()
//                    //.put("type", "Excel")
//                    .put("type", type)
//                    .toString();
//
//            //saveExcel(sendPost(URL_SER + entry.getKey(), jsonString));
//            save(sendPost(URL_SER + entry.getKey(), jsonString),fileName,eXTENSION);
//        }
//    }

    public void save(byte[] bytes, String fileName, String type) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName+type, "rw");
        randomAccessFile.write(bytes);
        randomAccessFile.close();

    }

    //HTTP GET request
    public String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER__AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    //HTTP POST request
    public String sendPostJson(String url, String json) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER__AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //Send post request
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes());
            os.flush();
            os.close();
        }
        int responseCode = con.getResponseCode();
        StringBuffer response = new StringBuffer();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            throw new Exception("ErrorCode:" + responseCode + "/" + con.getResponseMessage() + ", Причина:" + response.toString());
        }
    }

    //HTTP POST request
    public byte[] sendPost(String url, String json) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER__AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //Send post request
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes());
            os.flush();
            os.close();
        }
        int responseCode = con.getResponseCode();
        StringBuffer response = new StringBuffer();
        if (responseCode == 200) {
            InputStream stream =con.getInputStream();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            byte[] byteArray = buffer.toByteArray();
            return byteArray;
        } else {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            throw new Exception("ErrorCode:" + responseCode + "/" + con.getResponseMessage() + ", Причина:" + response.toString());
        }
    }

}
