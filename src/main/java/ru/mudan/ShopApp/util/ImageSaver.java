package ru.mudan.ShopApp.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageSaver {
    public void saveImageToFolder(String imageUrl, String folderPath) {
        try {
            // Чтение изображения из URL
            URL url = new URL(imageUrl);
            InputStream in = new BufferedInputStream(url.openStream());


            // Сохранение изображения в указанную папку
            Path path = Paths.get(folderPath, imageUrl);
            Files.copy(in, path);

            in.close();

            System.out.println("Изображение успешно сохранено в папку: " + folderPath);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении изображения: " + e.getMessage());
        }
    }
}
