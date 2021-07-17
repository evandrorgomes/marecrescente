package br.org.cancer.modred.service;

import br.org.cancer.modred.model.EnderecoContatoPaciente;

/**
 * Classe para utilização de serviços envolvidos com a 
 * entidade EnderecoContato.
 * 
 * @author Pizão.
 *
 */
public interface IEnderecoContatoPacienteService {
	
	/**
	 * Atualiza o endereço contato.
	 * 
	 * @param endContato endereço mais atual.
	 */
	EnderecoContatoPaciente atualizar(EnderecoContatoPaciente endContato);
	
	
	/**
	 * A partir do paciente informado, realiza os ajustes no endereço de contato, de acordo com o 
	 * que foi selecionado no formulário. Um paciente que mora no estrangeiro, por exemplo, não
	 * pode ter CEP preenchido.
	 * 
	 * @param enderecoContato endereço que deve ser normalizado.
	 */
	void normalizarEnderecoContato(EnderecoContatoPaciente enderecoContatoPaciente);
}
