package mx.unam.dgtic.service.product;

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
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchByAllColumns(String search, Pageable pageable) {
        return productRepository.searchByAllColumns(search, pageable);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Product entity) throws Exception {
        log.info("Save Product {}", entity);
        if (entity.getId() == null || entity.getId().isBlank()) {
            entity.setId(UUID.randomUUID().toString());
            entity.setCreatedAt(Instant.now());
        }

        entity.setUpdatedAt(Instant.now());
        productRepository.save(entity);
    }

    @Override
    public void delete(String id) throws Exception {
        productRepository.deleteById(id);
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
}
