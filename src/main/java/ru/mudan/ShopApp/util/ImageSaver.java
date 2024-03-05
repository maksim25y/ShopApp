package ru.mudan.ShopApp.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Component
public class ImageSaver {
    @Value("${images.path}")
    private String folderPath;
    public boolean saveImage(MultipartFile file,String fileName){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(folderPath + fileName);
                System.out.println(path);
                Files.write(path, bytes);
                System.out.println("Изображение успешно сохранено: " + path);
            } catch (Exception e) {
                return false;
            }
        }else {
            return false;
        }
        return true;
    }
}
