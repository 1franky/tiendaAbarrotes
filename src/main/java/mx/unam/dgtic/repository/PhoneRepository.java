package mx.unam.dgtic.repository;

import mx.unam.dgtic.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, String> {
  }