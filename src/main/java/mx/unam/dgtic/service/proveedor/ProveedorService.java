package mx.unam.dgtic.service.proveedor;


import mx.unam.dgtic.entity.Proveedor;
import mx.unam.dgtic.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

public interface ProveedorService extends BaseService<Proveedor> {

    List<Proveedor> getProveedores();

    Boolean updateFull(Proveedor proveedor, MultipartFile imageFile);

}
