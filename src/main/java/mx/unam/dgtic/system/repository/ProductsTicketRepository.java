package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.ProductsTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsTicketRepository extends JpaRepository<ProductsTicket, String> {


  List<ProductsTicket> findByTicket_Id(String id);
}