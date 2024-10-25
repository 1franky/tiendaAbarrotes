package mx.unam.dgtic.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.entity.Category;
import mx.unam.dgtic.service.category.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 19/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping(path = "/{cadena}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Category>> findCategories(@PathVariable String cadena) {
        log.info("Buscando categorias de {}", cadena);
        List<Category> categories = categoryService.searchByAllColumns(cadena);
        return ResponseEntity.ok(categories);
    }

}
