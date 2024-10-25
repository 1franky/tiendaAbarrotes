package mx.unam.dgtic.service.product;

import mx.unam.dgtic.entity.Product;
import mx.unam.dgtic.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    List<Product> searchByAllColumnsByIdProveedorId(String search, String Id);



}
