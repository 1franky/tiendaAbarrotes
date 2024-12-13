package mx.unam.dgtic.system.service.reporte;

import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 12/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

public interface ReporteService {

    void inventarioBajo(int cantidad, String email) throws DocumentException, MessagingException;

    void reporteVentas(String email, String fechaInicio, String fechaFin) throws DocumentException, MessagingException;

}