package mx.unam.dgtic.system.rest;

import lombok.Data;

import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 20/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Data
public class ResponseApiPageable<T> {
    private List<T> content;
    private Pageable pageable;
    private boolean last;
    private int totalPages;
    private int totalElements;
    private boolean first;
    private int size;
    private int number;
    private Sort sort;
    private int numberOfElements;
    private boolean empty;
}