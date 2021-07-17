package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Cid;
import br.org.cancer.modred.model.Diagnostico;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.persistence.IDiagnosticoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ICidService;
import br.org.cancer.modred.service.IDiagnosticoService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe para de negócios para Evolucao.
 * 
 * @author Dev Team
 *
 */
@Service
@Transactional
public class DiagnosticoService extends AbstractLoggingService<Diagnostico, Long> implements IDiagnosticoService {

	@Autowired
	private ICidService cidService;
	
	@Autowired
	private IDiagnosticoRepository diagnosticoRepository;
	
	@Override
	public IRepository<Diagnostico, Long> getRepository() {
		return diagnosticoRepository;
	}
	

	@Autowired
	private MessageSource messageSource;

	@Override
	public void validar(Diagnostico diagnostico, List<CampoMensagem> campos) {
		if (diagnostico != null && diagnostico.getCid() != null && diagnostico.getCid()
				.getId() != null) {

			final Cid cid = cidService.findById(diagnostico.getCid().getId());

			if (cid == null) {
				campos.add(
						new CampoMensagem(
								"diagnostico.cid",
								messageSource.getMessage("cid.invalido", null, LocaleContextHolder
										.getLocale())));
			}
		}
	}
	
	private Diagnostico obterDiagnostico(Long rmr) {
		Diagnostico diagnostico = findById(rmr);
		if (diagnostico == null) {
			throw new BusinessException("erro.diagnostico.obter");
		}
		return diagnostico;
	}

	@CreateLog(value=TipoLogEvolucao.DIAGNOSTICO_PACIENTE_ALTERADO)
	@Override
	public void alterarDiagnostico(Long rmr, Diagnostico diagnostico) {
		Diagnostico diagnosticoRecuperado = obterDiagnostico(rmr);
		
		if (!diagnosticoRecuperado.getDataDiagnostico().equals(diagnostico.getDataDiagnostico()) || 
			!diagnosticoRecuperado.getCid().equals(diagnostico.getCid())) {
			
			diagnosticoRecuperado.setDataDiagnostico(diagnostico.getDataDiagnostico());
			diagnosticoRecuperado.setCid(diagnostico.getCid());
			
			save(diagnosticoRecuperado);			
		}
		
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(Diagnostico diagnostico) {
		List<CampoMensagem> campos = super.validateEntity(diagnostico);
		validar(diagnostico, campos);
		return campos;
	}

	@Override
	public Paciente obterPaciente(Diagnostico diagnostico) {
		return diagnostico.getPaciente();
	}

	@Override
	public String[] obterParametros(Diagnostico diagnostico) {
		final String[] params = {diagnostico.getPaciente().getRmr().toString()}; 
		return params;
	}
	
	
}
