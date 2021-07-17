package br.org.cancer.redome.workup.service;

import java.util.List;

import br.org.cancer.redome.workup.model.FonteCelula;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Fonte de Celula.
 * @author bruno.sousa
 *
 */
public interface IFonteCelulaService extends IService<FonteCelula, Long> {
	
	List<FonteCelula> findBySigla(String[] siglas);
	
	FonteCelula obterFonteCelula(Long id);
    
}
