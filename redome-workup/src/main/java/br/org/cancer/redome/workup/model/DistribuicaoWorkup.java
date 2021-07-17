package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DISTRIBUICAO_WORKUP")
@SequenceGenerator(name = "SQ_DIWO_ID", sequenceName = "SQ_DIWO_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistribuicaoWorkup implements Serializable {
	
	private static final long serialVersionUID = 4002476984031162512L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DIWO_ID")
	@Column(name = "DIWO_ID")
	private Long id;
	
	@Column(name = "DIWO_IN_TIPO")	
	private Long tipo;

	@Column(name = "DIWO_DT_CRIACAO")
	@Default
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@Column(name = "DIWO_DT_ATUALIZACAO")
	@Default
	private LocalDateTime dataAtualizacao = LocalDateTime.now();

	@Column(name = "SOLI_ID")
	private Long solicitacao;
	
	@Column(name = "USUA_ID_DISTRIBUIU")
	private Long usuarioDistribiu;
	
	@Column(name = "USUA_ID_RECEBEU")
	private Long usuarioRecebeu;
	

}
