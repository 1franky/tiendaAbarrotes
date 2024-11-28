package mx.unam.dgtic.system.controllers.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.controllers.BaseController;
import mx.unam.dgtic.system.entity.Category;
import mx.unam.dgtic.system.service.BaseService;
import mx.unam.dgtic.system.service.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("category")
@RequiredArgsConstructor
@Log4j2
public class CategoryController extends BaseController<Category> {

    private final CategoryService categoryService;

    @Override
    protected BaseService<Category> getService() {
        return categoryService;
    }

    @Override
    protected String getEntityName() {
        return "category";
    }

    @Override
    protected String getViewName() {
        return "categories";
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    @Override
    protected Category cleanObject(Category object) {
        return object;
    }

    @Override
    protected Map<String, List<Object>> getSelects() {
        return Map.of();
    }

}
