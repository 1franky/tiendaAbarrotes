package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, String> {

    Optional<Proveedor> findByName(String name);

//    @Query(name = "Proveedor.findActiveByCategory")
//    List<Proveedor> findActiveByCategory(String categoryName);

    @Query("SELECT p FROM Proveedor p " +
            "JOIN FETCH p.email " +
            "JOIN FETCH p.phone " +
            "WHERE p.id = :proveedorId")
    Optional<Proveedor> findProveedorWithContactInfo(String proveedorId);

    @Query("SELECT DISTINCT p FROM Proveedor p " +
            "JOIN p.products pr " +
            "WHERE pr.activo = true")
    List<Proveedor> findProveedoresWithActiveProducts();


    @Query("SELECT p FROM Proveedor p WHERE " +
            "LOWER(FUNCTION('TO_CHAR', p.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', p.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.address) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CASE WHEN p.activo = true THEN 'true' ELSE 'false' END) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Proveedor> searchByAllColumns(@Param("search") String search, Pageable pageable);

    @Query("SELECT p FROM Proveedor p WHERE " +
            "LOWER(FUNCTION('TO_CHAR', p.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', p.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(CASE WHEN p.activo = true THEN 'true' ELSE 'false' END) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Proveedor> searchByAllColumns(@Param("search") String search);


}