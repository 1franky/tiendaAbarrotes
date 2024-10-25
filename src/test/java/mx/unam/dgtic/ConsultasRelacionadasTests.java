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
public class ConsultasRelacionadasTests {

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
    public void findProductWithCategoryTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar producto con categoria");

        productRepository.findProductWithCategory("2b3c4d5e-6789-01bc-def0-2345678901bc")
                .ifPresentOrElse(p -> {
                    System.out.printf("Nombre: %s categoria: %s %n",
                            p.getName(), p.getCategory().getName());
        }, () -> System.out.println("No existe producto "));

    }

    @Test
    @Transactional
    public void findProductWithCategoryAndProveedorTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar producto con categoria y proveedor");

        productRepository.findProductWithProveedorAndCategory("2b3c4d5e-6789-01bc-def0-2345678901bc")
                .ifPresentOrElse(p -> {
                    System.out.printf("Nombre: %s categoria: %s Proveedor: %s %n",
                            p.getName(), p.getCategory().getName(), p.getProveedor().getName());
        }, () -> System.out.println("No existe producto "));
    }

    @Test
    @Transactional
    public void findTicketWithProductsTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar Ticket con products detalles");

        ticketRepository.findTicketWithDetails("1a2b3c4d-5678-90ab-cdef-1234567890ab")
                .ifPresentOrElse(t -> {
                    System.out.printf("Ticket: %s Fecha: %s Total: %.2f Cliente: %s Products: %n",
                            t.getId(), t.getFecha(), t.getTotal(), t.getClient().getName());
                    t.getProductsTickets().forEach(pt -> {
                        System.out.printf("Product: %s \t Precio Venta: %.2f  %n",
                                pt.getProduct().getName(), pt.getPrecioVenta());
            });
        }, () -> System.out.println("No existe ticket "));
    }

    @Test
    @Transactional
    public void findTicketMinClientTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar Ticket por client ID y minTotal");

        ticketRepository.findClientTickets(15F, "2b3c4d5e-6789-01bc-def0-2345678901bc")
                .forEach(t -> {
                    System.out.printf("Ticket: %s Fecha: %s Total: %.2f Cliente: %s Products: %n",
                            t.getId(), t.getFecha(), t.getTotal(), t.getClient().getName());
                    t.getProductsTickets().forEach(pt -> {
                        System.out.printf("Product: %s \t Precio Venta: %.2f  %n",
                                pt.getProduct().getName(), pt.getPrecioVenta());
            });
        });
    }

    @Test
    @Transactional
    public void findProveedorWithContactTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar proveedor con informaicon email y phone");

        proveedorRepository.findProveedorWithContactInfo("3c4d5e6f-7890-12cd-ef01-3456789012cd")
                .ifPresentOrElse(p -> {
                    System.out.printf("Proveedor: %s email: %s Phone: %s  %n",
                            p.getName(), p.getEmail().getEmail(), p.getPhone().getPhone());
        }, () -> System.out.println("No existe proveedor "));
    }

    @Test
    @Transactional
    public void findUserWithImageTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar usuario con image");

        userRepository.findUserWithImage("2b3c4d5e-6789-01bc-def0-2345678901bc")
                .ifPresentOrElse(u -> {
                    System.out.printf("user name: %s path Image: %s  %n",
                            u.getName(), u.getImage().getPathImage());
        }, () -> System.out.println("No existe usuario "));
    }

    @Test
    @Transactional
    public void findUserAdminsTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar usuarios administradores");

        userRepository.findAdminUsersWithImages().forEach(u -> {
            System.out.printf("user name: %s tipo user: %d  %n",
                    u.getName(), u.getTipoUser());
        });
    }

    @Test
    @Transactional
    public void findClientWithTicketsTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar cliente con tickets");

        clientRepository.findClientWithTickets("4d5e6f7g-8901-23de-f012-4567890123de")
                .ifPresentOrElse(c -> {
                    System.out.printf("id: %s name: %s tickets: %n", c.getId(), c.getName());
                    c.getTickets().forEach(t -> {
                        System.out.printf("ticket total: %.2f Products: %n", t.getTotal());
                        t.getProductsTickets().forEach(pt -> {
                            System.out.printf("product: %s precio venta: %.2f %n",
                                    pt.getProduct().getName(), pt.getPrecioVenta());
                });
            });
        }, () -> System.out.println("No existe cliente "));
    }

    @Test
    @Transactional
    public void findClientsWithMoreTicketsTest(){
        System.out.println(ALUMNO);
        System.out.println("Buscar clients con un minimo de tickets");

        clientRepository.findClientsWithMoreThanTickets(0).forEach(c -> {
            System.out.printf("Cliente name: %s numero de tickets: %d %n",
                    c.getName(), c.getTickets().size());
        });
    }




}
