	package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.domain.StatusExame;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.redomelib.exception.AleloException;
import br.org.cancer.modred.redomelib.exception.BlankException;
import br.org.cancer.modred.redomelib.service.IHlaService;
import br.org.cancer.modred.service.IExameService;
import br.org.cancer.modred.service.ILocusService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe de implementação da interface IExameService.java. O objetivo é fornecer os acessos ao modelo da entidade Exame.
 *  
 * @author Pizão
 * 
 * @param <T> classe que estende de Exame
 *
 */
@Transactional(propagation = Propagation.REQUIRED)
public abstract class ExameService<T extends Exame> extends AbstractLoggingService<T, Long> implements IExameService<T> {
	
	protected static final String ERRO_VALIDACAO = "erro.validacao";
	
	@Autowired
	private ILocusService locusService;
	
	@Autowired
	protected IHlaService hlaService;

	@Override
	public abstract IRepository<T, Long> getRepository();

	/**
	 * Método para verificar se HLA do exame é válido (ambos os alelos de todos os locus informados).
	 * 
	 * @param listaLocusExame lista de locus exame a serem validados.
	 * @return Quantidade de referências encontradas no banco.
	 */
	@Override
	public List<CampoMensagem> validarHla(String codigoLocusId, String valor) {
		return this.validarHlaGeral(codigoLocusId, valor, false);
	}
	
	@Override
	public List<CampoMensagem> validarHlaComAntigeno(String codigoLocusId, String valor) {
		return this.validarHlaGeral(codigoLocusId, valor, true);
	}
	
	public List<CampoMensagem> validarHlaGeral(String codigoLocusId, String valor, Boolean temAntigeno) {

		List<CampoMensagem> campos = new ArrayList<>();
		
		if(!temAntigeno && valor.length() < 5) {
			campos.add(montarMensagemExameHlaInvalido(codigoLocusId, valor));
		}else {
			if (!hlaService.validarHla(codigoLocusId, valor, false)) {
				campos.add(montarMensagemExameHlaInvalido(codigoLocusId, valor));
			}	
		}
		return campos;
	}
	
	private CampoMensagem montarMensagemExameHlaInvalido(String codigoLocusId, String alelo) {
		return new CampoMensagem("erro", AppUtil.getMensagem(messageSource,
				"exame.hla.invalido", 
				(codigoLocusId != null ? codigoLocusId : "") +
				(alelo != null ? " " + alelo : "") ));
	}

	/**
	 * Método para validar o HLA com uma lista de locus exame.
	 * 
	 * @param List<LocusExame> locusExames
	 * @param List<CampoMensagem> campos
	 */
	@Override
	public void validarHlaComListaLocusExames(List<LocusExame> locusExames,
			List<CampoMensagem> campos) {
		try {
			if (Optional.ofNullable(locusExames).isPresent()) {
				locusExames.forEach(locusExame -> {
					
					try {
						hlaService.validarHla(locusExame.getId().getLocus().getCodigo(), 
								locusExame.getPrimeiroAlelo(), locusExame.getSegundoAlelo());
					}
					catch (BlankException be) {
						campos.add(new CampoMensagem("erro", AppUtil.getMensagem(messageSource,
								"exame.hla.ambos.alelos.blank")));
					}
					catch (AleloException ae) {
						if (!ae.isAlelo1Valido()) {
							campos.add(montarMensagemExameHlaInvalido(locusExame.getId().getLocus().getCodigo(), 
								locusExame.getPrimeiroAlelo()));
						}
						if (!ae.isAlelo2Valido()) {
							campos.add(montarMensagemExameHlaInvalido(locusExame.getId().getLocus().getCodigo(), 
									locusExame.getSegundoAlelo()));	
						}
					}
				});
			}
			if (!campos.isEmpty()) {
				campos.add(new CampoMensagem("exameGeral", AppUtil.getMensagem(messageSource,
						"exame.hla.invalido.generico")));
				throw new ValidationException(ERRO_VALIDACAO, campos);
			}
		}
		catch (BusinessException | ValidationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new BusinessException("erro.validar.hla", e);
		}
	}

	/**
	 * Método que retorna um exame.
	 * 
	 * @param id
	 */
	@Override
	public T obterExame(Long id) {
		T exame = getRepository().findById(id).orElse(null);
		if (exame == null) {
			throw new BusinessException("exame.nao.encontrado");
		}

		if (StatusExame.DESCARTADO.getCodigo().equals(exame.getStatusExame())) {
			throw new BusinessException("mensagem.exame.descartado");
		}

		return exame;
	}

	@Override
	public List<CampoMensagem> validarExame(T exame) {
		
		List<CampoMensagem> campos = new ConstraintViolationTransformer(validator.validate(
				exame)).transform();
		
		locusService.validarSeLocusSaoValidosPorCodigo(exame.getLocusExames(), campos);
		validarHlaComListaLocusExames(exame.getLocusExames(), campos);
		
		return campos;
	}
	
	
	@Override
	public List<CampoMensagem> validarExames(List<T> exames){
		
		List<CampoMensagem> camposMensagem = new ArrayList<>();
		
		if(CollectionUtils.isEmpty(exames)){
			camposMensagem.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "exames.nao.informados.falha")));
		}
		else {
			exames.forEach(exame -> camposMensagem.addAll(validarExame(exame)));
		}
		return camposMensagem;
	}

	/**
	 * Atualiza o exame para algumas informações e o status informados.
	 * 
	 * @param exameOriginal exame com os dados mais atualizados.
	 * @param status para o qual o exame será atualizado.
	 */
	protected void atualizarStatusExame(T exameOriginal, StatusExame status) {
		exameOriginal.setStatusExame(status.getCodigo());

		List<CampoMensagem> campos = validarExame(exameOriginal);
		if (!campos.isEmpty()) {
			throw new ValidationException(ERRO_VALIDACAO, campos);
		}

		save(exameOriginal);
	}
	

	@Override
	public String[] obterParametros(T exame) {
		String id = null;
		
		if (exame instanceof ExamePaciente) {
			id = String.valueOf(((ExamePaciente)exame).getPaciente().getRmr());
		}
		else if (exame instanceof ExameDoadorNacional) {
			id = String.valueOf(((ExameDoadorNacional)exame).getDoador().getDmr());	
		}
		else if (exame instanceof ExameDoadorInternacional) {
			id = String.valueOf(((ExameDoadorInternacional)exame).getDoador().getIdentificacao());	
		}
		else {
			throw new IllegalStateException("Tipo de exame não identificado: " + exame);
		}
		
		return new String[] { id };
		
	}
	
}