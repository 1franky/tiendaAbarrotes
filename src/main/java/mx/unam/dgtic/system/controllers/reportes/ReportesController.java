package mx.unam.dgtic.system.controllers.reportes;

import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.service.reporte.ReporteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 12/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Controller
@RequestMapping("reporte")
@RequiredArgsConstructor
@Log4j2
public class ReportesController {

    private final ReporteService reporteService;

    private String getEntityName() {
        return "Reporte";
    }

    private String getViewName() {
        return "reportes";
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("contenido", getEntityName() + "s");

        return "reporte/reporte";
    }

    @PostMapping("/inventario")
    public String generarReporteInventario(@RequestParam int numProductos,
                                           @RequestParam String email,
                                           RedirectAttributes flash) {
        log.info("Reporte de inventtario bajo.");

        try {
            reporteService.inventarioBajo(numProductos, email);
            flash.addFlashAttribute("success", "Reporte enviado a: " + email);
            log.info("Reporte de inventario completo.");
        } catch (DocumentException e) {
            log.error("Error al generar reporte de inventario {}", e.getMessage());
            flash.addFlashAttribute("error", "Error al generar el pdf: " + e.getMessage());
        } catch (MessagingException e){
            log.error("Error al enviar reporte de inventario {}", e.getMessage());
            flash.addFlashAttribute("error", "Error al enviar el pdf: " + e.getMessage());
        }
        return "redirect:/reporte/";
    }

    @PostMapping("/ventas")
    public String generarReporteVentas(
            @RequestParam String tipoReporte,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam String email,
            RedirectAttributes flash) {


        log.info("Reporte de ventas.");

        try {

            if ("hoy".equals(tipoReporte)) {
                log.info("Generando reporte de ventas del día.");
                reporteService.reporteVentas(email, null, null);
            } else if ("rango".equals(tipoReporte)) {
                log.info("Generando reporte de ventas desde {} hasta {}", fechaInicio, fechaFin);
                reporteService.reporteVentas(email, fechaInicio, fechaFin);
            }
            log.info("Reporte de ventas completado.");
            flash.addFlashAttribute("success", "Reporte enviado a: " + email);
        } catch (DocumentException e) {
            log.error("Error al generar reporte de ventas {}", e.getMessage());
            flash.addFlashAttribute("error", "Error al generar el pdf: " + e.getMessage());
        } catch (MessagingException e){
            log.error("Error al enviar reporte de ventas {}", e.getMessage());
            flash.addFlashAttribute("error", "Error al enviar el pdf: " + e.getMessage());
        }

        return "redirect:/reporte/";
    }

}