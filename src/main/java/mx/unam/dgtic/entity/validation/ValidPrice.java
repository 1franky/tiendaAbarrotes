package mx.unam.dgtic.entity.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 28/10/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Constraint(validatedBy = ValidPriceValidator.class) // Vincula con el validador
@Target(ElementType.TYPE) // Aplica a clases (entidades)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPrice {
    String message() default "El precio del proveedor no puede ser mayor que el precio de venta";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
