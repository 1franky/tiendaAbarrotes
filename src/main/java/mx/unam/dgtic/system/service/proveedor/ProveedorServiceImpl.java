package mx.unam.dgtic.system.service.proveedor;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.*;
import mx.unam.dgtic.system.repository.EmailRepository;
import mx.unam.dgtic.system.repository.ImageRepository;
import mx.unam.dgtic.system.repository.PhoneRepository;
import mx.unam.dgtic.system.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static mx.unam.dgtic.system.utils.Utils.deleteImage;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Log4j2
@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    private final ImageRepository imageRepository;

    private final PhoneRepository phoneRepository;

    private final EmailRepository emailRepository;

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    );

    @Value("${app.upload.dir}")
    private String UPLOAD_DIRECTORY;

    @Override
    @Transactional(readOnly = true)
    public Page<Proveedor> findAll(Pageable pageable) {
        Page<Proveedor> proveedors = proveedorRepository.findAll(pageable);
        deleteCampos(proveedors);
        return proveedors;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Proveedor> searchByAllColumns(String search, Pageable pageable) {
        Page<Proveedor> proveedors = proveedorRepository.searchByAllColumns(search, pageable);
        deleteCampos(proveedors);
        return proveedors;
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor findById(String id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Proveedor save(Proveedor proveedor) throws Exception {
        if (proveedor.getId() == null || proveedor.getId().isBlank()) {
            proveedor.setId(UUID.randomUUID().toString());
            proveedor.setCreatedAt(Instant.now());
        }

        proveedor.setUpdatedAt(Instant.now());
        proveedorRepository.save(proveedor);
        return proveedor;
    }

    @Override
    @Transactional
    public void delete(String id) throws Exception {
        try{
            proveedorRepository.findById(id).ifPresentOrElse(proveedor -> {
                String imgToDelete = null;
                if (proveedor.getImage() != null) {
                    imgToDelete = proveedor.getImage().getPathImage();
                    imageRepository.delete(proveedor.getImage());
                }
                if (proveedor.getPhone() != null) {
                    phoneRepository.delete(proveedor.getPhone());
                }
                if (proveedor.getEmail() != null) {
                    emailRepository.delete(proveedor.getEmail());
                }
                proveedorRepository.delete(proveedor);
                deleteImage(UPLOAD_DIRECTORY, imgToDelete);
            }, () -> {
                throw new EntityNotFoundException("No existe Proveedor con id: " + id);
            });
        } catch (Exception e){
            log.error("Error al eliminar el proveedor: {}", e.getMessage());
            throw new Exception("Error al eliminar el proveedor ");
        }
    }

    @Override
    public Proveedor getEmpty() {
        Proveedor proveedor = Proveedor.builder()
                .category(new Category())
                .build();
        log.info("Proveedor {}", proveedor);
        return proveedor;
    }

    @Override
    public List<Proveedor> getProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    @Transactional()
    public Boolean updateFull(Proveedor proveedor, MultipartFile imageFile) {
        try {
            Proveedor proveedorDB = proveedorRepository.findById(proveedor.getId()).orElse(null);
            if (proveedorDB == null) {
                return Boolean.FALSE;
            }

            proveedorDB.setUpdatedAt(Instant.now());
            proveedorDB.setName(proveedor.getName());
            proveedorDB.setAddress(proveedor.getAddress());
            proveedorDB.setActivo(proveedor.getActivo());
            proveedorDB.setCategory(proveedor.getCategory());

            String imgToDelete = "";
            if (proveedorDB.getImage() != null) {
                Image imgTpm = proveedorDB.getImage();
                imgToDelete = imgTpm.getPathImage();
            }

            Image image = saveImage(proveedorDB, imageFile);
            proveedorDB.setImage(image);

            Phone phone = getPhone(proveedor, proveedorDB);
            phoneRepository.save(phone);
            proveedorDB.setPhone(phone);

            Email email = getEmail(proveedor, proveedorDB);
            emailRepository.save(email);
            proveedorDB.setEmail(email);

            proveedorRepository.save(proveedorDB);

            if (!imgToDelete.equals(image.getPathImage())){
                log.info("New img {}", image.getPathImage());
                log.info("New old delete {}", imgToDelete);
                deleteImage(UPLOAD_DIRECTORY, imgToDelete);
            }

        }catch (Exception e){
            log.error("Error al actualizar el proveedor: {}", e.getMessage());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private static Email getEmail(Proveedor proveedor, Proveedor proveedorDB) {
        Email email = new Email();
        if (proveedorDB.getEmail() != null) {
            email = proveedorDB.getEmail();
        }else {
            email.setId(UUID.randomUUID().toString());
            email.setCreatedAt(Instant.now());
        }
        email.setUpdatedAt(Instant.now());

        if (proveedor.getEmail() != null && proveedor.getEmail().getEmail() != null) {
            email.setEmail(proveedor.getEmail().getEmail());
        }
        if (proveedor.getEmail() != null && proveedor.getEmail().getDescription() != null) {
            email.setDescription(proveedor.getEmail().getDescription());
        }
        return email;
    }

    private static Phone getPhone(Proveedor proveedor, Proveedor proveedorDB) {
        Phone phone = new Phone();
        if (proveedorDB.getPhone() != null) {
            phone = proveedorDB.getPhone();
        }else {
            phone.setId(UUID.randomUUID().toString());
            phone.setCreatedAt(Instant.now());
        }
        phone.setUpdatedAt(Instant.now());

        if (proveedor.getPhone() != null && proveedor.getPhone().getPhone() != null) {
            phone.setPhone(proveedor.getPhone().getPhone());
        }
        if (proveedor.getPhone() != null && proveedor.getPhone().getDescription() != null) {
            phone.setDescription(proveedor.getPhone().getDescription());
        }
        return phone;
    }

    private Image saveImage(Proveedor proveedor, MultipartFile image){

        if (image == null || image.isEmpty()) {
            return proveedor.getImage();
        }

        Image imageDB = new Image();

        if (proveedor.getImage() != null) {
            imageDB = proveedor.getImage();
        }

        String contentType = image.getContentType();
        if (!ALLOWED_MIME_TYPES.contains(contentType)) {
            log.error("Error en tipo de archivo no soportado. {}", contentType);
            return imageDB;
        }

        String fileName = (UUID.randomUUID() + "_" + image.getOriginalFilename()).replaceAll(" ", "_");
        Path fullPath = Paths.get(UPLOAD_DIRECTORY,"images", fileName);

        try {
            if (imageDB.getId() == null) {
                imageDB.setId(UUID.randomUUID().toString());
                imageDB.setCreatedAt(Instant.now());
            }
            imageDB.setUpdatedAt(Instant.now());
            Files.write(fullPath, image.getBytes());
            imageDB.setPathImage("images/" + fileName);
            log.info("Archivo guardado en {}", fullPath);
            imageRepository.save(imageDB);
        } catch (IOException e) {
            log.error("Error al guardar el archivo: {}", e.getMessage());
            return imageDB;
        }
        return imageDB;
    }

//    public void deleteImage(String imageName)  {
//        Path imagePath = Paths.get(UPLOAD_DIRECTORY, imageName);
//        log.info("esta si se elimina: {}", imagePath);
//        try{
//            if (Files.exists(imagePath)) {
//                Files.delete(imagePath); // Elimina el archivo
//                log.info("Archivo eliminado en {}", imagePath);
//            } else {
//                log.warn("No existe la imagen: {}", imageName);
//            }
//        }catch (IOException e){
//            log.error("Error al eliminar el archivo: {} erro -> {}", imageName, e.getMessage());
//        }
//    }

    private void deleteCampos(Page<Proveedor> proveedors) {
        for (Proveedor proveedor : proveedors.getContent()) {
            proveedor.setImage(null);
        }
    }

}
