package mx.unam.dgtic.rest;

import lombok.Data;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Data
public class Pageable {
    private int pageNumber;
    private int pageSize;
    private Sort sort;
    private int offset;
    private boolean unpaged;
    private boolean paged;

}