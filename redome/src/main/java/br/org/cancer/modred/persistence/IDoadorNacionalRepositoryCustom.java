package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;

/**
 * Interface de persistência para controlar a paginação da busca de doador.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IDoadorNacionalRepositoryCustom {

	/**
	 * Método para buscar a lista de doadores pelos parametros enviados.
	 * 
	 * @param pageable - objeto de paginação
	 * @param dmr - identificador do doador
	 * @param nome - nome do doador
	 * @param cpf - cpf do doador
	 * @return lista Paginada de doadores
	 */
	List<ConsultaDoadorNacionalVo> listarDoadoresNacionaisVo(Pageable pageable, Long dmr, String nome, String cpf);

	List<DoadorNacional> listarDoadoresNacionais(Pageable pageable, Long dmr, String nome);
}
