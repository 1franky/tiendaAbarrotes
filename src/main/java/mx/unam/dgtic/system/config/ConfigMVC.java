package mx.unam.dgtic.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 21/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Configuration
public class ConfigMVC implements WebMvcConfigurer {

    @Value("${app.upload.dir}")
    private String UPLOAD_DIR;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String path = "file:" + UPLOAD_DIR + "/";

        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/imagenes/**")
                .addResourceLocations(path);
    }

}
