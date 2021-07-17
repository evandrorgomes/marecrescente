package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.FonteCelula;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negócios para Fonte de Celula.
 * @author bruno.sousa
 *
 */
public interface IFonteCelulaService extends IService<FonteCelula, Long> {
	
	List<FonteCelula> findBySigla(String[] siglas);
	
	FonteCelula obterFonteCelula(Long id);
    
}
