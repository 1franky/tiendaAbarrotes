package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {


    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE " +
            "LOWER(c.id) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', c.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', c.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Category> searchByAllColumns(@Param("search") String search, Pageable pageable);

    @Query("SELECT c FROM Category c WHERE " +
            "LOWER(c.id) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', c.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', c.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Category> searchByAllColumns(@Param("search") String search);



}