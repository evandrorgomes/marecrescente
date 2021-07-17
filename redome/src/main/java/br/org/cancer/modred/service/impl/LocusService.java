package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.persistence.ILocusExameRepository;
import br.org.cancer.modred.persistence.ILocusRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ILocusService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de serviço para a Entidade Locus.
 * 
 * @author Filipe Paes
 */
@Service
@Transactional
public class LocusService extends AbstractService<Locus, String> implements ILocusService {

	@Autowired
	private ILocusRepository locusRepository;

	@Autowired
	private ILocusExameRepository locusExameRepository;


	/**
	 * Método para buscar a lista de locus na base.
	 * 
	 * @return List<Locus> listagem de locus.
	 */
	@Override
	public List<Locus> listarLocus() {
		return locusRepository.findByOrderByOrdemAsc();
	}

	/**
	 * Método para validar os locus contra o domínio.
	 * 
	 * @param listaLocusParaComparar
	 * @param campos
	 * @author Fillipe Queiroz
	 */
	@Override
	public void validarSeLocusSaoValidosPorCodigo(List<LocusExame> listaLocusParaComparar,
			List<CampoMensagem> campos) {
		listaLocusParaComparar.stream().forEach(locusParaComparar -> this.compararLocusPorCodigo(
				locusParaComparar.getId().getLocus(), campos));
	}

	/**
	 * Método para recuperar um LocusExame através do id do exame o grupo alélico.
	 * 
	 * @param exameId id do exame
	 * @param grupoAlelico grupo alelico
	 * @param primeiroAlelo primeiro alelo
	 * @param segundoAlelo segundo alelo
	 * @return locus exame
	 */
	@Override
	public LocusExame obterLocusExamePorExameIdGrupoAlelicoAlelos(Long exameId, String grupoAlelico, String primeiroAlelo,
			String segundoAlelo) {
		return locusExameRepository
				.findByExameIdAndGrupoAlelicoAndAlelos(
						exameId, grupoAlelico, primeiroAlelo, segundoAlelo);
	}

	/**
	 * Compara o locus informado com a lista de locus no dominio.
	 * 
	 * @param locusParaComparar
	 * @param campos
	 * @author Fillipe Queiroz
	 */
	private void compararLocusPorCodigo(Locus locusParaComparar, List<CampoMensagem> campos) {
		List<Locus> locusExistentes = listarLocus();
		boolean existeLocus = locusExistentes.stream().anyMatch(locus -> locus.getCodigo().equals(
				locusParaComparar.getCodigo()));
		if (!existeLocus) {
			campos.add(new CampoMensagem("exame",
					AppUtil.getMensagem(messageSource, "locus.nao.existente")));
		}
	}

	@Override
	public List<LocusExame> listarLocusExames(Long exameId) {
		return locusExameRepository.listarLocusExames(exameId);
	}

	@Override
	public void delete(List<LocusExame> locusExames) {
		locusExameRepository.deleteAll(locusExames);
	}

	/* (non-Javadoc)
	 * @see br.org.cancer.modred.service.impl.AbstractService#getRepository()
	 */
	@Override
	public IRepository<Locus, String> getRepository() {
		return this.locusRepository;
	}

}
