package br.org.cancer.modred.model;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;

public interface ICordaoNacional {
	
	@JsonView(PrescricaoView.DetalheDoador.class)
	public String getIdBancoSangueCordao();
	
	@JsonView({PrescricaoView.DetalheDoador.class,  SolicitacaoView.detalhe.class})
	public BancoSangueCordao getBancoSangueCordao();

}
