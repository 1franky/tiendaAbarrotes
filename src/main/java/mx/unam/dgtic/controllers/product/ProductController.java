package mx.unam.dgtic.controllers.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.controllers.BaseController;
import mx.unam.dgtic.entity.Product;
import mx.unam.dgtic.service.BaseService;
import mx.unam.dgtic.service.product.ProductService;
import mx.unam.dgtic.utils.RenderPagina;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 22/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Controller
@RequestMapping("product")
@RequiredArgsConstructor
@Log4j2
public class ProductController extends BaseController<Product> {

    private final ProductService productService;


    @Value("${app.upload.dir}")
    private String UPLOAD_DIR;

    @Override
    protected BaseService<Product> getService() {
        return productService;
    }

    @Override
    protected String getEntityName() {
        return "product";
    }

    @Override
    protected String getViewName() {
        return "productos";
    }

    @Override
    protected Map<String, List<Object>> getSelects() {
        return Map.of();
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

        model.addAttribute(getViewName(), products);
        model.addAttribute(getEntityName(), product);
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
        log.info("image product {}", product.getImage().getPathImage());
        model.addAttribute("prov", product);
        return getEntityName() + "/cardDetails";
    }

}
