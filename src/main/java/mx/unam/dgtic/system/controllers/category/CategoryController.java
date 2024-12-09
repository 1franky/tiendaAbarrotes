package mx.unam.dgtic.system.controllers.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.Category;
import mx.unam.dgtic.system.service.category.CategoryService;
import mx.unam.dgtic.system.utils.RenderPagina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Controller
@RequestMapping("category")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;

    private CategoryService getService() {
        return categoryService;
    }

    private String getEntityName() {
        return "category";
    }

    private String getViewName() {
        return "categories";
    }

    private Class<Category> getEntityClass() {
        return Category.class;
    }

    @GetMapping("/")
    public String listAll(@RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "4") int size,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "search", required = false) String search,
                          Model model) {

        Category entity;
        if (model.containsAttribute(getEntityName())){
            log.info("se encontro {} -> {}",getEntityName(), model.getAttribute(getEntityName()));
            entity = (Category) model.getAttribute(getEntityName());
        }else {
            entity = getService().getEmpty();
        }

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Category> entities = null;
        String r, url;
        RestTemplate restTemplate = new RestTemplate();
        if (search != null && !search.isEmpty()) {
            entities = getService().searchByAllColumns(search, pageable);
        } else {
            entities = getService().findAll(pageable);
        }

        RenderPagina<Category> renderPagina = new RenderPagina<>("/" + getEntityName() + "/", entities, size);

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
    public String saveEntity(@Valid @ModelAttribute("entity") Category entity,
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

    protected void errorsValidation(BindingResult result, RedirectAttributes flash, Category entity) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        result.getFieldErrors().forEach(error -> {
            String fieldNameError = error.getField() + "Error";
            flash.addFlashAttribute(fieldNameError, error.getDefaultMessage());
        });
        flash.addFlashAttribute(getEntityName(), entity);
    }

    protected void errorsSave(RedirectAttributes flash, Category entity, Exception e) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        flash.addFlashAttribute(getEntityName(), entity);
        flash.addFlashAttribute("nameError", e.getMessage());
    }

}
