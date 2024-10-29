package mx.unam.dgtic.entity.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.entity.Product;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 28/10/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Log4j2
public class ValidPriceValidator implements ConstraintValidator<ValidPrice, Product> {

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if (product.getPrecioVenta() == null || product.getPrecioProveedor() == null) {
            return true; // Deja que otras validaciones se encarguen de los nulos
        }
        if (product.getPrecioProveedor().compareTo(product.getPrecioVenta()) > 0) {
            context.disableDefaultConstraintViolation();
            // Agregar un error personalizado al objeto completo
            context.buildConstraintViolationWithTemplate(
                    "El precio del proveedor no puede ser mayor que el precio de venta"
            ).addPropertyNode("precioProveedor").addConstraintViolation();
            return false; // Indica que la validación falló
        }

        return true;
    }
}
