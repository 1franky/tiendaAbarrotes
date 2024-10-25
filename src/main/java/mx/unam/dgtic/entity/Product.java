package mx.unam.dgtic.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

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
@NamedQueries({
        @NamedQuery(
                name = "Product.findByPriceRange",
                query = "SELECT p FROM Product p " +
                        "WHERE p.precioVenta BETWEEN :minPrice " +
                        "AND :maxPrice"
        ),
        @NamedQuery(
                name = "Product.findActiveByCategory",
                query = "SELECT p FROM Product p " +
                        "WHERE p.activo = true " +
                        "AND p.category.name = :categoryName"
        )
})

/*
<!--    <div class="container mt-4">-->
<!--        <div class="card">-->
<!--            <div class="row g-0">-->
<!--                &lt;!&ndash; Imagen &ndash;&gt;-->
<!--                <div class="col-md-4" th:if="${prov.image != null}">-->
<!--                    <img th:src="@{'/imagenes/'+${prov.image.pathImage}}" class="img-fluid rounded-start" alt="Imagen del proveedor">-->
<!--                </div>-->
<!--                <div class="col-md-4" th:if="${prov.image == null}">-->
<!--                    <img th:src="@{'/imagenes/'+${imgDefault}}" class="img-fluid rounded-start" alt="Imagen del proveedor">-->
<!--                </div>-->

<!--                <div class="col-md-8">-->
<!--                    <div class="card-body">-->
<!--                        &lt;!&ndash; Información del product &ndash;&gt;-->
<!--                        <h2 class="card-title" th:text="${prov.name}"></h2>-->
<!--                        <p class="card-text">-->
<!--                            <strong>UUID:</strong> <span th:text="${prov.id}"></span><br>-->
<!--                            <strong>Creado:</strong> <span th:text="${#temporals.format(prov.createdAt, 'dd MMMM yyyy HH:mm')}"></span><br>-->
<!--                            <strong>Actualizado:</strong> <span th:text="${#temporals.format(prov.updatedAt, 'dd MMMM yyyy HH:mm')}"></span><br>-->
<!--                            <strong>Descripción:</strong> <span th:text="${prov.description}"></span><br>-->
<!--                            <strong>Precio Venta:</strong> <span th:text="${prov.precioVenta}"></span><br>-->
<!--                            <strong>Precio Proveedor:</strong> <span th:text="${prov.precioProveedor}"></span><br>-->
<!--                            <strong>Existencia:</strong> <span th:text="${prov.existencia}"></span><br>-->
<!--                            <strong>Activo:</strong> <span th:text="${prov.activo ? 'Activo' : 'Inactivo'}"></span><br>-->
<!--                        </p>-->

<!--                        &lt;!&ndash; Información del proveedor &ndash;&gt;-->
<!--                        <p class="card-text" th:if="${prov.proveedor != null}">-->
<!--                            <strong>Proveedor:</strong> <span th:text="${prov.proveedor.name}"></span><br>-->
<!--                        </p>-->

<!--                        &lt;!&ndash; Información de la categoria &ndash;&gt;-->
<!--                        <p class="card-text" th:if="${prov.category != null}">-->
<!--                            <strong>Categoría:</strong> <span th:text="${prov.category.name}"></span><br>-->
<!--                        </p>-->

<!--                        &lt;!&ndash; Botones &ndash;&gt;-->
<!--                        <div class="d-flex justify-content-between mt-4">-->
<!--                            <a th:href="@{/product/edit/{id}(id=${prov.id})}" class="btn btn-warning disabled">Editar</a>-->
<!--                            <button type="button" class="btn btn-danger" th:attr="data-id=${prov.id}"-->
<!--                                    data-bs-toggle="modal" data-bs-target="#modaEliminar"-->
<!--                                    onclick="setModaId(this.getAttribute('data-id'))">-->
<!--                                Eliminar <i class="fas fa-trash-alt" aria-hidden="true"></i>-->
<!--                            </button>-->
<!--                            <a th:href="@{/product/}" class="btn btn-secondary">Regresar</a>-->
<!--                        </div>-->
<!--                    </div>-->
<!--                </div>-->


<!--            </div>-->

<!--        </div>-->
<!--    </div>-->
 */
public class Product {
    @Id
    @Column(name = "id", nullable = false, length = 40)
    private String id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "precioVenta")
    private Float precioVenta;

    @Column(name = "precioProveedor")
    private Float precioProveedor;

    @Column(name = "existencia")
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
    private Image image;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
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
}