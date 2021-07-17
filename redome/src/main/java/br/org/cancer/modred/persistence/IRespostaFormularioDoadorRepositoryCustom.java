package br.org.cancer.modred.persistence;

import br.org.cancer.modred.model.RespostaFormularioDoador;

/**
 * Interface custom para entidade RespostaFormularioDoador.
 * 
 * @author bruno.sousa
 *
 */
public interface IRespostaFormularioDoadorRepositoryCustom {
	
	RespostaFormularioDoador findFirstByformularioDoadorPedidoContatoIdAndDoadorIdAndTokenOrderByFormularioDoadorDataRespostaDesc(Long idPedidoContato, Long idDoador, String token);
	
	RespostaFormularioDoador findFirstByformularioDoadorPedidoWorkupIdAndDoadorIdAndTokenOrderByFormularioDoadorDataRespostaDesc(Long idPedidoWorkup, Long idDoador, String token);
}
