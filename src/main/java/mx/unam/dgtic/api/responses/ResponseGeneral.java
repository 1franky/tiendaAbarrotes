package mx.unam.dgtic.api.responses;

import lombok.Data;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 21/11/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Data
public class ResponseGeneral {
    private Integer status;
    private String message;
    private Object data;
}