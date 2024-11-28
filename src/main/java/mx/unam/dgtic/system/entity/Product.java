package mx.unam.dgtic.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import mx.unam.dgtic.system.entity.validation.ValidPrice;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Products")
@ValidPrice
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @Column(name = "id", nullable = false, length = 40)
    private String id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @Column(name = "precioVenta")
    @NotNull(message = "El precio de venta no puede ser nulo")
    @Positive(message = "El precio de venta debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio de venta debe tener máximo 2 decimales")
    private BigDecimal precioVenta;

    @Column(name = "precioProveedor")
    @NotNull(message = "El precio del proveedor no puede ser nulo")
    @Positive(message = "El precio del proveedor debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio del proveedor debe tener máximo 2 decimales")
    private BigDecimal precioProveedor;

    @Column(name = "existencia")
    @NotNull(message = "La existencia no puede ser nula")
    @Min(value = 0, message = "La existencia no puede ser negativa")
    private Integer existencia;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    @ToString.Exclude
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @ToString.Exclude
    @JsonIgnore
    private Image image;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @JsonIgnore
    private Set<ProductsTicket> productsTickets = new LinkedHashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir el objeto Product a JSON " + e.getMessage());
        }
    }

}