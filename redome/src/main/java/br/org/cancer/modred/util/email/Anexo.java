package br.org.cancer.modred.util.email;

/**
 * Classe que representa um anexo de email.
 * 
 * @author brunosousa
 *
 */
public class Anexo {
	
	private String nomeArquivo;
	
	private String tipoConteudo;
	
	private byte[] conteudo;
	
	public Anexo() {
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public Anexo nomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
		return this;
	}

	public String getTipoConteudo() {
		return tipoConteudo;
	}

	public Anexo tipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
		return this;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

	public Anexo conteudo(byte[] conteudo) {
		this.conteudo = conteudo;
		return this;
	}
	
}
