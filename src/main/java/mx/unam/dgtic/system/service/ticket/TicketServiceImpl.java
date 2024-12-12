package mx.unam.dgtic.system.service.ticket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.Client;
import mx.unam.dgtic.system.entity.Product;
import mx.unam.dgtic.system.entity.ProductsTicket;
import mx.unam.dgtic.system.entity.Ticket;
import mx.unam.dgtic.system.repository.ClientRepository;
import mx.unam.dgtic.system.repository.ProductRepository;
import mx.unam.dgtic.system.repository.ProductsTicketRepository;
import mx.unam.dgtic.system.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 10/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Log4j2
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;

    private final ProductsTicketRepository productsTicketRepository;

    private final ProductRepository productRepository;

    private final ClientRepository clientRepository;


    @Override
    public Page<Ticket> findAll(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @Override
    public Page<Ticket> searchByAllColumns(String search, Pageable pageable) {
        return ticketRepository.searchByAllColumns(search, pageable);
    }

    @Override
    public Ticket findById(String id) {
        return null;
    }

    @Override
    public Ticket save(Ticket entity) throws Exception {
        return null;
    }

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    public Ticket getEmpty() {
        return null;
    }

    @Override
    public Boolean saveTicket(String json) {
        try {

            String ticketId = UUID.randomUUID().toString();

            AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.valueOf(0));
            List<Client> clients = clientRepository.findAll();
            Client client = clients.get(0);
            Set<ProductsTicket> productsTickets = new HashSet<>();

            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> products = mapper.readValue(json, new TypeReference<>() {});
            products.forEach(productData -> {
                ProductsTicket pt = new ProductsTicket();
                productRepository.findById(productData.get("productId").toString()).ifPresent(p -> {
                    int cantidad = Integer.parseInt(productData.get("quantity").toString());
                    BigDecimal cantidadBd = BigDecimal.valueOf(cantidad);
                    pt.setId(UUID.randomUUID().toString());
                    pt.setCantidad(cantidad);
                    pt.setPrecioVenta(p.getPrecioVenta());
                    pt.setProduct(p);
                    total.updateAndGet(v -> v.add(cantidadBd.multiply(p.getPrecioVenta()))); // Multiplicación de BigDecimal
                });
                productsTickets.add(pt);
            });

            Ticket ticket = Ticket.builder()
                    .id(ticketId)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .fecha(Instant.now())
                    .total(total.getAcquire())
                    .client(client)
//                    .productsTickets(productsTickets)
                    .build();

            ticketRepository.save(ticket);

            productsTickets.forEach(productsTicket -> {
                productsTicket.setTicket(ticket);
                productsTicketRepository.save(productsTicket);
                Product p = productsTicket.getProduct();
                p.setExistencia(p.getExistencia() - productsTicket.getCantidad());
                productRepository.save(p);
            });


            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("Error al generar ticket: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    public List<ProductsTicket> getTicket(String id) {
        return productsTicketRepository.findByTicket_Id(id);
    }


}