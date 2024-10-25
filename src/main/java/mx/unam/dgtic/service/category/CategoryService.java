package mx.unam.dgtic.service.category;

import mx.unam.dgtic.entity.Category;
import mx.unam.dgtic.service.BaseService;

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
