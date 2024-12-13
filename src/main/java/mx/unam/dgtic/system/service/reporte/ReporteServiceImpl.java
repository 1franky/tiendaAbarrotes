package mx.unam.dgtic.system.service.reporte;

import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.Product;
import mx.unam.dgtic.system.entity.Ticket;
import mx.unam.dgtic.system.repository.ProductRepository;
import mx.unam.dgtic.system.repository.ProductsTicketRepository;
import mx.unam.dgtic.system.repository.TicketRepository;
import mx.unam.dgtic.system.service.EmailService;
import mx.unam.dgtic.system.service.PdfGenerationService;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 12/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ProductRepository productRepository;

    private final ProductsTicketRepository productsTicketRepository;

    private final TicketRepository ticketRepository;

    private final EmailService emailService;

    private final PdfGenerationService pdfGenerationService;


    @Override
    public void inventarioBajo(int cantidad, String email) throws DocumentException, MessagingException {

        List<Product> products = productRepository.findByExistenciaLessThanEqualOrderByProveedor_NameAscCategory_NameAsc(cantidad);

        byte[] pdfContent = pdfGenerationService.generateProductsBajosPdf(products);

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime fecha = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String fechaString = formatoFecha.format(fecha);

        String subject = "Reporte de inventario bajo";
        String body = String.format(
                """
                        Se envía reporte de inventario bajo
                        
                        Cantidad de productos por debajo de : %d
                        Fecha de reporte: %s
                        
                        
                        Saludos coordiales.
                        
                        Francisco Miztli López Salinas.
                        Diplomado JAVA 15.
                        """, cantidad, fechaString
        );

        String title = "inventarioBajo_" + fechaString + ".pdf";

        emailService.sendEmail(email, subject, body, title, pdfContent);
        return;
    }


    @Override
    public void reporteVentas(String email, String fechaInicio, String fechaFin) throws DocumentException, MessagingException {

        log.info("Inicia inventario de ventas");

        Instant inicio;
        Instant fin;

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime fecha = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String fechaString = formatoFecha.format(fecha);

        String fechasMensaje, titleFecha;
        if (fechaInicio == null || fechaFin == null) {
            LocalDate today = LocalDate.now();
            inicio = today.atStartOfDay(ZoneOffset.UTC).toInstant();
            fin = today.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant();
            fechasMensaje = "El reporte solo incluye las ventas realizadas el dia de hoy.";
            titleFecha = fechaString;
        } else {
            DateTimeFormatter formatoFecha1 = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate date1 = LocalDate.parse(fechaInicio, formatoFecha1);
            LocalDate date2 = LocalDate.parse(fechaFin, formatoFecha1);
            inicio = date1.atStartOfDay(ZoneOffset.UTC).toInstant();
            fin    = date2.atTime(LocalTime.MAX).atZone(ZoneOffset.UTC).toInstant();
            fechasMensaje = String.format("""
                    El reporte incluye las ventas realizadas entre las siguientes fechas.
                    fecha de Inicio %s
                    fecha de Fin %s
                    """, fechaInicio, fechaFin
            );
            titleFecha = String.format("%s - %s", fechaInicio, fechaFin);
        }

        List<Ticket> tickets = ticketRepository.findByCreatedAtBetweenOrderByCreatedAtAsc(inicio, fin);

        log.info("Se encontraron: {} tickets", tickets.size());

        byte[] pdfContent = pdfGenerationService.generateReporteVentasPdf(tickets, titleFecha);

        String subject = "Reporte de ventas.";
        String body = String.format(
                """
                        Se envía reporte de ventas.
                        
                        Cantidad de tickets: %d
                        Fecha de reporte: %s
                        
                        %s
                        
                        Saludos coordiales.
                        
                        Francisco Miztli López Salinas.
                        Diplomado JAVA 15.
                        """, tickets.size(), fechaString, fechasMensaje
        );

        String title = "reporte_ventas_" + fechaString + ".pdf";

        emailService.sendEmail(email, subject, body, title, pdfContent);
        return;
    }


}