package mx.unam.dgtic.system.service.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.Image;
import mx.unam.dgtic.system.entity.Product;
import mx.unam.dgtic.system.repository.ImageRepository;
import mx.unam.dgtic.system.repository.ProductRepository;
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
 * @date 22/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    );

    @Value("${app.upload.dir}")
    private String UPLOAD_DIRECTORY;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        deleteCampos(products);
        return products;
    }

    @Override
    public Page<Product> searchByAllColumns(String search, Pageable pageable) {
        Page<Product> products = productRepository.searchByAllColumns(search, pageable);
        deleteCampos(products);
        return products;
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Product save(Product entity) throws Exception {
        log.info("Save Product {}", entity);
        if (entity.getId() == null || entity.getId().isBlank()) {
            entity.setId(UUID.randomUUID().toString());
            entity.setCreatedAt(Instant.now());
        }

        entity.setUpdatedAt(Instant.now());
        productRepository.save(entity);
        return entity;
    }

    @Override
    public void delete(String id) throws Exception {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {
                    throw new EntityNotFoundException("No existe Product con id: " + id);
                }
        );
    }

    @Override
    public Product getEmpty() {
        return new Product();
    }


    @Override
    public Page<Product> findAllByProveedorId(String Id, Pageable pageable) {
        return productRepository.findByProveedor_Id(Id, pageable);
    }

    @Override
    public Page<Product> searchByAllColumnsByIdProveedorId(String search, String Id, Pageable pageable) {
        return productRepository.searchByAllColumnsAndProveedorId(search, Id, pageable);
    }

    @Override
    public Boolean updateFull(Product product, MultipartFile imageFile) {
        try {
            productRepository.findById(product.getId()).ifPresent(p -> {
                log.info("Update full product. {}", product);
                p.setUpdatedAt(Instant.now());
                p.setName(product.getName());
                p.setDescription(product.getDescription());
                p.setPrecioVenta(product.getPrecioVenta());
                p.setPrecioProveedor(product.getPrecioProveedor());
                p.setExistencia(product.getExistencia());
                p.setActivo(product.getActivo());
                p.setCategory(product.getCategory());
                p.setProveedor(product.getProveedor());
                String imgToDelete = "";
                if (p.getImage() != null) {
                    imgToDelete =  p.getImage().getPathImage();
                }

                Image image = saveImage(p, imageFile);
                p.setImage(image);
                productRepository.save(p);

                if(!imgToDelete.equals(image.getPathImage())){
                    log.info("New img {}", image.getPathImage());
                    log.info("New old delete {}", imgToDelete);
                    deleteImage(UPLOAD_DIRECTORY, imgToDelete);
                }
            });
        }catch (Exception e){
            log.error("Error al actualizar el product: {}", e.getMessage());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public List<Product> getProductos() {
        return productRepository.findAll();
    }

    private void deleteCampos(Page<Product> products) {
        for (Product product : products.getContent()) {
            product.getProveedor().setCategory(null);
            product.getProveedor().setImage(null);
            product.getProveedor().setPhone(null);
            product.getProveedor().setEmail(null);
            product.getProveedor().setProducts(null);
        }
    }

    private Image saveImage(Product product, MultipartFile image){

        if (image == null || image.isEmpty()) {
            return product.getImage();
        }

        Image imageDB = new Image();

        if (product.getImage() != null) {
            imageDB = product.getImage();
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
                imageDB.setUpdatedAt(Instant.now());
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



}
