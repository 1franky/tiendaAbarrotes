package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, String> {
  }