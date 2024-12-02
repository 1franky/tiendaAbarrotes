package mx.unam.dgtic.system.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 02/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Log4j2
public class Utils {

    public static void deleteImage(String path, String imageName)  {
        Path imagePath = Paths.get(path, imageName);
        try{
            if (Files.exists(imagePath)) {
                Files.delete(imagePath); // Elimina el archivo
                log.info("Archivo eliminado en {}", imagePath);
            } else {
                log.warn("No existe la imagen: {}", imageName);
            }
        }catch (IOException e){
            log.error("Error al eliminar el archivo: {} error -> {}", imageName, e.getMessage());
        }
    }

}