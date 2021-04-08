package com.indas.portal.media;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Configuration
public class MediaLoader {

    @Value("${media.fullPath}")
    private String rootMediaPath;

    @Value("${media.image.extension}")
    private String imageExtension;

    private static final String videoName = "video";
    private static final String videoExtension = ".mp4";

    public byte[] getPhoto(String uidPart, int idCar, int side) throws IOException {
        SidePhoto sidePhoto = null;
        try {
            sidePhoto = SidePhoto.get(side);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (sidePhoto != null){
            String innerPath = "/"+uidPart+"/"+idCar+"/"+sidePhoto.text+imageExtension;
            if (pathIsExists(rootMediaPath + innerPath)) {
                ByteBuffer byteBuffer;

                RandomAccessFile in = new RandomAccessFile(rootMediaPath + innerPath, "r");
                FileChannel fileChannel = in.getChannel();
                byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
                fileChannel.read(byteBuffer);
                byteBuffer.flip(); // ToDo флипить не обязательяно только для записи

                in.close();// ToDo Проверить
                fileChannel.close();// ToDo Проверить

                return byteBuffer.array();
            } else {
                throw new FileNotFoundException(innerPath);
            }
        }
        return new byte[0];
    }

    public boolean pathIsExists(String dir) {
        Path path = Paths.get(dir);
        return Files.exists(path);
    }

    public void loadVideo(String uidPart, HttpServletResponse response) {
        Path file = Paths.get(rootMediaPath +"/"+ uidPart +"/"+videoName+videoExtension);
        if (Files.exists(file)){
            response.setHeader("Content-disposition", "attachment;filename=" + uidPart + " " + videoName+videoExtension);
            //response.setContentType("audio/mp4");

            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
//                LOG.info("Error writing file to output stream. Filename was '{}'" + "video.mp4", e);
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }
}
