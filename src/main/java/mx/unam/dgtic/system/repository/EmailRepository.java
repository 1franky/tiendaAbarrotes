package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, String> {
    List<Email> findByDescriptionContaining(String keyword);
}