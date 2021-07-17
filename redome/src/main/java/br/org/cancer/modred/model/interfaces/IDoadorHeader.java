package br.org.cancer.modred.model.interfaces;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.model.BancoSangueCordao;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.StatusDoador;

public interface IDoadorHeader {
	
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class})
	public Long getId();
	
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class})
	public Long getIdTipoDoador();
	
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class})
	public String getIdentificacao();
	
	@JsonView(DoadorView.IdentificacaoCompleta.class)
	public String getNome();
	
	@JsonView(DoadorView.IdentificacaoCompleta.class)
	public String getSexo();
	
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class})
	public StatusDoador getStatusDoador();
	
	@JsonView(DoadorView.IdentificacaoCompleta.class)
	public LocalDate getDataNascimento();
	
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class})
	public Registro getRegistroOrigem();
	
	@JsonView({DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class})
	public BancoSangueCordao getBancoSangueCordao();
	
	
	
	
	

}
