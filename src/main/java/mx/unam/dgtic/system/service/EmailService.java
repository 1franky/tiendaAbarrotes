package mx.unam.dgtic.system.service;

import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.Product;
import mx.unam.dgtic.system.entity.ProductsTicket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
public class EmailService {

    private final JavaMailSender mailSender;

    private final PdfGenerationService pdfGenerationService;

    @Value(value = "${spring.mail.username}")
    private String EMAIL_FROM;

    public void sendTicketByEmail(String toEmail, List<ProductsTicket> productsTickets) throws MessagingException, DocumentException {


        byte[] pdfContent = pdfGenerationService.generateTicketPdf(productsTickets);

        String ticketId = productsTickets.get(0).getTicket().getId();
        String clientName = productsTickets.get(0).getTicket().getClient().getName();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(EMAIL_FROM); // Configura tu email de envío
        helper.setTo(toEmail);
        helper.setSubject("Detalles del Ticket #" + ticketId);

        String emailBody = String.format(
                """
                        Estimado %s,\s
                        
                        Adjunto encontrará los detalles del ticket #%s.
                        
                        Gracias por su compra.
                        
                        Saludos cordiales,
                        UNAM - DIPLOMADO 15. Francisco Mizlti Lopez Salinas.""",
                clientName, ticketId
        );

        helper.setText(emailBody);

        helper.addAttachment("Ticket_" + ticketId + ".pdf",
                new ByteArrayResource(pdfContent),
                "application/pdf");

        mailSender.send(message);
    }


    public void sendEmail(String toEmail, String subject, String body, String tittelAttachment, byte[] attachment ) throws MessagingException {
        log.info("Sending email to {}", toEmail);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(EMAIL_FROM);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);

        helper.addAttachment(tittelAttachment,
                new ByteArrayResource(attachment),
                "application/pdf");

        mailSender.send(message);

    }


}