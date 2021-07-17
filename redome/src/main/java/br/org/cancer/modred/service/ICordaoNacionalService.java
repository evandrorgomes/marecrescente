package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.CordaoNacional;
import br.org.cancer.modred.vo.CordaoNacionalVo;

/**
 * Interface de acesso as funcionalidades envolvendo a classe CordaoNacional.
 * 
 * @author bruno.sousa
 */
public interface ICordaoNacionalService extends IDoadorService {

	/**
	 * Obtém o idBancoSangueCordao do cordao nacional, buscando pela pk do doador.
	 * 
	 * @param idDoador
	 * @return
	 */
	String obterIdBancoSangueCordaoFormatadoPorIdDoador(Long idDoador);
	
	/**
	 * Grava o cordão e gera o genotipo dele.
	 * @param cordao - cordão a ser persistido.
	 */
	void salvarCordao(CordaoNacional cordao);
	
	/**
	 * Passa um cordão vo para model.
	 * @param cordaoVo - vo para ser convertido.
	 * @return cordão model.
	 */
	CordaoNacional parseCordaoVoParaCordaoModel(CordaoNacionalVo cordaoVo);
	
	/**
	 * Importa cordoes nacionais vindos do BrasilCord.
	 * @param cordoesVo - lista de cordões enviados pelo JOB.
	 */
	void salvarCordoesImportados(List<CordaoNacionalVo> cordoesVo);
}
