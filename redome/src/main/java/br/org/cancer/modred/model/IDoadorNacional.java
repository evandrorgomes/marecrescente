package br.org.cancer.modred.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PrescricaoView;

public interface IDoadorNacional {
	
	@JsonView(PrescricaoView.DetalheDoador.class)
	Long getDmr();
	
	@JsonView(PrescricaoView.DetalheDoador.class)
	BigDecimal getPeso();

}
