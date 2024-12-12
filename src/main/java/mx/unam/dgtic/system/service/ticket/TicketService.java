package mx.unam.dgtic.system.service.ticket;

import mx.unam.dgtic.system.entity.ProductsTicket;
import mx.unam.dgtic.system.entity.Ticket;
import mx.unam.dgtic.system.service.BaseService;

import java.util.List;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 10/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

public interface TicketService  extends BaseService<Ticket> {

    Boolean saveTicket(String json);

    List<ProductsTicket> getTicket(String id);
}
