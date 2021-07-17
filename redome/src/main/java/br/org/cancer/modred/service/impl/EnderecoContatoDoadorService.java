package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.EnderecoContatoDoador;
import br.org.cancer.modred.persistence.IEnderecoContatoDoadorRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IEnderecoContatoDoadorService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de implementação de métodos de negócio de Endereço de Contato Doador.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class EnderecoContatoDoadorService extends AbstractService<EnderecoContatoDoador, Long> 
		implements IEnderecoContatoDoadorService {

	@Autowired
	private IEnderecoContatoDoadorRepository repositorio;
	
	@Override
	public IRepository<EnderecoContatoDoador, Long> getRepository() {
		return repositorio;
	}

	@Override
	public List<EnderecoContatoDoador> listarEnderecos(Long idDoador) {
		return repositorio.listarEnderecos(idDoador);
	}
	

}
