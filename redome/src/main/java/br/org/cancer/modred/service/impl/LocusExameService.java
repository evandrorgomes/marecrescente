package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameCordaoInternacional;
import br.org.cancer.modred.model.ExameCordaoNacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.LocusExamePk;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.MotivoDivergenciaLocusExame;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.persistence.ILocusExameRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.ILocusExameService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;

/**
 * Classe de negócios de LocusExame.
 * @author Filipe Paes
 *
 */
@Service
public class LocusExameService extends AbstractLoggingService<LocusExame, LocusExamePk> implements ILocusExameService {

	@Autowired
	private ILocusExameRepository locusExameRepository;
		
	@Autowired
	private IBuscaService buscaService;
	
	@Autowired
	private IUsuarioService usuarioService;
		
	@Override
	public IRepository<LocusExame, LocusExamePk> getRepository() {
		return locusExameRepository;
	}
	
	@CreateLog(value=TipoLogEvolucao.DIVERGENCIA_LOCUS_EXAME_SOLUCIONADA, perfisExcluidos= {Perfis.MEDICO})
	@Override
	public void inativar(Long idBusca, Long exameId, String locus, MotivoDivergenciaLocusExame motivo) {
		final Busca busca = buscaService.obterBusca(idBusca);
		
		LocusExame locusExame = locusExameRepository.findByExameIdAndLocusCodigo(exameId, locus);
		if (locusExame == null) {
			throw new BusinessException("exame.nao.encontrado");
		}
		
		locusExame.setInativo(true);
		locusExame.setIdBusca(busca.getId());
		locusExame.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		locusExame.setDataMarcacao(LocalDateTime.now());
		locusExame.setMotivoDivergencia(motivo.getId());
		locusExame.setSelecionado(false);
		save(locusExame);		
	}

	@Override
	public Paciente obterPaciente(LocusExame entity) {
		if (entity.getIdBusca() != null) {
			final Busca busca = buscaService.obterBusca(entity.getIdBusca());
			return busca.getPaciente();
		}
		
		return null;
	}

	@Override
	public String[] obterParametros(LocusExame entity) {
		String[] retorno = {null, null, entity.getId().getLocus().getCodigo()};
		
		Exame exame = entity.getId().getExame();
		if (exame instanceof ExamePaciente) {
			retorno[0] = "paciente";
			retorno[1] = ((ExamePaciente) exame).getPaciente().getRmr()+"";
		}
		else if (exame instanceof ExameDoadorNacional || exame instanceof ExameDoadorInternacional) {
			retorno[0] = "doador";			
			if (exame instanceof ExameDoadorInternacional) {
				retorno[1] = ((ExameDoadorInternacional) exame).getDoador().getIdRegistro();
			}
			else {
				retorno[1] = ((ExameDoadorNacional) exame).getDoador().getDmr()+"";
			}			
		}
		else if (exame instanceof ExameCordaoNacional || exame instanceof ExameCordaoInternacional) {			
			retorno[0] = "cordão";
			if (exame instanceof ExameCordaoInternacional) {
				retorno[1] = ((ExameCordaoInternacional) exame).getDoador().getIdRegistro();
			}
			else {
				retorno[1] = ((ExameCordaoNacional) exame).getDoador().getIdBancoSangueCordao();
			}
			
		}
		
		return retorno;
	}
			
	@Override
	public void marcarSelecionado(Long idBusca, Long idExameSelecionado, String locus, MotivoDivergenciaLocusExame motivo) {
		final Busca busca = buscaService.obterBusca(idBusca);
		
		LocusExame locusExame = locusExameRepository.findByExameIdAndLocusCodigo(idExameSelecionado, locus);
		if (locusExame == null) {
			throw new BusinessException("exame.nao.encontrado");
		}
				
		locusExame.setIdBusca(busca.getId());
		locusExame.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		locusExame.setDataMarcacao(LocalDateTime.now());
		locusExame.setMotivoDivergencia(motivo.getId());
		locusExame.setPrimeiroAleloDivergente(false);
		locusExame.setSegundoAleloDivergente(false);
		locusExame.setSelecionado(true);
		save(locusExame);		
	}

}
