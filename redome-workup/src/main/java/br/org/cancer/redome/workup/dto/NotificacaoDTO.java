package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe que define os atributos contidos na entidade
 * que representa uma notificação associado a um determinado
 * paciente enviado para um determinado usuário.
 * 
 * @author ergomes
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class NotificacaoDTO implements Serializable {
	
	private static final long serialVersionUID = 1498608486981255830L;

	private Long id;
	private String descricao;
	private Long categoriaId;
	private Long rmr;
	private Boolean lido;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataLeitura;	
	private Long parceiro;
	private String nomeClasseParceiro;
	private Long usuarioId;
	private Long totalNotificacoes;
	private Long idPerfil;


}