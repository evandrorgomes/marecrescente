package br.org.cancer.modred.authorization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.cancer.modred.authorization.restriction.IRestricaoCustomizada;
import br.org.cancer.modred.authorization.restriction.PermissaoPerfilCentro;
import br.org.cancer.modred.authorization.restriction.PermissaoPerfilLaboratorio;
import br.org.cancer.modred.authorization.restriction.PermissaoPerfilMedicoAvaliador;
import br.org.cancer.modred.authorization.restriction.PermissaoPerfilMedicoAvaliadorParaTransferencia;
import br.org.cancer.modred.authorization.restriction.PermissaoPerfilMedicoResponsavel;
import br.org.cancer.modred.authorization.restriction.PermissaoPerfilMedicoTransplantador;
import br.org.cancer.modred.authorization.restriction.PermissaoPerfilMedicoTransplantadorCentroTransplante;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.persistence.IGenericRepository;

/**
 * Classe que verifica se a restrição de uma permissão foi atendida.
 * 
 * @author Cintia Oliveira
 *
 */
public class VerificarPermissaoAcesso {

	private IGenericRepository genericRepository;

	@SuppressWarnings("rawtypes")
	private Map<Long, List<IRestricaoCustomizada>> restricoes;

	/**
	 * Construtor que recebe uma referência para acesso ao repositórios.
	 * 
	 * @param genericRepository repositório genérico para acesso aos demais, sob demanda. FIXME: Deveria ser usado como injeção de
	 * dependência.
	 */
	@SuppressWarnings("rawtypes")
	public VerificarPermissaoAcesso(IGenericRepository genericRepository) {
		restricoes = new HashMap<Long, List<IRestricaoCustomizada>>();
		this.genericRepository = genericRepository;

		add(Perfis.MEDICO.getId(), Arrays.asList(new PermissaoPerfilMedicoResponsavel(genericRepository)));		
		add(Perfis.MEDICO_AVALIADOR.getId(), Arrays.asList(new PermissaoPerfilMedicoAvaliador(genericRepository),
				new PermissaoPerfilMedicoAvaliadorParaTransferencia(genericRepository)));
		add(Perfis.CADASTRADOR_RESULTADO_WORKUP.getId(), Arrays.asList(new PermissaoPerfilCentro(genericRepository)));
		add(Perfis.LABORATORIO.getId(), Arrays.asList(new PermissaoPerfilLaboratorio(genericRepository)));
		add(Perfis.MEDICO_TRANSPLANTADOR.getId(), Arrays.asList(new PermissaoPerfilMedicoTransplantador(genericRepository),
				new PermissaoPerfilMedicoTransplantadorCentroTransplante(genericRepository)));
		
	}

	/**
	 * Adiciona uma verificação de restrição para conceder ou não autorização ao recurso.
	 * 
	 * @param idPerfil id do Perfil
	 * @param lista de restrições Classe com a validação customizada.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	protected void add(Long idPerfil, List<IRestricaoCustomizada> restricao) {
		restricoes.put(idPerfil, restricao);
	}

	/**
	 * Retorna a verificação customizada associada ao recurso.
	 * 
	 * @param idPerfil id do Perfil
	 * @return lista de restrições customizada para o perfil
	 */
	@SuppressWarnings("rawtypes")
	public List<IRestricaoCustomizada> get(Long idPerfil) {
		return restricoes.get(idPerfil);
	}

	/**
	 * Retorna o repositorio generico.
	 * 
	 * @return genericRepository
	 */
	public IGenericRepository getGenericRepository() {
		return genericRepository;
	}

}
