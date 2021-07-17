package br.org.cancer.modred.model;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PrescricaoView;

public interface ICordaoInternacional {
	
	@JsonView(PrescricaoView.DetalheDoador.class)
	public String getIdRegistro();

}
