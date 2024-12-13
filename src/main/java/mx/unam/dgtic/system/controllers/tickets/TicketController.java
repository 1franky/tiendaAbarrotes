package mx.unam.dgtic.system.controllers.tickets;

import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.ProductsTicket;
import mx.unam.dgtic.system.entity.Ticket;
import mx.unam.dgtic.system.service.EmailService;
import mx.unam.dgtic.system.service.product.ProductService;
import mx.unam.dgtic.system.service.ticket.TicketService;
import mx.unam.dgtic.system.utils.RenderPagina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */


@Controller
@RequestMapping("ticket")
@RequiredArgsConstructor
@Log4j2
public class TicketController {

    private final TicketService ticketService;

    private final ProductService productService;

    private final EmailService emailService;

    protected String getEntityName() {
        return "ticket";
    }

    protected String getViewName() {
        return "tickets";
    }

    protected Map<String, List<Object>> getSelects(){

        Map<String, List<Object>> select = new HashMap<>();
        select.put("productsList", new ArrayList<>());

        productService.getProductos().forEach(select.get("productsList")::add);

        return select;
    }

    @GetMapping("details/{id}")
    public String details(@PathVariable("id") String id, Model model){

        log.info("Buscando ticket con id: {}", id);
        List<ProductsTicket> productsTickets =  ticketService.getTicket(id);

        log.info("Tamaño del ticket: {}", productsTickets.size());
        if (productsTickets.isEmpty() ) {
            log.error("No tienes productos con id: {}", id);
            return "redirect:/ticket/";
        }

        model.addAttribute("productsTickets", productsTickets);
        return "ticket/cardDetails";
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("id") String ticketId,
                            @RequestParam("email") String email,
                            RedirectAttributes flash) {

        log.info("Enviando ticket con id: {}", ticketId);
        List<ProductsTicket> productsTickets =  ticketService.getTicket(ticketId);
        try {
            emailService.sendTicketByEmail(email, productsTickets);
            flash.addFlashAttribute("success", "Ticket enviado a: " + email);
        } catch (MessagingException e) {
            log.error("Error al enviar el email: {}", e.getMessage());
            flash.addFlashAttribute("error", "Error al enviar email.");
        } catch (DocumentException e) {
            log.error("Error al generar el documento: {}", e.getMessage());
            flash.addFlashAttribute("error", "Error al generar el documento.");
        }


        return "redirect:/ticket/details/" + ticketId;

    }

    @GetMapping("/")
    public String listAll(@RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "4") int size,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "search", required = false) String search,
                          Model model) {

        Ticket entity;
        if (model.containsAttribute(getEntityName())){
            log.info("se encontro {} -> {}",getEntityName(), model.getAttribute(getEntityName()));
            entity = (Ticket) model.getAttribute(getEntityName());
        }else {
                entity = new Ticket();
        }

        getSelects().forEach(model::addAttribute);

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Ticket> entities;

        if (search != null && !search.isEmpty()) {
            entities = ticketService.searchByAllColumns(search, pageable);
        }else {
            entities = ticketService.findAll(pageable);
        }

        RenderPagina<Ticket> renderPagina = new RenderPagina<>("/" + getEntityName() + "/", entities, size);

        model.addAttribute(getViewName(), entities);
        model.addAttribute(getEntityName(), entity);
        model.addAttribute("page", renderPagina);
        model.addAttribute("contenido", getEntityName() + "s");
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("invertDirection", "asc".equals(direction) ? "desc" : "asc");

        return "ticket/ticket";

    }

    @PostMapping("/")
    public String saveEntity(@RequestParam("productsJson") String productsJson,
                             Model model,
                             RedirectAttributes flash){

        log.info("guardar: {}", productsJson);

        try {
            boolean r = ticketService.saveTicket(productsJson);
            if (Boolean.TRUE.equals(r)) {
                flash.addFlashAttribute("success", getEntityName() + " guardado con éxito");
            }else{
                flash.addFlashAttribute("error", getEntityName() + " no se pudo guardar");
            }
        } catch (Exception e) {
            log.error("Error al guardar: {}", e.getMessage());
            flash.addFlashAttribute("error", getEntityName() + " no se pudo guardar");
//            errorsSave(flash, entity, e);
        }
        return "redirect:/ticket/";
    }


    private void errorsValidation(BindingResult result, RedirectAttributes flash, Ticket entity) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        result.getFieldErrors().forEach(error -> {
            String fieldNameError = error.getField() + "Error";
            flash.addFlashAttribute(fieldNameError, error.getDefaultMessage());
//            log.error("Campo: {} -> error: {}", fieldNameError, error.getDefaultMessage());
        });
        flash.addFlashAttribute(getEntityName(), entity);
    }

    private void errorsSave(RedirectAttributes flash, Ticket entity, Exception e) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        flash.addFlashAttribute(getEntityName(), entity);
        flash.addFlashAttribute("nameError", e.getMessage());
    }


}