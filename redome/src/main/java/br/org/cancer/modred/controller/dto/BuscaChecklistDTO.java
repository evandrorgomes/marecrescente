package br.org.cancer.modred.controller.dto;

import static java.time.temporal.ChronoUnit.DAYS;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import br.org.cancer.modred.model.BuscaChecklist;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.IDoador;

/**
 * DTO que representa os itens exibidos no tooltip quando existe alguma item de
 * checklist (BuscaChecklist) para o analista de busca tomar ciência do
 * ocorrido.
 * 
 * @author Pizão
 */
public class BuscaChecklistDTO implements Serializable {
	private static final long serialVersionUID = -1752124203626836261L;

	private Long id;
	private String descricao;
	private Long prazoEmDias;
	private Long idBusca;
	private Long idMatch;
	private Long rmr;
	private Long dmr;
	private String identificadorDoador;
	private Boolean matchAtivo;
	private String fase;
	private String tipoDoador;
	private LocalDateTime dataCriacao; 
	private Boolean vistarChecklist;
	private Long idTipoBuscaChecklist;
	
	
	public BuscaChecklistDTO() {
	}

	/**
	 * Busca cheklist dto.
	 * 
	 * @param buscaChecklist - identificacao da busca checklist
	 */
	@SuppressWarnings("rawtypes")
	public BuscaChecklistDTO(BuscaChecklist buscaChecklist) {
		this.id = buscaChecklist.getId();
		this.descricao = buscaChecklist.getTipoBuscaChecklist().getDescricao();
		this.idTipoBuscaChecklist = buscaChecklist.getTipoBuscaChecklist().getId();
		this.idBusca = buscaChecklist.getBusca().getId();
		this.rmr = buscaChecklist.getBusca().getPaciente().getRmr();
		if(buscaChecklist.getMatch() != null){
			this.idMatch = buscaChecklist.getMatch().getId();
			this.dmr = buscaChecklist.getMatch().getDoador() instanceof DoadorNacional ?
					((DoadorNacional)buscaChecklist.getMatch().getDoador()).getDmr(): null;			
			final IDoador doadorI = (IDoador)buscaChecklist.getMatch().getDoador();
			this.identificadorDoador = doadorI.getIdentificacao().toString();
			this.tipoDoador = this.obterDescricaoTipoDoador(doadorI);
			this.fase = doadorI.getFase();
			this.setMatchAtivo(buscaChecklist.getMatch().getStatus());
		}
		this.dataCriacao = buscaChecklist.getDataCriacao();
		this.prazoEmDias = DAYS.between(LocalDate.now(), buscaChecklist.getDataCriacao().plusDays(buscaChecklist.getTipoBuscaChecklist().getAgeEmDias()).toLocalDate());
	}
	
	

	private String obterDescricaoTipoDoador(IDoador doador) {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		
		if(doador.isCordao()) {
			return messageSource.getMessage("tipodoador.nome.cordao", null, LocaleContextHolder.getLocale());
		}
		else if (doador.isInternacional()) {
			return messageSource.getMessage("tipodoador.nome.internacional", null, LocaleContextHolder.getLocale());
		}
		else if(doador.isNacional()) {
			return messageSource.getMessage("tipodoador.nome.nacional", null, LocaleContextHolder.getLocale());	
		}
		else {
			return messageSource.getMessage("tipodoador.nome.medula", null, LocaleContextHolder.getLocale());
		}
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the prazoEmDias
	 */
	public Long getPrazoEmDias() {
		return prazoEmDias;
	}

	/**
	 * @param prazoEmDias the prazoEmDias to set
	 */
	public void setPrazoEmDias(Long prazoEmDias) {
		this.prazoEmDias = prazoEmDias;
	}

	/**
	 * @return the idBusca
	 */
	public Long getIdBusca() {
		return idBusca;
	}

	/**
	 * @param idBusca the idBusca to set
	 */
	public void setIdBusca(Long idBusca) {
		this.idBusca = idBusca;
	}

	/**
	 * @return the idMatch
	 */
	public Long getIdMatch() {
		return idMatch;
	}

	/**
	 * @param idMatch the idMatch to set
	 */
	public void setIdMatch(Long idMatch) {
		this.idMatch = idMatch;
	}

	/**
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}

	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}

	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * @param rmr the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * @return the matchAtivo
	 */
	public Boolean getMatchAtivo() {
		return matchAtivo;
	}


	/**
	 * @param matchAtivo the matchAtivo to set
	 */
	public void setMatchAtivo(Boolean matchAtivo) {
		this.matchAtivo = matchAtivo;
	}

	/**
	 * @return the identificadorDoador
	 */
	public String getIdentificadorDoador() {
		return identificadorDoador;
	}

	/**
	 * @param identificadorDoador the identificadorDoador to set
	 */
	public void setIdentificadorDoador(String identificadorDoador) {
		this.identificadorDoador = identificadorDoador;
	}

	/**
	 * @return the tipoDoador
	 */
	public String getTipoDoador() {
		return tipoDoador;
	}

	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(String tipoDoador) {
		this.tipoDoador = tipoDoador;
	}

	/**
	 * @return the fase
	 */
	public String getFase() {
		return fase;
	}

	/**
	 * @param fase the fase to set
	 */
	public void setFase(String fase) {
		this.fase = fase;
	}

	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the vistarChecklist
	 */
	public Boolean getVistarChecklist() {
		return vistarChecklist;
	}

	/**
	 * @param vistarChecklist the vistarChecklist to set
	 */
	public void setVistarChecklist(Boolean vistarChecklist) {
		this.vistarChecklist = vistarChecklist;
	}

	/**
	 * @return the idTipoBuscaChecklist
	 */
	public Long getIdTipoBuscaChecklist() {
		return idTipoBuscaChecklist;
	}

	/**
	 * @param idTipoBuscaChecklist the idTipoBuscaChecklist to set
	 */
	public void setIdTipoBuscaChecklist(Long idTipoBuscaChecklist) {
		this.idTipoBuscaChecklist = idTipoBuscaChecklist;
	}
	
}
