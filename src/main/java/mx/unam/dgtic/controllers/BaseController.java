package mx.unam.dgtic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.api.responses.ResponseGeneral;
import mx.unam.dgtic.rest.ResponseApiPageable;
import mx.unam.dgtic.service.BaseService;
import mx.unam.dgtic.utils.RenderPagina;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Log4j2
public abstract class BaseController<T> {

    @Value(value = "${app.api.url}")
    private String URLBASE;

    protected abstract BaseService<T> getService();

    protected abstract String getEntityName();

    protected abstract String getViewName();

    protected abstract Class<T> getEntityClass();

    protected abstract T cleanObject (T object);

    protected abstract Map<String, List<Object>> getSelects();

    @GetMapping("/")
    public String listAll(@RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "size", defaultValue = "4") int size,
                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "search", required = false) String search,
                          Model model) {

        T entity;
        if (model.containsAttribute(getEntityName())){
            log.info("se encontro {} -> {}",getEntityName(), model.getAttribute(getEntityName()));
            entity = (T) model.getAttribute(getEntityName());
        }else {
            entity = getService().getEmpty();
        }

        Map<String, List<Object>> selects = getSelects();
        for (Map.Entry<String, List<Object>> entry : selects.entrySet())
            model.addAttribute(entry.getKey(), entry.getValue());

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<T> entities = null;
        String r, url;
        RestTemplate restTemplate = new RestTemplate();
        if (search != null && !search.isEmpty()) {
            url = "%s/%s/search?search=%s&page%d&size=%d&sort=%s&direction=%s".formatted(URLBASE, getEntityName(), search, page, size, sort, direction);
        } else {
            url = "%s/%s/?page=%d&size=%d&sort=%s&direction=%s".formatted(URLBASE, getEntityName(), page, size, sort, direction);
        }

        log.info("url get: {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        entities = getEntities(response, entities, pageable);

        RenderPagina<T> renderPagina = new RenderPagina<>("/" + getEntityName() + "/", entities, size);

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
    public String saveEntity(@Valid @ModelAttribute("entity") T entity,
                             BindingResult result,
                             RedirectAttributes flash) {
        log.info("guardar: {}", entity);
        flash.addFlashAttribute(getEntityName(), entity);
        if (result.hasErrors()) {
            errorsValidation(result, flash, entity);
            return "redirect:/" + getEntityName() + "/";
        }

        try {
            String url = "%s/%s/".formatted(URLBASE, getEntityName());
            log.info("url post: {}", url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(entity.toString(), headers);
            ResponseEntity<ResponseGeneral> response = restTemplate.postForEntity(url, request, ResponseGeneral.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Guardado exitosamente");
            }
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
            String url = "%s/%s/%s".formatted(URLBASE, getEntityName(), id);
            log.info("url delete: {}", url);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ResponseGeneral> response = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    new HttpEntity<>(new HttpHeaders()),
                    ResponseGeneral.class
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                flash.addFlashAttribute("success", getEntityName() + Objects.requireNonNull(response.getBody()).getMessage());
            } else {
                flash.addFlashAttribute("error", Objects.requireNonNull(response.getBody()).getMessage());
            }
        } catch (Exception e) {
            log.error("No se puede eliminar {}", id);
            flash.addFlashAttribute("error", "Error al Eliminar.");
        }
        return "redirect:/" + getEntityName() + "/";
    }

    protected void errorsValidation(BindingResult result, RedirectAttributes flash, T entity) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        result.getFieldErrors().forEach(error -> {
            String fieldNameError = error.getField() + "Error";
            flash.addFlashAttribute(fieldNameError, error.getDefaultMessage());
//            log.error("Campo: {} -> error: {}", fieldNameError, error.getDefaultMessage());
        });
        flash.addFlashAttribute(getEntityName(), entity);
    }

    protected void errorsSave(RedirectAttributes flash, T entity, Exception e) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        flash.addFlashAttribute(getEntityName(), entity);
        flash.addFlashAttribute("nameError", e.getMessage());
    }

    private Page<T> getEntities(ResponseEntity<String> response, Page<T> entities, Pageable pageable) {
        if (response.getStatusCode().is2xxSuccessful()) {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            try {
                ResponseApiPageable<T> responseApiPageable = objectMapper.readValue(response.getBody(), ResponseApiPageable.class);
                Pageable p1 = PageRequest.of(
                        responseApiPageable.getPageable().getPageNumber(),  // Número de página
                        responseApiPageable.getPageable().getPageSize()    // Tamaño de página
                );
                List<T> listContent = responseApiPageable.getContent()
                        .stream()
                        .map(t -> objectMapper.convertValue(t, getEntityClass()))
                        .collect(Collectors.toList());
                entities = new PageImpl<>(listContent, p1, responseApiPageable.getTotalElements());
            }catch (Exception e) {
                log.error("Error al deserializar el JSON: {}", e.getMessage());
            }

        }
        if (entities == null) {
            entities = getService().findAll(pageable);
        }
        return entities;
    }



}