package mx.unam.dgtic.system.controllers.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.Product;
import mx.unam.dgtic.system.service.BaseService;
import mx.unam.dgtic.system.service.category.CategoryService;
import mx.unam.dgtic.system.service.product.ProductService;
import mx.unam.dgtic.system.service.proveedor.ProveedorService;
import mx.unam.dgtic.system.utils.RenderPagina;
import org.springframework.beans.factory.annotation.Value;
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
 * @date 22/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@SuppressWarnings("ALL")
@Controller
@RequestMapping("product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final ProveedorService proveedorService;

    @Value("${app.upload.dir}")
    private String UPLOAD_DIR;

    protected BaseService<Product> getService() {
        return productService;
    }

    protected String getEntityName() {
        return "product";
    }

    protected String getViewName() {
        return "productos";
    }

    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    protected Product cleanObject(Product object) {
        object.getProveedor().setImage(null);
        return object;
    }

    protected Map<String, List<Object>> getSelects() {
        Map<String, List<Object>> select = new HashMap<>();
        select.put("selectProveedor", new ArrayList<>());
        select.put("selectCategory", new ArrayList<>());

        categoryService.getCategories().forEach(category -> {
            select.get("selectCategory").add(category);
        });

        proveedorService.getProveedores().forEach(proveedor -> {
            select.get("selectProveedor").add(proveedor);
        });

        return select;
    }

    @GetMapping("proveedor/{id}")
    public String listAllByProveedor(@PathVariable("id") String id,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "4") int size,
                                     @RequestParam(name = "sort", defaultValue = "id") String sort,
                                     @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                     @RequestParam(name = "search", required = false) String search,
                                     Model model){

        Product product;
        if (model.containsAttribute(getEntityName())){
            log.info("se encontro {} -> {}",getEntityName(), model.getAttribute(getEntityName()));
            product = (Product) model.getAttribute(getEntityName());
        }else {
            product = getService().getEmpty();
        }

        Map<String, List<Object>> selects = getSelects();
        for (Map.Entry<String, List<Object>> entry : selects.entrySet())
            model.addAttribute(entry.getKey(), entry.getValue());

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Product> products;

        if (search != null && !search.isEmpty()) {
            products = productService.searchByAllColumnsByIdProveedorId(search, id, pageable);
        } else {
            products = productService.findAllByProveedorId(id, pageable);
        }

        String url = "/product/proveedor/" + id;
        RenderPagina<Product> renderPagina = new RenderPagina<>(url, products, size);

        model.addAttribute(getEntityName(), product);
        model.addAttribute("contenido", getEntityName() + "s");
        model.addAttribute("page", renderPagina);
        model.addAttribute(getViewName(), products);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("invertDirection", "asc".equals(direction) ? "desc" : "asc");

        return getEntityName() + "/" + getEntityName();
    }



    @GetMapping("/")
    public String listAll(@RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "4") int size,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "search", required = false) String search,
                          Model model) {

        Product entity;
        if (model.containsAttribute(getEntityName())){
            log.info("se encontro {} -> {}", getEntityName(), model.getAttribute(getEntityName()));
            entity = (Product) model.getAttribute(getEntityName());
        }else {
            entity = getService().getEmpty();
        }

        Map<String, List<Object>> selects = getSelects();
        for (Map.Entry<String, List<Object>> entry : selects.entrySet())
            model.addAttribute(entry.getKey(), entry.getValue());

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Product> entities = null;

        if (search != null && !search.isEmpty()) {
            entities = getService().searchByAllColumns(search, pageable);
        }else {
            entities = getService().findAll(pageable);
        }

        RenderPagina<Product> renderPagina = new RenderPagina<>("/" + getEntityName() + "/", entities, size);

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

    @GetMapping("details/{id}")
    public String details(@PathVariable("id") String id, Model model){
        Product product =  productService.findById(id);
        if (product.getImage() == null){
            log.warn("Producto con id {} no tiene imagen se usara default", id);
            model.addAttribute("imgDefault", "/images/default2.jpg");
        }
        model.addAttribute("prov", product);
        return getEntityName() + "/cardDetails";
    }

    @PostMapping("/")
    public String saveEntity(@Valid @ModelAttribute("entity") Product entity,
                             BindingResult result,
                             RedirectAttributes flash) {
        log.info("guardar: {}", entity);
        flash.addFlashAttribute(getEntityName(), entity);
        if (result.hasErrors()) {
            errorsValidation(result, flash, entity);
            return "redirect:/" + getEntityName() + "/";
        }

        try {
            getService().save(entity);
            flash.addFlashAttribute("success", getEntityName() + " guardado con éxito");
        } catch (Exception e) {
            log.error("Error al guardar: {}", e.getMessage());
            errorsSave(flash, entity, e);
        }
        return "redirect:/" + getEntityName() + "/";
    }

    @GetMapping("edit/{id}")
    public String editProduct(@PathVariable("id") String id, Model model){
        log.info("Editar Product {}", id);
        Product product =  productService.findById(id);
        if (product.getImage() == null){
            log.warn("Product con id {} no tiene imagen se usara default", id);
            model.addAttribute("imgDefault", "/images/default2.jpg");
        }
        Map<String, List<Object>> selects = getSelects();
        for (Map.Entry<String, List<Object>> entry : selects.entrySet())
            model.addAttribute(entry.getKey(), entry.getValue());
        model.addAttribute("prod", product);
        model.addAttribute("contenido", "Editar product: " + product.getName());
        return getEntityName() + "/editFull";
    }

    @PostMapping("edit")
    public String saveEntity(@Valid @ModelAttribute("prod") Product product,
                             BindingResult result,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             RedirectAttributes flash) {
        log.info("Guardar: {}", product);

        flash.addFlashAttribute("prod", product);
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(e -> {
                String fieldNameError = e.getField().replace(".", "") + "Error";
                flash.addFlashAttribute(fieldNameError, e.getDefaultMessage());
            });
            return "redirect:/" + getEntityName() + "/edit/" + product.getId();
        }
        Boolean r = productService.updateFull(product, imageFile);
        log.warn("Respuesta actualizar: {}", r);
        return "redirect:/" + getEntityName() + "/details/" + product.getId();
    }

    @GetMapping("eliminar/{id}")
    public String deleteEntity(@PathVariable("id") String id, RedirectAttributes flash) {
        log.info("eliminar: {}", id);
        try {
            getService().delete(id);
            flash.addFlashAttribute("success", getEntityName() + " eliminado exitosamente");
        } catch (Exception e) {
            log.error("No se puede eliminar {}", id);
            flash.addFlashAttribute("error", "Error al Eliminar.");
        }
        return "redirect:/" + getEntityName() + "/";
    }

    private void errorsValidation(BindingResult result, RedirectAttributes flash, Product entity) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        result.getFieldErrors().forEach(error -> {
            String fieldNameError = error.getField() + "Error";
            flash.addFlashAttribute(fieldNameError, error.getDefaultMessage());
        });
        flash.addFlashAttribute(getEntityName(), entity);
    }

    private void errorsSave(RedirectAttributes flash, Product entity, Exception e) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        flash.addFlashAttribute(getEntityName(), entity);
        flash.addFlashAttribute("nameError", e.getMessage());
    }

}
