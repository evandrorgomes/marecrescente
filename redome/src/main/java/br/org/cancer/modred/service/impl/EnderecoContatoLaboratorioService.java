package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.EnderecoContatoLaboratorio;
import br.org.cancer.modred.persistence.IEnderecoContatoLaboratorioRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IEnderecoContatoLaboratorioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de implementação de métodos de negócio de Endereço de Contato Doador.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class EnderecoContatoLaboratorioService extends AbstractService<EnderecoContatoLaboratorio, Long> 
		implements IEnderecoContatoLaboratorioService {

	@Autowired
	private IEnderecoContatoLaboratorioRepository repositorio;
	
	@Override
	public IRepository<EnderecoContatoLaboratorio, Long> getRepository() {
		return repositorio;
	}

	@Override
	public List<EnderecoContatoLaboratorio> listarEnderecos(Long idLaboratorio) {
		return repositorio.listarEnderecos(idLaboratorio);
	}
	

}
