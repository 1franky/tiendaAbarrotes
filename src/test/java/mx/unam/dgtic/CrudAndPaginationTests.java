package mx.unam.dgtic;

import mx.unam.dgtic.entity.Product;
import mx.unam.dgtic.repository.CategoryRepository;
import mx.unam.dgtic.repository.ImageRepository;
import mx.unam.dgtic.repository.ProductRepository;
import mx.unam.dgtic.repository.ProveedorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 13/10/24
 * @project M8AP_Francisco_Lopez
 * Descripción: OPERACIONES CRUD BASICAS
 */

@SpringBootTest
public class CrudAndPaginationTests {

    private static String ALUMNO = "Francisco Miztli López Salinas";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    @Transactional
    public void createProductTest(){
        System.out.println(ALUMNO);
        System.out.println("Guardando producto.");
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        product.setName("Product name TEST");
        product.setDescription("Product description TEST");
        product.setPrecioVenta(15.50F);
        product.setPrecioProveedor(10F);
        product.setExistencia(15);
        product.setActivo(Boolean.TRUE);
        product.setProveedor(proveedorRepository.findAll().iterator().next());
        product.setCategory(categoryRepository.findAll().iterator().next());
        product.setImage(imageRepository.findAll().iterator().next());
        productRepository.save(product);
        System.out.printf("Product guardado exitosamente. %s", product);
    }

    @Test
    @Transactional
    public void findProductByIdTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscando product por nombre");

        String id = productRepository.findAll().iterator().next().getId();

        productRepository.findById(id).ifPresentOrElse(p -> {
            System.out.printf("Producto encontrado %s ", p);
        }, () -> System.out.printf("Product con ID %s not found", id));
    }

    @Test
    @Transactional
    public void findAllPaginationTest(){
        System.out.println(ALUMNO);
        System.out.println("Products por paginador");
        Pageable pageable = PageRequest.of(2, 3);
        Page<Product> products = productRepository.findAll(pageable);

        System.out.printf("Numero de pagina: %d %n", products.getNumber());
        System.out.printf("Numero total de elemntos: %d %n", products.getTotalElements());
        System.out.printf("Elemtnos en la pagina %n");
        products.forEach(p -> {
            System.out.printf("id: %s nombre: %s categoria: %s %n",
                    p.getId(), p.getName(), p.getCategory().getName());
        });
    }

    @Test
    @Transactional
    @Commit
    public void updateProductTest(){
        System.out.println(ALUMNO);
        System.out.println("Actualizando product ");

        String id = productRepository.findAll().iterator().next().getId();

        productRepository.findById(id).ifPresentOrElse(p -> {
            p.setUpdatedAt(Instant.now());
            p.setExistencia(30);
            p.setImage(imageRepository.findAll().iterator().next());
            p.setPrecioProveedor(16.5F);
            productRepository.save(p);
            System.out.printf("Producto actualizado %s ", p);
        }, () -> System.out.printf("Product con ID %s not found", id));
    }

}
