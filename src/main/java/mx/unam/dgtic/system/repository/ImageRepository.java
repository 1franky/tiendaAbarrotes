package mx.unam.dgtic.system.repository;

import mx.unam.dgtic.system.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
  }