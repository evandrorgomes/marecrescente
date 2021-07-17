package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.service.impl.StorageService;

/**
 * Classe que representa um arquivo de exame.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_AREX_ID", sequenceName = "SQ_AREX_ID", allocationSize = 1)
@Table(name = "ARQUIVO_EXAME")
public class ArquivoExame implements Serializable {

	private static final long serialVersionUID = 3635062299392408419L;

	/**
	 * Variável estática representando o tamanho máximo que o nome do arquivo pode conter.
	 */
	public static final int TAMANHO_MAXIMO_NOME_ARQUIVO = 257;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AREX_ID")
	@Column(name = "AREX_ID")
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class })
	private Long id;

	@Column(name = "AREX_TX_CAMINHO_ARQUIVO")
	@NotNull
	@Length(max = 263)
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class })
	private String caminhoArquivo;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "EXAM_ID", nullable = true)
	@NotNull
	private Exame exame;

	// Guarda o ID do paciente para ser utilizado durante o download do arquivo.
	// essa é uma estratégia para não precisar construtir um objeto Paciente
	// completo
	@Transient
	private Long rmr;

	/**
	 * Construtor padrão.
	 */
	public ArquivoExame() {}

	/**
	 * Construtor sobrecarregado recebendo o rmr e o caminho do arquivo.
	 * 
	 * @param pacienteRmr
	 * @param caminhoArquivo
	 */
	public ArquivoExame(Long rmr, String caminhoArquivo) {
		this.rmr = rmr;
		this.caminhoArquivo = caminhoArquivo;
	}

	/**
	 * Construtor sobrecarregado recebendo o caminho do arquivo.
	 * 
	 * @param caminhoArquivo
	 */
	public ArquivoExame(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	/**
	 * @return the caminhoArquivo
	 */
	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	/**
	 * @param caminhoArquivo the caminhoArquivo to set
	 */
	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	/**
	 * @return the exame
	 */
	public Exame getExame() {
		return exame;
	}

	/**
	 * @param exame the exame to set
	 */
	public void setExame(Exame exame) {
		this.exame = exame;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param exame the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * @param exame the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * Método para obter somente o nome do arquivo de exame.
	 * 
	 * @return nome nome
	 */
	public String obterNomeArquivo() {
		String[] partes = this.getCaminhoArquivo().split("/");
		if (partes.length > 1) {
			return partes[1];
		}
		else {
			return null;
		}
	}

	/**
	 * Método para obter somente o diretorio de exame.
	 * 
	 * @return dir dir
	 */
	public String obterDiretorioArquivo() {
		String[] result = this.getCaminhoArquivo().split("/");
		if (result.length > 1) {
			return result[0];
		}
		else {
			return null;
		}
	}

	/**
	 * Método para obter o path completo do storage de arquivo exame.
	 * 
	 * @return
	 */
	public String obterCaminhoCompletoArquivo() {
		return StorageService.DIRETORIO_PACIENTE_STORAGE + "/" + rmr + "/" + StorageService.DIRETORIO_PACIENTE_EXAME_STORAGE + "/"
				+ obterDiretorioArquivo();
	}

	/**
	 * Implementação do método hashCode para a classe ArquivoExame.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( caminhoArquivo == null ) ? 0 : caminhoArquivo.hashCode() );
		result = prime * result + ( ( exame == null ) ? 0 : exame.hashCode() );
		return result;
	}

	/**
	 * Implementação do método equals para a classe ArquivoExame.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ArquivoExame other = (ArquivoExame) obj;
		if (caminhoArquivo == null) {
			if (other.caminhoArquivo != null) {
				return false;
			}
		}
		else
			if (!caminhoArquivo.equals(other.caminhoArquivo)) {
				return false;
			}
		if (exame == null) {
			if (other.exame != null) {
				return false;
			}
		}
		else
			if (!exame.equals(other.exame)) {
				return false;
			}
		return true;
	}
}
