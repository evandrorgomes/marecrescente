package br.org.cancer.modred.controller.dto.doador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.org.cancer.modred.controller.dto.SolicitacaoDTO;

/**
 * Representa o pedido de exame solicitado para determinado paciente ou doador,
 * podendo estes serem nacionais ou internacionais.
 * 
 * @author evandro.gomes
 * 
 */
public class PedidoExameDTO {

	private Long id;
	private LocalDateTime dataCriacao;
    private Long idSolicitacao;
	private Long idStatusPedidoExame;
    private Long idLaboratorio;
	private LocalDate dataColetaAmostra;
	private LocalDate dataRecebimentoAmostra;
	private LocalDate dataCancelamento;
    private ExameDoadorNacionalDTO exame;
    private Long idTipoExame;
	private Long idOwner;
	private String justificativa;
	private List<SolicitacaoRedomewebDTO> solicitacoesRedomeweb;
	private SolicitacaoDTO solicitacao;
	
	/**
	 * Construtor para definição dos observers envolvendo
	 * esta entidade.
	 */
	public PedidoExameDTO() {
		super();
	}
	
	/**
	 * Construtor para facilitar a instanciação do 
	 * pedido já com o ID preenchido.
	 * 
	 * @param pedidoExameId ID do pedido de exame a ser instanciado.
	 */
	public PedidoExameDTO(Long pedidoExameId) {
		super();
		this.id = pedidoExameId;
	}

	/**
	 * @param id - identificação do pedido exame.
	 * @param idStatusPedidoExame - identificação do status do pedido exame.
	 */
	public PedidoExameDTO(Long id, Long idStatusPedidoExame) {
		super();
		this.id = id;
		this.idStatusPedidoExame = idStatusPedidoExame;
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
	 * @return the dataColetaAmostra
	 */
	public LocalDate getDataColetaAmostra() {
		return dataColetaAmostra;
	}

	/**
	 * @param dataColetaAmostra the dataColetaAmostra to set
	 */
	public void setDataColetaAmostra(LocalDate dataColetaAmostra) {
		this.dataColetaAmostra = dataColetaAmostra;
	}

	/**
	 * @return the dataRecebimentoAmostra
	 */
	public LocalDate getDataRecebimentoAmostra() {
		return dataRecebimentoAmostra;
	}

	/**
	 * @param dataRecebimentoAmostra the dataRecebimentoAmostra to set
	 */
	public void setDataRecebimentoAmostra(LocalDate dataRecebimentoAmostra) {
		this.dataRecebimentoAmostra = dataRecebimentoAmostra;
	}

	/**
	 * @return the dataCancelamento
	 */
	public LocalDate getDataCancelamento() {
		return dataCancelamento;
	}

	/**
	 * @param dataCancelamento the dataCancelamento to set
	 */
	public void setDataCancelamento(LocalDate dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	/**
	 * @return the exame
	 */
	public ExameDoadorNacionalDTO getExame() {
		return exame;
	}

	/**
	 * @param exame the exame to set
	 */
	public void setExame(ExameDoadorNacionalDTO exame) {
		this.exame = exame;
	}

	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}

	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	/**
	 * @return the solicitacoesRedomeweb
	 */
	public List<SolicitacaoRedomewebDTO> getSolicitacoesRedomeweb() {
		return solicitacoesRedomeweb;
	}

	/**
	 * @param solicitacoesRedomeweb the solicitacoesRedomeweb to set
	 */
	public void setSolicitacoesRedomeweb(List<SolicitacaoRedomewebDTO> solicitacoesRedomeweb) {
		this.solicitacoesRedomeweb = solicitacoesRedomeweb;
	}
	
	/**
	 * @return the idSolicitacao
	 */
	public Long getIdSolicitacao() {
		return idSolicitacao;
	}

	/**
	 * @param idSolicitacao the idSolicitacao to set
	 */
	public void setIdSolicitacao(Long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	/**
	 * @return the idStatusPedidoExame
	 */
	public Long getIdStatusPedidoExame() {
		return idStatusPedidoExame;
	}

	/**
	 * @param idStatusPedidoExame the idStatusPedidoExame to set
	 */
	public void setIdStatusPedidoExame(Long idStatusPedidoExame) {
		this.idStatusPedidoExame = idStatusPedidoExame;
	}

	/**
	 * @return the idLaboratorio
	 */
	public Long getIdLaboratorio() {
		return idLaboratorio;
	}

	/**
	 * @param idLaboratorio the idLaboratorio to set
	 */
	public void setIdLaboratorio(Long idLaboratorio) {
		this.idLaboratorio = idLaboratorio;
	}

	/**
	 * @return the idTipoExame
	 */
	public Long getIdTipoExame() {
		return idTipoExame;
	}

	/**
	 * @param idTipoExame the idTipoExame to set
	 */
	public void setIdTipoExame(Long idTipoExame) {
		this.idTipoExame = idTipoExame;
	}

	/**
	 * @return the idOwner
	 */
	public Long getIdOwner() {
		return idOwner;
	}

	/**
	 * @param idOwner the idOwner to set
	 */
	public void setIdOwner(Long idOwner) {
		this.idOwner = idOwner;
	}
	
	/**
	 * @return the solicitacao
	 */
	public SolicitacaoDTO getSolicitacao() {
		return solicitacao;
	}

	/**
	 * @param solicitacao the solicitacao to set
	 */
	public void setSolicitacao(SolicitacaoDTO solicitacao) {
		this.solicitacao = solicitacao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!( obj instanceof PedidoExameDTO )) {
			return false;
		}
		PedidoExameDTO other = (PedidoExameDTO) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
	}
}