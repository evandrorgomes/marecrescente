package br.org.cancer.redome.workup.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ContagemCelulaDTO;
import br.org.cancer.redome.workup.model.ContagemCelula;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Contagem Celula.
 * @author ruan.agra
 *
 */
public interface IContagemCelulaService extends IService<ContagemCelula, Long> {

	ContagemCelula save(ContagemCelula entity);
	
	void finalizarContagemCelula(Long idPedidoWorkup, ContagemCelulaDTO contagemCelulaDTO) throws JsonProcessingException;

	void salvarContagemCelula(Long idPedidoWorkup, ContagemCelulaDTO contagemCelulaDTO) throws JsonProcessingException;

	ContagemCelulaDTO obterContagemCelulaDto(Long idPedidoWorkup);

	ContagemCelula criarPedidoContagemCelula(Long idPedidoWorkup);
	
}