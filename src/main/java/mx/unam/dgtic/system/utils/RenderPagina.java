package mx.unam.dgtic.system.utils;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class RenderPagina<T> {
	@Getter
    private String url;
	private Page<T> page;
	@Getter
    private int totalPaginas;
	private int elementosPorPagina;
	@Getter
    private int paginaActual;
	@Getter
    private List<PageItem> paginas;
	@Getter
	private Integer size;

	public RenderPagina(String url, Page<T> page) {		
		this.url = url;
		this.page = page;
		this.paginas=new ArrayList<PageItem>();
		elementosPorPagina=page.getSize();
		totalPaginas=page.getTotalPages();
		paginaActual=page.getNumber()+1;
		int desde,hasta;
		if(totalPaginas<=elementosPorPagina) {
			desde=1;
			hasta=totalPaginas;
		}else {
			if(paginaActual<=elementosPorPagina/2) {
				desde=1;
				hasta=elementosPorPagina;
			}else if(paginaActual>=totalPaginas-elementosPorPagina/2) {
				desde=totalPaginas-elementosPorPagina+1;
				hasta=elementosPorPagina;
			}else {
				desde=paginaActual-elementosPorPagina/2;
				hasta=elementosPorPagina;
			}
		}
		for(int i =0;i<hasta;i++) {
			paginas.add(new PageItem(desde+i, paginaActual==desde+i));
		}
	}

	public RenderPagina(String url, Page<T> page, Integer size) {
		this.url = url;
		this.page = page;
		this.size=size;
		this.paginas=new ArrayList<PageItem>();
		elementosPorPagina=page.getSize();
		totalPaginas=page.getTotalPages();
		paginaActual=page.getNumber()+1;
		int desde,hasta;
		if(totalPaginas<=elementosPorPagina) {
			desde=1;
			hasta=totalPaginas;
		}else {
			if(paginaActual<=elementosPorPagina/2) {
				desde=1;
				hasta=elementosPorPagina;
			}else if(paginaActual>=totalPaginas-elementosPorPagina/2) {
				desde=totalPaginas-elementosPorPagina+1;
				hasta=elementosPorPagina;
			}else {
				desde=paginaActual-elementosPorPagina/2;
				hasta=elementosPorPagina;
			}
		}
		for(int i =0;i<hasta;i++) {
			paginas.add(new PageItem(desde+i, paginaActual==desde+i));
		}
	}

    public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public boolean isHasNext() {
		return page.hasNext();
	}

	public boolean isHasPrevious() {
		return page.hasPrevious();
	}

}
