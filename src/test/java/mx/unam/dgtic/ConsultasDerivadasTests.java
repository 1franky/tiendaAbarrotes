package mx.unam.dgtic;

import mx.unam.dgtic.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 13/10/24
 * @project M8AP_Francisco_Lopez
 * Descripción: [...]
 */

@SpringBootTest
public class ConsultasDerivadasTests {

    private static final String ALUMNO = "Francisco Miztli Lopez Salinas";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Test
    @Transactional
    public void findProductByNameTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar product por nombre");

        productRepository.findByName("Carne de Res").ifPresentOrElse(System.out::println, () -> {
            System.out.println("Not found by Name: Carne de Res" );
        });
    }

    @Test
    @Transactional
    public void findProductosByActivoTrueTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar productos activos");

        productRepository.findByActivoTrue().forEach(p -> {
            System.out.printf("Name: %s Activo: %s %n", p.getName(), p.getActivo());
        });
    }

    @Test
    @Transactional
    public void findProductosByPrecioVentaTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar productos precio venta");

        productRepository.findByPrecioVentaGreaterThan(10.5F).forEach(p -> {
            System.out.printf("Name: %s Precio Venta: %f %n", p.getName(), p.getPrecioVenta());
        });
    }

    @Test
    @Transactional
    public void findProductosByCategoryNameTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar productos por nombre de categoria");

        productRepository.findByCategory_Name("Bebidas").forEach(p -> {
            System.out.printf("Name: %s Categoria: %s %n", p.getName(), p.getCategory().getName());
        });
    }

    @Test
    @Transactional
    public void findProductosByNameOrdenadoPrecioVnetaTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar productos por nombre de proveedor ordenados por precio venta");

        productRepository.findByProveedor_NameOrderByPrecioVentaDesc("Proveedor A")
                .forEach(p -> {
                    System.out.printf("Name: %s Proveedor: %s Precio Venta: %f %n",
                            p.getName(), p.getProveedor().getName(), p.getPrecioVenta());
        });
    }

    @Test
    @Transactional
    public void findProductosByExistenciaMenorTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar productos por existencia baja");

        productRepository.findByExistenciaLessThan(40).forEach(p -> {
            System.out.printf("Name: %s Existencia: %d %n", p.getName(), p.getExistencia());
        });
    }

    @Test
    @Transactional
    public void findClientByEmailTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar Clients por email");

        clientRepository.findByEmail("carlos.garcia@example.com")
                .ifPresentOrElse(System.out::println, () -> {
                    System.out.println("Not found by Email: carlos.garcia@example.com");
        });
    }

    @Test
    @Transactional
    public void findTicketsByFechaTest(){

        System.out.println(ALUMNO);
        System.out.println("Buscar Tickets por fecha beetween");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTimeStart = LocalDateTime.parse("2024-02-03 12:00:00", formatter);
        LocalDateTime localDateTimeEnds = LocalDateTime.parse("2024-02-06 15:00:00", formatter);

        Instant start = localDateTimeStart.toInstant(ZoneOffset.UTC);
        Instant end = localDateTimeEnds.toInstant(ZoneOffset.UTC);

        ticketRepository.findByFechaBetween(start, end).forEach(t -> {
            System.out.printf("Fecha: %s Total: %.2f %n", t.getFecha(), t.getTotal());
        });
    }

    @Test
    @Transactional
    public void findUsersByTipoTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar Users por Tipo");

        userRepository.findByTipoUser(1).forEach(u -> {
            System.out.printf("Name: %s Tipo: %d %n", u.getName(), u.getTipoUser());
        });
    }

    @Test
    @Transactional
    public void findEmailsByDescripcionContainTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar Emails por descripcion");

        emailRepository.findByDescriptionContaining("Provee").forEach(e -> {
            System.out.printf("Email: %s Descripcion: %s %n", e.getEmail(), e.getDescription());
        });
    }

}
