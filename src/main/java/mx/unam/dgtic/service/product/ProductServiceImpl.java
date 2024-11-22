package mx.unam.dgtic.service.product;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.unam.dgtic.entity.Product;
import mx.unam.dgtic.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 22/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

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
    public List<Product> searchByAllColumnsByIdProveedorId(String search, String Id) {
        return productRepository.searchByAllColumnsAndProveedorId(search, Id);
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
}
