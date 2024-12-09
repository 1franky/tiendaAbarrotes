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
import mx.unam.dgtic.system.entity.Product;
import mx.unam.dgtic.system.service.product.ProductService;
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
import java.util.Map;

import static mx.unam.dgtic.system.api.Constants.*;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 21/11/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/api/product")
@Tag(name = "Products", description = "Gestión de Productos.")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Obtener products paginadas",
            description = "Obtiene una lista de products en formato paginado.",
            parameters = {
                    @Parameter(name = "search", description = "Cadena de búsqueda.", example = "leche"),
                    @Parameter(name = "page", description = "Número de la página (inicia en 0).", example = "0"),
                    @Parameter(name = "size", description = "Cantidad de elementos por página.", example = "3"),
                    @Parameter(name = "sort", description = "Criterios de ordenamiento. [campo]", example = "id"),
                    @Parameter(name = "direction", description = "Criterios de ordenamiento. [ASC/DESC]", example = "ASC")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de productos obtenida con éxito.",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = PRODUCTLIST_200))
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
    public @ResponseBody ResponseEntity<Page<Product>> findProductsSearch(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        log.info("Buscando productos por campos page: {}, size: {}, sort: {}, direccion: {} search: {}", page, size, sort, direction, search != null ? search : "null");
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Product> products = productService.searchByAllColumns(search, pageable);
        return ResponseEntity.ok(products);
    }


    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Obtener products paginadas",
            description = "Obtiene una lista de products en formato paginado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de productos obtenida con éxito.",
                            content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = PRODUCTLIST_200))
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
    public @ResponseBody ResponseEntity<Page<Product>> findAllProducts(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "4") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        log.info("Buscando todos las Products");
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Product> products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }


    @PostMapping("/")
    @Operation(
            summary = "Guardar una nuevo Producto",
            description = "Recibe un preoducto en formato JSON, la valida y la guarda en la base de datos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Producto creado con éxito.",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = PRODUCTCREATED_201))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Errores de validación",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = PRODUCTCREATED_400))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error al guardar la entidad",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = PRODUCTCREATED_500))
            )
    })
    public ResponseEntity<ResponseGeneral> saveEntity(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Producto a guardar",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Object.class),
                    examples = @ExampleObject(
                            name = "Ejemplo de entrada",
                            value = PRODUCTRESQUEST
                    )
            )
    ) @Valid @RequestBody Product product, BindingResult result) {
        log.info("Intentando guardar la entidad: {}", product);

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
            product = productService.save(product);
            log.info("Entidad guardada con éxito: {}", product);
            responseGeneral.setStatus(HttpStatus.CREATED.value());
            responseGeneral.setMessage("Created");
            responseGeneral.setData(product);
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
    @Operation(summary = "Eliminar una Producto por ID",
            description = "Elimina una Producto del sistema dado su ID. Devuelve un mensaje de éxito o un error en caso de fallo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entidad eliminada exitosamente",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = PRODUCTDELETED_200))
            ),
            @ApiResponse(responseCode = "404", description = "Error al eliminar la entidad",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = PRODUCTDELETED_404))
            ),
            @ApiResponse(responseCode = "500", description = "Entidad no encontrada",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Respuesta de ejemplo", value = PRODUCTDELETED_500))
            )
    })
    public ResponseEntity<ResponseGeneral> deleteEntity(
            @PathVariable("id") String id) {
        log.info("eliminar producto: {}", id);
        ResponseGeneral r = new ResponseGeneral();
        try {
            productService.delete(id);
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
