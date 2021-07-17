package br.org.cancer.modred.vo;

import java.util.ArrayList;
import java.util.List;

import br.org.cancer.modred.model.correio.Logradouro;

/**
 * Classe de conversão do LogradouroCorreio para CepCorreioVo.
 * 
 * @author bruno.sousa
 *
 */
public class CepCorreioTransformer {

	private static volatile CepCorreioTransformer INSTANCE;

	private List<Logradouro> listaToTransformer;
	private Logradouro entityToTransformer;

	/**
	 * Método estático que devolve uma instancia desse classe.
	 * 
	 * @return CepCorreioDTO
	 */
	private static CepCorreioTransformer instance() {
		if (INSTANCE == null) {
			synchronized (CepCorreioTransformer.class) {
				if (INSTANCE == null) {
					INSTANCE = new CepCorreioTransformer();
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * Cosntrutor padrão - Está como privado. Usar o método "instance" para criar a instancia.
	 */
	private CepCorreioTransformer() {}

	/**
	 * 
	 * @param logradouro
	 * @return CepCorreio
	 */
	private CepCorreioVO buildEntity(Logradouro logradouro) {
		if (logradouro == null) {
			return new CepCorreioVO();
		}
		CepCorreioVO cep = new CepCorreioVO();
		cep.setCep(logradouro.getCep());
		cep.setUf(logradouro.getBairroInicial().getLocalidade().getUnidadeFederativa().getSigla());
		cep.setLocalidade(logradouro.getBairroInicial().getLocalidade().getNome());
		cep.setBairro(logradouro.getBairroInicial().getNome());
		cep.setTipoLogradouro(logradouro.getTipoLogradouro().getNome());
		cep.setLogradouro(logradouro.getNome());
		cep.setCodigoIbge(logradouro.getBairroInicial().getLocalidade().getCodigoIbge());
		return cep;
	}

	/**
	 * @param lista
	 * @return IListTransformer<CepCorreio>
	 */
	public static IListTransformer<CepCorreioVO> transformar(List<Logradouro> lista) {
		instance().listaToTransformer = lista;
		return instance().new ListTransformer();
	}

	/**
	 * @param entity
	 * @return IEntityTransformer<CepCorreio>
	 */
	public static IEntityTransformer<CepCorreioVO> transformar(Logradouro entity) {
		instance().entityToTransformer = entity;
		return instance().new EntityTransformer();
	}

	/**
	 * Classe interna que transforma uma lista de LoradouroCorreio em lista de CepCorrreio.
	 * 
	 * @author bruno.sousa
	 *
	 */
	private class ListTransformer implements IListTransformer<CepCorreioVO> {

		@Override
		public List<CepCorreioVO> transformar() {
			List<CepCorreioVO> retorno = new ArrayList<CepCorreioVO>();
			for (Logradouro logradouro : listaToTransformer) {
				retorno.add(buildEntity(logradouro));
			}

			return retorno;
		}
	}

	/**
	 * Classe interna que transforma um LogradouroCorreio em CepCorreio.
	 * 
	 * @author bruno.sousa
	 *
	 */
	private class EntityTransformer implements IEntityTransformer<CepCorreioVO> {

		@Override
		public CepCorreioVO transformar() {
			return buildEntity(entityToTransformer);
		}
	}
}
