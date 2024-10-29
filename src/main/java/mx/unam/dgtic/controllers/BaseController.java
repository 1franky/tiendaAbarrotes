package mx.unam.dgtic.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.service.BaseService;
import mx.unam.dgtic.utils.RenderPagina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Log4j2
public abstract class BaseController<T> {

    protected abstract BaseService<T> getService();

    protected abstract String getEntityName();

    protected abstract String getViewName();

    protected abstract Map<String, List<Object>> getSelects();

    @GetMapping("/")
    public String listAll(@RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "4") int size,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "search", required = false) String search,
                          Model model) {

        T entity;
        if (model.containsAttribute(getEntityName())){
            log.info("se encontro {} -> {}",getEntityName(), model.getAttribute(getEntityName()));
            entity = (T) model.getAttribute(getEntityName());
        }else {
            entity = getService().getEmpty();
        }


        Map<String, List<Object>> selects = getSelects();
        for (Map.Entry<String, List<Object>> entry : selects.entrySet())
            model.addAttribute(entry.getKey(), entry.getValue());

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<T> entities;

        if (search != null && !search.isEmpty()) {
            entities = getService().searchByAllColumns(search, pageable);
        } else {
            entities = getService().findAll(pageable);
        }

        RenderPagina<T> renderPagina = new RenderPagina<>("/" + getEntityName() + "/", entities, size);

        model.addAttribute(getViewName(), entities);
        model.addAttribute(getEntityName(), entity);
        model.addAttribute("page", renderPagina);
        model.addAttribute("contenido", getEntityName() + "s");
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("invertDirection", "asc".equals(direction) ? "desc" : "asc");

        return getEntityName() + "/" + getEntityName();

    }

    @PostMapping("/")
    public String saveEntity(@Valid @ModelAttribute("entity") T entity,
                             BindingResult result,
                             RedirectAttributes flash) {
        log.info("guardar: {}", entity);
        flash.addFlashAttribute(getEntityName(), entity);
        if (result.hasErrors()) {
            errorsValidation(result, flash, entity);
            return "redirect:/" + getEntityName() + "/";
        }

        try {
            log.info("guardar: {}", entity);
            getService().save(entity);
            flash.addFlashAttribute("success", getEntityName() + " guardado con éxito");
        } catch (Exception e) {
            log.error("Error al guardar: {}", e.getMessage());
            errorsSave(flash, entity, e);
        }
        return "redirect:/" + getEntityName() + "/";
    }

    @GetMapping("eliminar/{id}")
    public String deleteEntity(@PathVariable("id") String id, RedirectAttributes flash) {
        log.info("eliminar: {}", id);
        try {
            getService().delete(id);
            flash.addFlashAttribute("success", getEntityName() + " eliminada exitosamente");
        } catch (Exception e) {
            log.error("No se puede eliminar {}", id);
            flash.addFlashAttribute("error", "Error al Eliminar.");
        }
        return "redirect:/" + getEntityName() + "/";
    }

//    @GetMapping("modificar/{id}")
//    public String modificarCategory(@PathVariable("id") String id, RedirectAttributes flash) {
//        T entity = getService().findById(id);
//        flash.addFlashAttribute("contenido", "Modificar " + getEntityName() + ".");
//        flash.addFlashAttribute(getEntityName(), entity);
//        log.info("Modificar: {}", id);
//        return "redirect:/" + getEntityName() + "/";
//    }

    protected void errorsValidation(BindingResult result, RedirectAttributes flash, T entity) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        result.getFieldErrors().forEach(error -> {
            String fieldNameError = error.getField() + "Error";
            flash.addFlashAttribute(fieldNameError, error.getDefaultMessage());
//            log.error("Campo: {} -> error: {}", fieldNameError, error.getDefaultMessage());
        });
        flash.addFlashAttribute(getEntityName(), entity);
    }

    protected void errorsSave(RedirectAttributes flash, T entity, Exception e) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        flash.addFlashAttribute(getEntityName(), entity);
        flash.addFlashAttribute("nameError", e.getMessage());
    }

}