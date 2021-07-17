package br.org.cancer.modred.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PrescricaoView;

public interface IDoadorInternacional {
	
	@JsonView(PrescricaoView.DetalheDoador.class)
	public String getIdRegistro();
	
	@JsonView(PrescricaoView.DetalheDoador.class)
	public BigDecimal getPeso();

}
