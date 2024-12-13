package mx.unam.dgtic.system.controllers.proveedor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.Proveedor;
import mx.unam.dgtic.system.service.BaseService;
import mx.unam.dgtic.system.service.category.CategoryService;
import mx.unam.dgtic.system.service.proveedor.ProveedorService;
import mx.unam.dgtic.system.utils.RenderPagina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@SuppressWarnings("DuplicatedCode")
@Controller
@RequestMapping("proveedor")
@RequiredArgsConstructor
@Log4j2
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final CategoryService categoryService;

    protected BaseService<Proveedor> getService() {
        return proveedorService;
    }

    protected String getEntityName() {
        return "proveedor";
    }

    protected String getViewName() {
        return "proveedores";
    }

    protected Map<String, List<Object>> getSelects(){
        Map<String, List<Object>> select = new HashMap<>();

        // Category
        select.put("selectCategory", new ArrayList<>());
        categoryService.getCategories().forEach(select.get("selectCategory")::add);

        return select;
    }

    @GetMapping("details/{id}")
    public String details(@PathVariable("id") String id, Model model){
        Proveedor proveedor =  proveedorService.findById(id);
        if (proveedor.getImage() == null){
            log.warn("Proveedor con id {} no tiene imagen se usara default", id);
            model.addAttribute("imgDefault", "/images/default2.jpg");
        }
        model.addAttribute("prov", proveedor);
        return "proveedor/cardDetails";
    }

    @GetMapping("edit/{id}")
    public String editProveedor(@PathVariable("id") String id, Model model){
        log.info("Editar proveedor {}", id);
        Proveedor proveedor =  proveedorService.findById(id);
        if (proveedor.getImage() == null){
            log.warn("Proveedor con id {} no tiene imagen se usara default", id);
            model.addAttribute("imgDefault", "/images/default2.jpg");
        }
        Map<String, List<Object>> selects = getSelects();
        for (Map.Entry<String, List<Object>> entry : selects.entrySet())
            model.addAttribute(entry.getKey(), entry.getValue());
        model.addAttribute("prov", proveedor);
        model.addAttribute("contenido", "Editar Proveedor: " + proveedor.getName());
        return "proveedor/editFull";
    }

    @PostMapping("edit")
    public String saveEntity(@Valid @ModelAttribute("prov") Proveedor proveedor,
                             BindingResult result,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             RedirectAttributes flash) {
        log.info("Guardar: {}", proveedor);

        flash.addFlashAttribute("prov", proveedor);
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(e -> {
                String fieldNameError = e.getField().replace(".", "") + "Error";
                flash.addFlashAttribute(fieldNameError, e.getDefaultMessage());
            });
            return "redirect:/proveedor/edit/" + proveedor.getId();
        }
        Boolean r = proveedorService.updateFull(proveedor, imageFile);
        log.warn("Respuesta actualizar: {}", r);
        return "redirect:/proveedor/details/" + proveedor.getId();
    }

    @GetMapping("/")
    public String listAll(@RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "4") int size,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "search", required = false) String search,
                          Model model) {

        Proveedor entity;
        if (model.containsAttribute(getEntityName())){
            log.info("se encontro {} -> {}",getEntityName(), model.getAttribute(getEntityName()));
            entity = (Proveedor) model.getAttribute(getEntityName());
        }else {
            entity = getService().getEmpty();
        }

        getSelects().forEach(model::addAttribute);

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Proveedor> entities;

        if (search != null && !search.isEmpty()) {
            entities = getService().searchByAllColumns(search, pageable);
        }else {
            entities = getService().findAll(pageable);
        }

        RenderPagina<Proveedor> renderPagina = new RenderPagina<>("/" + getEntityName() + "/", entities, size);

        model.addAttribute(getViewName(), entities);
        model.addAttribute(getEntityName(), entity);
        model.addAttribute("page", renderPagina);
        model.addAttribute("contenido", getEntityName() + "s");
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("invertDirection", "asc".equals(direction) ? "desc" : "asc");

        return "proveedor/proveedor";

    }

    @PostMapping("/")
    public String saveEntity(@Valid @ModelAttribute("entity") Proveedor entity,
                             BindingResult result,
                             RedirectAttributes flash) {
        log.info("guardar: {}", entity);
        flash.addFlashAttribute(getEntityName(), entity);
        if (result.hasErrors()) {
            errorsValidation(result, flash, entity);
            return "redirect:/proveedor/";
        }

        try {
            getService().save(entity);
            flash.addFlashAttribute("success", getEntityName() + " guardado con éxito");
        } catch (Exception e) {
            log.error("Error al guardar: {}", e.getMessage());
            errorsSave(flash, entity, e);
        }
        return "redirect:/proveedor/";
    }

    @GetMapping("eliminar/{id}")
    public String deleteEntity(@PathVariable("id") String id, RedirectAttributes flash) {
        log.info("eliminar: {}", id);
        try {
            getService().delete(id);
            flash.addFlashAttribute("success", getEntityName()  + " eliminado exitosamente");
        } catch (Exception e) {
            log.error("No se puede eliminar {}", id);
            flash.addFlashAttribute("error", "Error al Eliminar.");
        }
        return "redirect:/proveedor/";
    }

    protected void errorsValidation(BindingResult result, RedirectAttributes flash, Proveedor entity) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        result.getFieldErrors().forEach(error -> {
            String fieldNameError = error.getField() + "Error";
            flash.addFlashAttribute(fieldNameError, error.getDefaultMessage());
//            log.error("Campo: {} -> error: {}", fieldNameError, error.getDefaultMessage());
        });
        flash.addFlashAttribute(getEntityName(), entity);
    }

    protected void errorsSave(RedirectAttributes flash, Proveedor entity, Exception e) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        flash.addFlashAttribute(getEntityName(), entity);
        flash.addFlashAttribute("nameError", e.getMessage());
    }


}