package br.org.cancer.modred.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe de DTO para dados especificos da tela de pedido de transporte.
 * 
 * @author Filipe Paes
 *
 */
public class DetalheMaterialDTO {

	private Long idPedidoLogistica;
	private Long idPedidoTransporte;
	private Long idDoador;
	private Long rmr;
	private Long dmr;
	private String nomeDoador;
	
	private LocalDate dataColeta;
	private String nomeLocalRetirada;
	private String enderecoLocalRetirada;
	
	private LocalDateTime horaPrevistaRetirada;
	
	private String nomeCentroTransplante;
	private String enderecoCentroTransplante;
	private List<String> contatosCentroTransplante;
	
	private TransportadoraListaDTO transportadora;
	
	private CourierDTO courier;
	private String dadosVoo;
	private Long idTipoDoador;
	
	private String nomeFonteCelula;
	
	private List<String> contatosLocalRetirada;
	
	private String retiradaLocal;
	private String retiradaPais;
	private String retiradaEstado;
	private String retiradaCidade;
	private String retiradaRua;
	private String retiradaTelefone;
	private String retiradaIdDoador;
	private String retiradaHawb;
	
	private String nomeCourier;
	private String passaporteCourier;
	
	private LocalDate dataEmbarque;
	private LocalDate dataChegada;
	
	/**
	 * 
	 */
	public DetalheMaterialDTO() {}


	/**
	 * @return the idPedido
	 */
	public Long getIdPedidoLogistica() {
		return idPedidoLogistica;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedidoLogistica(Long idPedido) {
		this.idPedidoLogistica = idPedido;
	}

	/**
	 * @return the dataColeta
	 */
	public LocalDate getDataColeta() {
		return dataColeta;
	}

	/**
	 * @param dataColeta the dataColeta to set
	 */
	public void setDataColeta(LocalDate dataColeta) {
		this.dataColeta = dataColeta;
	}

	/**
	 * @return the horaPrevistaRetirada
	 */
	public LocalDateTime getHoraPrevistaRetirada() {
		return horaPrevistaRetirada;
	}

	/**
	 * @param horaPrevistaRetirada the horaPrevistaRetirada to set
	 */
	public void setHoraPrevistaRetirada(LocalDateTime horaPrevistaRetirada) {
		this.horaPrevistaRetirada = horaPrevistaRetirada;
	}

	/**
	 * @return the nomeCentroTransplante
	 */
	public String getNomeCentroTransplante() {
		return nomeCentroTransplante;
	}

	/**
	 * @param nomeCentroTransplante the nomeCentroTransplante to set
	 */
	public void setNomeCentroTransplante(String nomeCentroTransplante) {
		this.nomeCentroTransplante = nomeCentroTransplante;
	}

	/**
	 * @return the transportadora
	 */
	public TransportadoraListaDTO getTransportadora() {
		return transportadora;
	}

	/**
	 * @param transportadora the transportadora to set
	 */
	public void setTransportadora(TransportadoraListaDTO transportadora) {
		this.transportadora = transportadora;
	}

	public Long getIdDoador() {
		return idDoador;
	}

	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
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
	 * @return the courier
	 */
	public CourierDTO getCourier() {
		return courier;
	}

	/**
	 * @param courier the courier to set
	 */
	public void setCourier(CourierDTO courier) {
		this.courier = courier;
	}

	/**
	 * @return the dadosVoo
	 */
	public String getDadosVoo() {
		return dadosVoo;
	}

	/**
	 * @param dadosVoo the dadosVoo to set
	 */
	public void setDadosVoo(String dadosVoo) {
		this.dadosVoo = dadosVoo;
	}

	public String getEnderecoCentroTransplante() {
		return enderecoCentroTransplante;
	}

	public void setEnderecoCentroTransplante(String enderecoCentroTransplante) {
		this.enderecoCentroTransplante = enderecoCentroTransplante;
	}

	/**
	 * @return the idPedidoTransporte
	 */
	public Long getIdPedidoTransporte() {
		return idPedidoTransporte;
	}

	/**
	 * @param idPedidoTransporte the idPedidoTransporte to set
	 */
	public void setIdPedidoTransporte(Long idPedidoTransporte) {
		this.idPedidoTransporte = idPedidoTransporte;
	}

	/**
	 * Retorna o ID do tipo de doador indicando
	 * se � cord�o, medula (nacional ou n�o).
	 * 
	 * @return ID do tipo de doador.
	 */
	public Long getIdTipoDoador() {
		return idTipoDoador;
	}

	public void setIdTipoDoador(Long idTipoDoador) {
		this.idTipoDoador = idTipoDoador;
	}

	/**
	 * Retorna o nome do local da retirada do material, para log�sticas nacionais.
	 * Se medula nacional ser� o centro de coleta, cord�o, o banco de sangue.
	 * 
	 * @return nome do local de retirada.
	 */
	public String getNomeLocalRetirada() {
		return nomeLocalRetirada;
	}

	public void setNomeLocalRetirada(String nomeLocalRetirada) {
		this.nomeLocalRetirada = nomeLocalRetirada;
	}

	/**
	 * Retorna o endere�o de retirada do material.
	 * 
	 * @return endere�o do local.
	 */
	public String getEnderecoLocalRetirada() {
		return enderecoLocalRetirada;
	}

	public void setEnderecoLocalRetirada(String enderecoLocalRetirada) {
		this.enderecoLocalRetirada = enderecoLocalRetirada;
	}

	/**
	 * Fonte de célula de acordo com o pedido associado a logística.
	 * 
	 * @return nome da fonte de célula.
	 */
	public String getNomeFonteCelula() {
		return nomeFonteCelula;
	}

	public void setNomeFonteCelula(String nomeFonteCelula) {
		this.nomeFonteCelula = nomeFonteCelula;
	}

	/**
	 * Retorna a lista de telefones do local de retirada 
	 * concatenados para exibição.
	 * 
	 * @return lista de textos contendo todos os contatos formatados.
	 */
	public List<String> getContatosLocalRetirada() {
		return contatosLocalRetirada;
	}

	public void setContatosLocalRetirada(List<String> contatosLocalRetirada) {
		this.contatosLocalRetirada = contatosLocalRetirada;
	}

	/**
	 * Retorna a lista de telefones do centro de transplante.
	 * 
	 * @return lista de textos contendo todos os contatos formatados.
	 */
	public List<String> getContatosCentroTransplante() {
		return contatosCentroTransplante;
	}

	public void setContatosCentroTransplante(List<String> contatosCentroTransplante) {
		this.contatosCentroTransplante = contatosCentroTransplante;
	}

	/**
	 * @return the retiradaPais
	 */
	public String getRetiradaPais() {
		return retiradaPais;
	}

	/**
	 * @param retiradaPais the retiradaPais to set
	 */
	public void setRetiradaPais(String retiradaPais) {
		this.retiradaPais = retiradaPais;
	}

	/**
	 * @return the retiradaEstado
	 */
	public String getRetiradaEstado() {
		return retiradaEstado;
	}

	/**
	 * @param retiradaEstado the retiradaEstado to set
	 */
	public void setRetiradaEstado(String retiradaEstado) {
		this.retiradaEstado = retiradaEstado;
	}

	/**
	 * @return the retiradaCidade
	 */
	public String getRetiradaCidade() {
		return retiradaCidade;
	}

	/**
	 * @param retiradaCidade the retiradaCidade to set
	 */
	public void setRetiradaCidade(String retiradaCidade) {
		this.retiradaCidade = retiradaCidade;
	}

	/**
	 * @return the retiradaRua
	 */
	public String getRetiradaRua() {
		return retiradaRua;
	}

	/**
	 * @param retiradaRua the retiradaRua to set
	 */
	public void setRetiradaRua(String retiradaRua) {
		this.retiradaRua = retiradaRua;
	}

	/**
	 * @return the retiradaTelefone
	 */
	public String getRetiradaTelefone() {
		return retiradaTelefone;
	}

	/**
	 * @param retiradaTelefone the retiradaTelefone to set
	 */
	public void setRetiradaTelefone(String retiradaTelefone) {
		this.retiradaTelefone = retiradaTelefone;
	}

	/**
	 * @return the retiradaIdDoador
	 */
	public String getRetiradaIdDoador() {
		return retiradaIdDoador;
	}

	/**
	 * @param retiradaIdDoador the retiradaIdDoador to set
	 */
	public void setRetiradaIdDoador(String retiradaIdDoador) {
		this.retiradaIdDoador = retiradaIdDoador;
	}

	/**
	 * @return the retiradaHawb
	 */
	public String getRetiradaHawb() {
		return retiradaHawb;
	}

	/**
	 * @param retiradaHawb the retiradaHawb to set
	 */
	public void setRetiradaHawb(String retiradaHawb) {
		this.retiradaHawb = retiradaHawb;
	}

	public String getNomeCourier() {
		return nomeCourier;
	}

	public void setNomeCourier(String nomeCourier) {
		this.nomeCourier = nomeCourier;
	}

	public String getPassaporteCourier() {
		return passaporteCourier;
	}

	public void setPassaporteCourier(String passaporteCourier) {
		this.passaporteCourier = passaporteCourier;
	}


	public String getNomeDoador() {
		return nomeDoador;
	}


	public void setNomeDoador(String nomeDoador) {
		this.nomeDoador = nomeDoador;
	}


	public Long getDmr() {
		return dmr;
	}


	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}


	/**
	 * @return the dataEmbarque
	 */
	public LocalDate getDataEmbarque() {
		return dataEmbarque;
	}


	/**
	 * @param dataEmbarque the dataEmbarque to set
	 */
	public void setDataEmbarque(LocalDate dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}


	/**
	 * @return the dataChegada
	 */
	public LocalDate getDataChegada() {
		return dataChegada;
	}


	/**
	 * @param dataChegada the dataChegada to set
	 */
	public void setDataChegada(LocalDate dataChegada) {
		this.dataChegada = dataChegada;
	}


	/**
	 * @return the retiradaLocal
	 */
	public String getRetiradaLocal() {
		return retiradaLocal;
	}


	/**
	 * @param retiradaLocal the retiradaLocal to set
	 */
	public void setRetiradaLocal(String retiradaLocal) {
		this.retiradaLocal = retiradaLocal;
	}
	
	
}
