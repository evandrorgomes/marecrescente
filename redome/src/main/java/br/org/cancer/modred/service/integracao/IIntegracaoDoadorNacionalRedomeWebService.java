package br.org.cancer.modred.service.integracao;

import java.util.List;

import br.org.cancer.modred.controller.dto.doador.DoadorNacionalDTO;
import br.org.cancer.modred.controller.dto.doador.MensagemErroIntegracao;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe DoadorNacional.
 * 
 * 
 * @author ergomes
 */
public interface IIntegracaoDoadorNacionalRedomeWebService extends IService<DoadorNacional, Long> {

	List<MensagemErroIntegracao> atualizarDadosIntegracaoDoadorNacional(List<DoadorNacionalDTO> doadores);
	
}