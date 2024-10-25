package mx.unam.dgtic.repository;

import mx.unam.dgtic.entity.ProductsTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsTicketRepository extends JpaRepository<ProductsTicket, String> {
  }