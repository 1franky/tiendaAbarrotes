package mx.unam.dgtic.repository;

import mx.unam.dgtic.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByFechaBetween(Instant startDate, Instant endDate);

    @Query(name = "Ticket.findByClientId")
    List<Ticket> findByClientId(@Param("clientId") String clientId);


    @Query("SELECT t " +
            "FROM Ticket t " +
            "JOIN FETCH t.client " +
            "JOIN FETCH t.productsTickets pt " +
            "JOIN FETCH pt.product WHERE t.id = :ticketId")
    Optional<Ticket> findTicketWithDetails(String ticketId);

    @Query("SELECT t FROM Ticket t " +
            "JOIN t.client c " +
            "WHERE t.total > :minTotal " +
            "AND c.id = :clientId ")
    List<Ticket> findClientTickets(Float minTotal, String clientId);


}