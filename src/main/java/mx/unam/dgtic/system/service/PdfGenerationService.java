package mx.unam.dgtic.system.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import mx.unam.dgtic.system.entity.Product;
import mx.unam.dgtic.system.entity.ProductsTicket;
import mx.unam.dgtic.system.entity.Ticket;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 12/12/24
 * @project tiendaAbarrotes
 * Descripción: [...]
 */

@Slf4j
@Service
public class PdfGenerationService {

    public byte[] generateTicketPdf(List<ProductsTicket> productsTickets) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        Ticket ticket = productsTickets.get(0).getTicket();

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
        Paragraph title = new Paragraph("Detalles del Ticket", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        LocalDateTime fecha = LocalDateTime.ofInstant(ticket.getCreatedAt(), ZoneId.systemDefault());
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
        document.add(new Paragraph("Fecha: " + fecha.format(formatoFecha), headerFont));
        document.add(new Paragraph("Cliente: " + ticket.getClient().getName(), headerFont));
        document.add(Chunk.NEWLINE);

        // Tabla de Productos
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 1f, 2f, 2f});

        // Encabezados de la tabla
        String[] headers = {"Producto", "Cantidad", "Precio Unitario", "Subtotal"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }

        productsTickets.forEach(item -> {
            table.addCell(item.getProduct().getName());
            table.addCell(String.valueOf(item.getCantidad()));
            table.addCell(formatoMoneda.format(item.getPrecioVenta()));
            table.addCell(formatoMoneda.format(item.getPrecioVenta().multiply(BigDecimal.valueOf(item.getCantidad()))));
        });

        document.add(table);
        document.add(Chunk.NEWLINE);

        // Total
        Paragraph total = new Paragraph("Total: " + formatoMoneda.format(ticket.getTotal()),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.DARK_GRAY));
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(total);

        // Cerrar documento
        document.close();
        return baos.toByteArray();
    }

    public byte[] generateProductsBajosPdf(List<Product> products) throws DocumentException {

        Document document = new Document();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
        Paragraph title = new Paragraph("Reporte de inventario bajo", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        LocalDateTime fecha = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
        document.add(new Paragraph("Fecha: " + fecha.format(formatoFecha), headerFont));
//        document.add(new Paragraph("Cliente: " + ticket.getClient().getName(), headerFont));
        document.add(Chunk.NEWLINE);

        // Tabla de Productos
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3f, 3f, 2f, 1.5f, 1.5f, 1.5f, 2f, 2f});

        String[] headers = {"Id", "Nombre", "Descripción", "Precio Proveedor",  "Precio Venta", "Existencia", "Proveedor", "Categoría"};
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }

        products.forEach(product -> {
            table.addCell(product.getId());
            table.addCell(product.getName());
            table.addCell(product.getDescription());
            table.addCell(String.valueOf(product.getPrecioProveedor()));
            table.addCell(String.valueOf(product.getPrecioVenta()));
            table.addCell(String.valueOf(product.getExistencia()));
            table.addCell(product.getProveedor().getName());
            table.addCell(product.getCategory().getName());
        });

        document.add(table);
        document.add(Chunk.NEWLINE);

        // Cerrar documento
        document.close();
        return baos.toByteArray();
    }

    public byte[] generateReporteVentasPdf(List<Ticket> tickets, String titleFecha) throws DocumentException {

        log.info("Se inicia reporte de ventas pdf");

        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        document.open();

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
        Paragraph title = new Paragraph("Reporte de ventas " + titleFecha, titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        LocalDateTime fecha = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);
        document.add(new Paragraph("Fecha de reporte: " + fecha.format(formatoFecha), headerFont));
        document.add(Chunk.NEWLINE);

        for (Ticket ticket : tickets) {
            addTicketToDocument(document, ticket);
        }

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.close();
        return baos.toByteArray();
    }

    private void addTicketToDocument(Document document, Ticket ticket) throws DocumentException{

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.DARK_GRAY);

        LocalDateTime fecha = LocalDateTime.ofInstant(ticket.getFecha(), ZoneId.systemDefault());

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        PdfPCell headerCell = new PdfPCell(new Phrase("Fecha: " +  fecha.format(formatoFecha) + "\nCliente: " + ticket.getClient().getName(), headerFont));
        headerCell.setColspan(4);
        headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(headerCell);

//        table.addCell("Id");
        table.addCell(new Phrase("Producto", headerFont));
        table.addCell(new Phrase("Cantidad", headerFont));
        table.addCell(new Phrase("Precio de Venta ", headerFont));
        table.addCell(new Phrase("Subtotal", headerFont));

        ticket.getProductsTickets().forEach(pt -> {
            table.addCell(pt.getProduct().getName());
            table.addCell(String.valueOf(pt.getCantidad()));
            table.addCell(formatoMoneda.format(pt.getPrecioVenta()));
            table.addCell(formatoMoneda.format(pt.getPrecioVenta().multiply(BigDecimal.valueOf(pt.getCantidad()))));
        });

        PdfPCell totalCell = new PdfPCell(new Phrase(formatoMoneda.format(ticket.getTotal())));
        totalCell.setColspan(4);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalCell);

        PdfPTable separatorTable = new PdfPTable(1);
        separatorTable.setWidthPercentage(100);
        PdfPCell separador  = new PdfPCell();
        separador.setBackgroundColor(BaseColor.BLUE);
        separador.setFixedHeight(10);
        separador.setBorder(Rectangle.NO_BORDER);

        separatorTable.addCell(separador);

        document.add(table);

    }

}