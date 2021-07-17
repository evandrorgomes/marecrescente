package br.org.cancer.redome.notificacao.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.org.cancer.redome.notificacao.model.Notificacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que define os atributos contidos na entidade
 * que representa uma notificação associado a um determinado
 * paciente enviado para um determinado usuário.
 * 
 * @author ergomes
 * 
 */

@Data 
@NoArgsConstructor 
@Builder(toBuilder = true)
@AllArgsConstructor
public class NotificacaoDTO implements Serializable {

	private static final long serialVersionUID = 8595989602757577302L;

	private Long id;
	private String descricao;
	private Long categoriaId;
	private Long rmr;
	@Default
	private Boolean lido = false;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataLeitura;	
	private Long parceiro;
	private String nomeClasseParceiro;
	private Long usuarioId;
	private Long totalNotificacoes;
	private Long idPerfil;
 
	public static NotificacaoDTO parse(Notificacao notificacao) {
		
		NotificacaoDTO notificacaoDtoRetorno = new NotificacaoDTO();
		
		notificacaoDtoRetorno.setId(notificacao.getId());
		notificacaoDtoRetorno.setDescricao(notificacao.getDescricao());
		notificacaoDtoRetorno.setCategoriaId(notificacao.getCategoria().getId());
		notificacaoDtoRetorno.setRmr(notificacao.getRmr());
		notificacaoDtoRetorno.setLido(notificacao.getLido());
		notificacaoDtoRetorno.setDataCriacao(notificacao.getDataCriacao());
		notificacaoDtoRetorno.setDataLeitura(notificacao.getDataLeitura());
		notificacaoDtoRetorno.setParceiro(notificacao.getParceiro());
		notificacaoDtoRetorno.setUsuarioId(notificacao.getUsuario());
		
		return notificacaoDtoRetorno;
	}

}