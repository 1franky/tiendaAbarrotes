package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByExistenciaLessThanEqual(Integer existencia);

    List<Product> findByExistenciaLessThanEqualOrderByProveedor_NameAscCategory_NameAsc(Integer existencia);

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

    Page<Product> findByProveedor_Id(String id, Pageable pageable);

}