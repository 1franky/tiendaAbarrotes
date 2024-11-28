package mx.unam.dgtic.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import mx.unam.dgtic.system.entity.Image;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Users")
public class User {

    @Id
    @Size(max = 40)
    @Column(name = "id", nullable = false, length = 40)
    private String id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @ColumnDefault("1")
    @Column(name = "isActive")
    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles = new LinkedHashSet<>();


//    @Override
//    public String toString() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        mapper.registerModule(new JavaTimeModule());
//        try {
//            return mapper.writeValueAsString(this);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error al convertir el objeto Product a JSON " + e.getMessage());
//        }
//    }
}