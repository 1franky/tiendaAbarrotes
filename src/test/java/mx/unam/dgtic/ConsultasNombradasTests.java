package mx.unam.dgtic;

import mx.unam.dgtic.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 13/10/24
 * @project M8AP_Francisco_Lopez
 * Descripción: [...]
 */

@SpringBootTest
public class ConsultasNombradasTests {

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
    private ProveedorRepository proveedorRepository;

    @Test
    @Transactional
    public void findByPriceRangeTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar por rango de precios");

        productRepository.findByPriceRange(10F, 30F).forEach(product -> {
            System.out.printf("Name %s precio: %.2f %n",
                    product.getName(), product.getPrecioVenta());
        });

    }

    @Test
    @Transactional
    public void findByActiveAndCategoryNameTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar por Activo y categoria name");

        productRepository.findActiveByCategory("Bebidas").forEach(product -> {
            System.out.printf("Name %s categoria: %s %n", product.getName(),
                    product.getCategory().getName());
        });

    }

    @Test
    @Transactional
    public void findByClientIdTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar Tickets por client ID");

        ticketRepository.findByClientId("3c4d5e6f-7890-12cd-ef01-3456789012cd")
                .forEach(t -> {
                    System.out.printf("Fecha %s total: %.2f cliente name %s %n",
                            t.getFecha(), t.getTotal(), t.getClient().getName());
        });
    }

    @Test
    @Transactional
    public void findByTipoUserAndNameTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar por tipo user y nombre contiene");

        userRepository.findByTipoUserAndName(2,"ado").forEach(u -> {
            System.out.printf("Name %s total: %d  %n", u.getName(), u.getTipoUser());
        });

    }

    @Test
    @Transactional
    public void findActivoAndCategoryTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar proveedores activos y category name");

        proveedorRepository.findActiveByCategory("Panadería")
                .forEach(p -> {
                    System.out.printf("Name %s categoria: %s activo: %s %n",
                            p.getName(), p.getCategory().getName(), p.getActivo());
        });

    }


}
