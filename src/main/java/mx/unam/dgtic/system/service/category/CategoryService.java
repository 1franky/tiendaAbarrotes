package mx.unam.dgtic.system.service.category;

import mx.unam.dgtic.system.entity.Category;
import mx.unam.dgtic.system.service.BaseService;

import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

public interface CategoryService extends BaseService<Category> {
    List<Category> getCategories();
    List<Category> searchByAllColumns(String search);
}
