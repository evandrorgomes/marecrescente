package br.org.cancer.modred.service;

import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.ReservaDoadorInternacional;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe ReservaDoadorInternacional.
 * 
 * @author Pizão
 */
public interface IReservaDoadorInternacionalService extends IService<ReservaDoadorInternacional, Long> {
	
	/**
	 * Salva a reserva do doador, recém cadastrado, para o paciente.
	 * 
	 * @param doador doador reservado ao paciente.
	 * @param busca busca ativa do paciente a associado a reserva.
	 */
	void salvar(Doador doador, Busca busca);
	
	/**
	 * Obtém o paciente que está associado ao doador, ou seja, 
	 * o doador que está reservado.
	 * 
	 * @param doador Doador reservado.
	 * @return paciente que tem o doador reservado.
	 */
	Paciente obterPacienteAssociado(Doador doador);
	
}
