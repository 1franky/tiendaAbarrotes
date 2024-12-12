package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByFechaBetween(Instant startDate, Instant endDate);

    @Query("SELECT t FROM Ticket t WHERE " +
            "LOWER(FUNCTION('TO_CHAR', t.createdAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', t.updatedAt, 'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(FUNCTION('TO_CHAR', t.fecha,     'YYYY-MM-DD HH24:MI:SS')) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(t.total AS string) LIKE LOWER(CONCAT('%', :search, '%'))")
//            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
//            "LOWER(p.address) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
//            "LOWER(CASE WHEN p.activo = true THEN 'true' ELSE 'false' END) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Ticket> searchByAllColumns(@Param("search") String search, Pageable pageable);


}