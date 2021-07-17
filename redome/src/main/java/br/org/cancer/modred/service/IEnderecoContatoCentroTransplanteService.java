package br.org.cancer.modred.service;

import br.org.cancer.modred.model.EnderecoContatoCentroTransplante;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para tratar parte de negocios de Endereco de Contato de Centro de Transplante.
 * 
 * @author bruno.sousa
 *
 */
public interface IEnderecoContatoCentroTransplanteService extends IService<EnderecoContatoCentroTransplante, Long> {

	void atualizar(Long id, EnderecoContatoCentroTransplante enderecoContato);

}
