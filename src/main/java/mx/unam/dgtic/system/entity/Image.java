package mx.unam.dgtic.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Images")
public class Image {
    @Id
    @Column(name = "id", nullable = false, length = 40)
    private String id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "pathImage")
    private String pathImage;

    @OneToMany(mappedBy = "image")
    @ToString.Exclude
    @JsonIgnore
    private Set<Product> products = new LinkedHashSet<>();

    @OneToMany(mappedBy = "image")
    @ToString.Exclude
    @JsonIgnore
    private Set<Proveedor> proveedores = new LinkedHashSet<>();

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}