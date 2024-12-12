package mx.unam.dgtic.system.service.product;

import mx.unam.dgtic.system.entity.Product;
import mx.unam.dgtic.system.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 22/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

public interface ProductService extends BaseService<Product> {

    Page<Product> findAllByProveedorId(String Id, Pageable pageable);
    Page<Product> searchByAllColumnsByIdProveedorId(String search, String Id, Pageable pageable);
    Boolean updateFull(Product product, MultipartFile imageFile);
    List<Product> getProductos();

}
