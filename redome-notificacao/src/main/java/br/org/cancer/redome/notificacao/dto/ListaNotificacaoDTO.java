package br.org.cancer.redome.notificacao.dto;

import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaNotificacaoDTO {

	private Long idNotificacao;
	private Long idCategoriaNotificacao; 
	private List<Long> parceiros; 
	private Boolean lido; 
	private Long rmr; 
	private Long idUsuarioLogado; 
	private Boolean somentePacientesDoUsuario; 
	private Pageable pageable;
	private Boolean meusPacientes;
	private Long idCentroTransplante;
	
	/**
	 * @param idCentroTransplante
	 * @param rmr
	 * @param meusPacientes
	 * @param pageable
	 */
	public ListaNotificacaoDTO(Long idCentroTransplante, Long rmr, Boolean meusPacientes, Pageable pageable) {
		this.idCentroTransplante = idCentroTransplante;
		this.rmr = rmr;
		this.meusPacientes = meusPacientes;
		this.pageable = pageable;

//		if (meusPacientes != null && meusPacientes) {
//			this.setSomentePacientesDoUsuario(true);
//		}
//		if (idCentroTransplante != null) {
//			this.setParceiros(Arrays.asList(this.getIdCentroTransplante()));
//		}
	}

	/**
	 * @param rmr
	 * @param lidas
	 */
	public ListaNotificacaoDTO(Long rmr, Boolean lido) {
		super();
		this.rmr = rmr;
		this.lido = lido;
	}
	
}
