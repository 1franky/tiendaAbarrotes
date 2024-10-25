package mx.unam.dgtic.repository;

import mx.unam.dgtic.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByName(String name);
    List<Product> findByActivoTrue();
    List<Product> findByPrecioVentaGreaterThan(Float precio);
    List<Product> findByCategory_Name(String categoryName);
    List<Product> findByProveedor_NameOrderByPrecioVentaDesc(String proveedorName);
    List<Product> findByExistenciaLessThan(Integer existencia);

    @Query(name = "Product.findByPriceRange")
    List<Product> findByPriceRange(Float minPrice, Float maxPrice);

    @Query(name = "Product.findActiveByCategory")
    List<Product> findActiveByCategory(String categoryName);



    @Query("SELECT p FROM Product p JOIN FETCH p.category " +
            "WHERE p.id = :productId")
    Optional<Product> findProductWithCategory(String productId);


    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.proveedor " +
            "JOIN FETCH p.category " +
            "WHERE p.id = :productId")
    Optional<Product> findProductWithProveedorAndCategory(String productId);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(FUNCTION('TO_CHAR', p.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', p.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.proveedor.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.precioVenta AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.precioProveedor AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.existencia AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CASE WHEN p.activo = true THEN 'true' ELSE 'false' END) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> searchByAllColumns(@Param("search") String search, Pageable pageable);


    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(FUNCTION('TO_CHAR', p.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', p.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.proveedor.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.precioVenta AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.precioProveedor AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.existencia AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CASE WHEN p.activo = true THEN 'true' ELSE 'false' END) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Product> searchByAllColumns(@Param("search") String search);


    @Query("SELECT p FROM Product p WHERE " +
            "p.proveedor.id = :id AND " +
            "LOWER(FUNCTION('TO_CHAR', p.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', p.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.proveedor.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.precioVenta AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.precioProveedor AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.existencia AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CASE WHEN p.activo = true THEN 'true' ELSE 'false' END) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> searchByAllColumnsAndProveedorId(@Param("search") String search, @Param("id") String id, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "p.proveedor.id = :id AND " +
            "LOWER(FUNCTION('TO_CHAR', p.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', p.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.proveedor.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.precioVenta AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.precioProveedor AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(p.existencia AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CASE WHEN p.activo = true THEN 'true' ELSE 'false' END) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Product> searchByAllColumnsAndProveedorId(@Param("search") String search, @Param("id") String id);

    Page<Product> findByProveedor_Id(String id, Pageable pageable);


}