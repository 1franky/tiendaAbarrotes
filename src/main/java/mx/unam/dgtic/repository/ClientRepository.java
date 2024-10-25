package mx.unam.dgtic.repository;

import mx.unam.dgtic.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByEmail(String email);

    @Query(name = "Proveedor.findActiveByCategory")
    List<Client> findByNameContain(String name);


    @Query("SELECT c FROM Client c " +
            "JOIN FETCH c.tickets " +
            "WHERE c.id = :clientId")
    Optional<Client> findClientWithTickets(String clientId);

    @Query("SELECT c FROM Client c " +
            "WHERE SIZE(c.tickets) > :ticketCount")
    List<Client> findClientsWithMoreThanTickets(int ticketCount);

}