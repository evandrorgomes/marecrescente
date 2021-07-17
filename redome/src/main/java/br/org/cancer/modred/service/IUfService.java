package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Uf;

/**
 * Interface para metodos de negocio de uf.
 * 
 * @author Filipe Paes
 *
 */
public interface IUfService {
	
	/**
	 * Lista as ufs.
	 * 
	 * @param List<Uf>
	 */
	List<Uf> listarUf();
	
	/**
	 * Método para obter UF por id.
	 * @param id
	 * @return Uf uf localizada por id
	 */
	Uf getUf(String id);

	
	/**
	 * Lista as ufs sem a UF Não Informado.
	 * 
	 * @param List<Uf>
	 */
	List<Uf> listarUfSemNaoInformado();
}
