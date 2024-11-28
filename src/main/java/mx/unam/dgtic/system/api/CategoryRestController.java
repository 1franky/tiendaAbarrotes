package mx.unam.dgtic.system.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.api.responses.ResponseGeneral;
import mx.unam.dgtic.system.entity.Category;
import mx.unam.dgtic.system.service.category.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mx.unam.dgtic.system.api.Constants.*;

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
@Tag(name = "Categorías", description = "Gestión de categorías.")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping(path = "/{cadena}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Category>> findCategories(@PathVariable String cadena) {
        log.info("Buscando categorias de {}", cadena);
        List<Category> categories = categoryService.searchByAllColumns(cadena);
        return ResponseEntity.ok(categories);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Obtener categorías paginadas",
            description = "Obtiene una lista de categorías en formato paginado.",
            parameters = {
                    @Parameter(name = "search", description = "Cadena de búsqueda.", example = "lacteos"),
                    @Parameter(name = "page", description = "Número de la página (inicia en 0).", example = "0"),
                    @Parameter(name = "size", description = "Cantidad de elementos por página.", example = "3"),
                    @Parameter(name = "sort", description = "Criterios de ordenamiento. [campo]", example = "id"),
                    @Parameter(name = "direction", description = "Criterios de ordenamiento. [ASC/DESC]", example = "ASC")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de categorías obtenida con éxito.",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = CATEGORYLIST_200))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor.",
                            content = @Content
                    )
            }
    )
    public @ResponseBody ResponseEntity<Page<Category>> findCategoriesSearch(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "4") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        log.info("Buscando categorias por campos page: {}, size: {}, sort: {}, direccion: {} search: {}", page, size, sort, direction, search != null ? search : "null");
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Category> categories = categoryService.searchByAllColumns(search, pageable);
        return ResponseEntity.ok(categories);
    }


    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Obtener categorías paginadas",
            description = "Obtiene una lista de categorías en formato paginado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de categorías obtenida con éxito.",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = CATEGORYLIST_200))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor.",
                            content = @Content
                    )
            }
    )
    public @ResponseBody ResponseEntity<Page<Category>> findAllCategories(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "4") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        log.info("Buscando todas las categorias");
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Category> categories = categoryService.findAll(pageable);
        return ResponseEntity.ok(categories);
    }


    @PostMapping("/")
    @Operation(
            summary = "Guardar una nueva Categoria",
            description = "Recibe una categoria en formato JSON, la valida y la guarda en la base de datos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Categoria creada con éxito.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = CATEGORYCREATED_201))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errores de validación",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = CATEGORYCREATED_400))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error al guardar la entidad",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = CATEGORYCREATED_500))
            )
    })
    public ResponseEntity<ResponseGeneral> saveEntity(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Category a guardar",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Object.class),
                    examples = @ExampleObject(
                            name = "Ejemplo de entrada",
                            value = CATEGORYRESQUEST
                    )
            )
    ) @Valid @RequestBody Category category, BindingResult result) {
        log.info("Intentando guardar la entidad: {}", category);

        ResponseGeneral responseGeneral = new ResponseGeneral();
        // Validación de errores
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            log.warn("Errores de validación: {}", errors);
            responseGeneral.setStatus(HttpStatus.BAD_REQUEST.value());
            responseGeneral.setMessage("Error en datos.");
            responseGeneral.setData(errors);
            return ResponseEntity.badRequest().body(responseGeneral);
        }

        try {
            categoryService.save(category);
            log.info("Entidad guardada con éxito: {}", category);
            responseGeneral.setStatus(HttpStatus.CREATED.value());
            responseGeneral.setMessage("Created");
            responseGeneral.setData(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseGeneral);
        } catch (Exception e) {
            log.error("Error al guardar la entidad: {}", e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No se pudo guardar la entidad");
            errorResponse.put("details", e.getMessage());
            responseGeneral.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseGeneral.setMessage("Error al guardar la entidad");
            responseGeneral.setData(errorResponse);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseGeneral);
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una Categoria por ID",
            description = "Elimina una Categoria del sistema dado su ID. Devuelve un mensaje de éxito o un error en caso de fallo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entidad eliminada exitosamente",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = CATEGORYDELETED_200))
            ),
            @ApiResponse(responseCode = "404", description = "Error al eliminar la entidad",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = CATEGORYDELETED_404))
            ),
            @ApiResponse(responseCode = "500", description = "Entidad no encontrada",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = CATEGORYDELETED_500))
            )
    })
    public ResponseEntity<ResponseGeneral> deleteEntity(
            @PathVariable("id") String id) {
        log.info("eliminar categoria: {}", id);
        ResponseGeneral r = new ResponseGeneral();
        try {
            categoryService.delete(id);
            r.setMessage("Eliminado");
            r.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok(r);
        } catch (EntityNotFoundException e) {
            log.error("id no encontrada: {}", id);
            r.setMessage(e.getMessage());
            r.setStatus(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r);
        } catch (Exception e) {
            log.error("No se puede eliminar {}", e.getMessage());
            r.setMessage(e.getMessage());
            r.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(r);
        }
    }


}
