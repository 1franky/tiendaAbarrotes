package mx.unam.dgtic.controllers.proveedor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.controllers.BaseController;
import mx.unam.dgtic.entity.Category;
import mx.unam.dgtic.entity.Proveedor;
import mx.unam.dgtic.service.BaseService;
import mx.unam.dgtic.service.category.CategoryService;
import mx.unam.dgtic.service.proveedor.ProveedorService;
import org.springframework.beans.factory.annotation.Value;
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

@Controller
@RequestMapping("proveedor")
@RequiredArgsConstructor
@Log4j2
public class ProveedorController extends BaseController<Proveedor> {

    private final ProveedorService proveedorService;

    private final CategoryService categoryService;

    @Value("${app.upload.dir}")
    private String UPLOAD_DIR;

    @Override
    protected BaseService<Proveedor> getService() {
        return proveedorService;
    }

    @Override
    protected String getEntityName() {
        return "proveedor";
    }

    @Override
    protected String getViewName() {
        return "proveedores";
    }

    @Override
    protected Class<Proveedor> getEntityClass() {
        return Proveedor.class;
    }

    @Override
    protected Proveedor cleanObject(Proveedor object) {
        return object;
    }

    @Override
    protected Map<String, List<Object>> getSelects(){
        Map<String, List<Object>> select = new HashMap<>();
        select.put("selectCategory", new ArrayList<>());
        List<Category> categories = categoryService.getCategories();
        categories.forEach(category -> {
            select.get("selectCategory").add(category);
        });
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
        return getEntityName() + "/cardDetails";
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
        return getEntityName() + "/editFull";
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
            return "redirect:/" + getEntityName() + "/edit/" + proveedor.getId();
        }
        Boolean r = proveedorService.updateFull(proveedor, imageFile);
        log.warn("Respuesta actualizar: {}", r);
        return "redirect:/" + getEntityName() + "/details/" + proveedor.getId();
    }

}