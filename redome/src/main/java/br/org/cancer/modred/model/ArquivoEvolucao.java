package br.org.cancer.modred.model;

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

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.service.impl.StorageService;

/**
 * Classe para relacionamento entre arquivo e evolução.
 * 
 * @author Filipe Paes
 *
 */
@Entity
@SequenceGenerator(name = "SQ_AREV_ID", sequenceName = "SQ_AREV_ID", allocationSize = 1)
@Table(name = "ARQUIVO_EVOLUCAO")
public class ArquivoEvolucao {

	private static final long serialVersionUID = 3635062299392408419L;

	/**
	 * Variável estática representando o tamanho máximo que o nome do arquivo
	 * pode conter.
	 */
	public static final int TAMANHO_MAXIMO_NOME_ARQUIVO = 255;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AREV_ID")
	@Column(name = "AREV_ID")
	@JsonView(EvolucaoView.ListaEvolucao.class)
	private Long id;

	@Column(name = "AREV_TX_CAMINHO_ARQUIVO")
	@Length(max = 255)
	@JsonView({EvolucaoView.ListaEvolucao.class, PacienteView.Rascunho.class})
	private String caminhoArquivo;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "EVOL_ID", nullable = true)
	private Evolucao evolucao;

	// Guarda o ID do paciente para ser utilizado durante o download do arquivo.
	// essa é uma estratégia para não precisar construtir um objeto Paciente
	// completo
	@Transient
	private Long rmr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public Evolucao getEvolucao() {
		return evolucao;
	}

	public void setEvolucao(Evolucao evolucao) {
		this.evolucao = evolucao;
	}

	public Long getRmr() {
		return rmr;
	}

	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}
	
	
	/**
	 * Método para obter o path completo do storage de arquivo evolucao.
	 * 
	 * @return
	 */
	public String obterCaminhoCompletoArquivo() {
		return StorageService.DIRETORIO_PACIENTE_STORAGE + "/" + rmr + "/" + StorageService.DIRETORIO_PACIENTE_EVOLUCAO_STORAGE + "/"
				+ obterDiretorioArquivo();
	}
	/**
	 * Método para obter somente o diretorio de evolucao.
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caminhoArquivo == null) ? 0 : caminhoArquivo.hashCode());
		result = prime * result + ((evolucao == null) ? 0 : evolucao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

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
		ArquivoEvolucao other = (ArquivoEvolucao) obj;
		if (caminhoArquivo == null) {
			if (other.caminhoArquivo != null) {
				return false;
			}
		} 
		else if (!caminhoArquivo.equals(other.caminhoArquivo)) {
			return false;
		}
		if (evolucao == null) {
			if (other.evolucao != null) {
				return false;
			}
		} 
		else if (!evolucao.equals(other.evolucao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	
}
