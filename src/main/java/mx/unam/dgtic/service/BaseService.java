package mx.unam.dgtic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/10/24
 * @project proyectoFinal
 * Descripción: [...]
 */

public interface BaseService<T> {
    Page<T> findAll(Pageable pageable);
    Page<T> searchByAllColumns(String search, Pageable pageable);
    T findById(String id);
    void save(T entity)  throws Exception;
    void delete(String id)  throws Exception;
    T getEmpty();
}
