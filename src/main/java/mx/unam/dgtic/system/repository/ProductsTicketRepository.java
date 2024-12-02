package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.ProductsTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsTicketRepository extends JpaRepository<ProductsTicket, String> {
  }