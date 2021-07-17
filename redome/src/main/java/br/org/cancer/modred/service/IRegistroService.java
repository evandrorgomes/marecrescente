package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.doador.RegistroDTO;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.service.custom.IService;

/**
 * Inteface de negócios da classe de Registro.
 * @author bruno.sousa
 *
 */
public interface IRegistroService extends IService<Registro, Long> {
	

	/**
	 * Lista de registros ordenados.
	 * @return lista de registros.
	 */
	List<Registro> obterTodosRegistrosOrdenados();
	
	/**
	 * Obtém listagem de registros internacionais.
	 * @return listagem de registros internacionais.
	 */
	List<Registro> obterTodosRegistrosInternacionais();
	
	
	/**
	 * Obtém o registro por sigla.
	 * 
	 * @param sigla - sigla do registro
	 * @return RegistroDto caso exista.
	 */
	RegistroDTO obterRegistroPorSigla(String sigla);

	
	/**
	 * Obtém o registro por donPool.
	 * 
	 * @param donPool - donPool do registro
	 * @return Registro caso exista.
	 */
	Registro obterRegistroPorDonPool(Long donPool);
	
}
