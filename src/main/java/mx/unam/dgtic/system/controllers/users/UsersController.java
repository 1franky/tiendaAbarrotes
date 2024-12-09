package mx.unam.dgtic.system.controllers.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.auth.model.UserInfo;
import mx.unam.dgtic.auth.model.UserInfoRole;
import mx.unam.dgtic.system.service.user.UserService;
import mx.unam.dgtic.system.utils.RenderPagina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 03/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
@Log4j2
public class UsersController {

    private final UserService userService;

    private String getEntityName() {
        return "user";
    }

    private Map<String, List<Object>> getSelects(){
        Map<String, List<Object>> select = new HashMap<>();

        // Roles
        select.put("selectRole", new ArrayList<>());
        userService.getRoles().forEach(select.get("selectRole")::add);

        return select;
    }

    private String getViewName() {
        return "users";
    }

    @GetMapping(path = "/")
    public String listAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "4") int size,
                               @RequestParam(name = "sort", defaultValue = "useId") String sort,
                               @RequestParam(name = "direction", defaultValue = "asc") String direction,
                               @RequestParam(name = "search", required = false) String search,
                               Model model) {

        log.info("Obtiendo todos los usuarios y permisos");
        UserInfo entity;
        if (model.containsAttribute(getEntityName())){
            entity = (UserInfo) model.getAttribute(getEntityName());
        }else {
            entity = new UserInfo();
        }

        getSelects().forEach(model::addAttribute);

        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<UserInfo> entities;

        if (search != null && !search.isEmpty()) {
            entities = userService.searchByAllColumns(search, pageable);
        }else {
            entities = userService.findAll(pageable);
        }

        RenderPagina<UserInfo> renderPagina = new RenderPagina<>("/" + getEntityName() + "/", entities, size);

        model.addAttribute(getViewName(), entities);
        model.addAttribute(getEntityName(), entity);
        model.addAttribute("page", renderPagina);
        model.addAttribute("contenido", getEntityName() + "s");
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("invertDirection", "asc".equals(direction) ? "desc" : "asc");

        return "user/user";

    }


    @PostMapping(path = "/")
    public String saveEntity(
            @RequestParam(required = false, defaultValue = "0") Long useId,
            @RequestParam String useFirstName,
            @RequestParam String useLastName,
            @RequestParam String useEmail,
            @RequestParam String usePasswd,
            @RequestParam Boolean useIdStatus, // Radio button directamente como Integer
            @RequestParam(required = false) List<Long> useInfoRoles, // IDs de roles seleccionados
            Model model, RedirectAttributes flash) {

        UserInfo entity = new UserInfo();

        entity.setUseId(useId);
        entity.setUseFirstName(useFirstName);
        entity.setUseLastName(useLastName);
        entity.setUseEmail(useEmail);
        entity.setUsePasswd(usePasswd);
        if (useIdStatus)
            entity.setUseIdStatus(1);
        else
            entity.setUseIdStatus(0);

        Set<UserInfoRole> roles = new HashSet<>();
        for (Long roleId : useInfoRoles) {
            userService.getRoleById(roleId).ifPresent(roles::add);
        }
        entity.setUseInfoRoles(roles);

        log.info("guardar: {}", entity);
        flash.addFlashAttribute(getEntityName(), entity);

        try {
            userService.save(entity);
            log.info("Creado {} ", entity);
            flash.addFlashAttribute("success", getEntityName() + " guardado con éxito");
        } catch (Exception e) {
            log.error("Error al guardar: {}", e.getMessage());
            errorsSave(flash, entity, e);
        }
        return "redirect:/user/";

    }

    @GetMapping("eliminar/{id}")
    public String deleteEntity(@PathVariable("id") String id, RedirectAttributes flash) {
        log.info("eliminar: {}", id);
        try {
            UserInfo user = userService.findById(Long.valueOf(id));
            if (user.getUseIdStatus() == 0)
                user.setUseIdStatus(1);
            else
                user.setUseIdStatus(0);
            userService.save(user);
            flash.addFlashAttribute("success", getEntityName()  + " eliminado exitosamente");
        } catch (Exception e) {
            log.error("No se puede eliminar {}", id);
            flash.addFlashAttribute("error", "Error al Eliminar.");
        }
        return "redirect:/proveedor/";
    }


    private void errorsValidation(BindingResult result, RedirectAttributes flash, UserInfo entity) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        result.getFieldErrors().forEach(error -> {
            String fieldNameError = error.getField() + "Error";
            flash.addFlashAttribute(fieldNameError, error.getDefaultMessage());
//            log.error("Campo: {} -> error: {}", fieldNameError, error.getDefaultMessage());
        });
        flash.addFlashAttribute(getEntityName(), entity);
    }

    private void errorsSave(RedirectAttributes flash, UserInfo entity, Exception e) {
        flash.addFlashAttribute("errorModal", Boolean.TRUE);
        flash.addFlashAttribute(getEntityName(), entity);
        flash.addFlashAttribute("nameError", e.getMessage());
    }

}