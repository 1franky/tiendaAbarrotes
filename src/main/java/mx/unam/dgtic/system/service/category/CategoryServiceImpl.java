package mx.unam.dgtic.system.service.category;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.unam.dgtic.system.entity.Category;
import mx.unam.dgtic.system.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> searchByAllColumns(String search, Pageable pageable) {
        return categoryRepository.searchByAllColumns(search, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Category save(Category category) throws Exception {
        if (category.getId() == null || category.getId().isBlank() ) {
            category.setId(UUID.randomUUID().toString());
            category.setCreatedAt(Instant.now());
        }
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            log.error("Ya existe Category con nombre: {}", category.getName());
            throw new Exception("Ya existe Category con nombre: " + category.getName());
        }
        category.setUpdatedAt(Instant.now());
        categoryRepository.save(category);
        return category;
    }

    @Override
    @Transactional
    public void delete(String id)  throws Exception {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
                () -> {
                    throw new EntityNotFoundException("No existe Category con id: " + id);
                }
        );
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> searchByAllColumns(String search) {
        return categoryRepository.searchByAllColumns(search);
    }

    @Override
    public Category getEmpty(){
        return new Category();
    }

}
