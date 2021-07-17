package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.EnderecoContatoCentroTransplante;
import br.org.cancer.modred.persistence.IEnderecoContatoCentroTransplanteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IEnderecoContatoCentroTransplanteService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de implementação de métodos de negócio de Endereço de Contato de Centro de Transplante.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class EnderecoContatoCentroTransplanteService extends AbstractService<EnderecoContatoCentroTransplante, Long> 
		implements IEnderecoContatoCentroTransplanteService {

	@Autowired
	private IEnderecoContatoCentroTransplanteRepository repositorio;
	
	@Override
	public IRepository<EnderecoContatoCentroTransplante, Long> getRepository() {
		return repositorio;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void atualizar(Long id, EnderecoContatoCentroTransplante enderecoContato) {
		EnderecoContatoCentroTransplante enderecoContatoRecuperado = findById(id);
		if (enderecoContatoRecuperado == null) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "Endereço");
		}
		enderecoContatoRecuperado.setCep(enderecoContato.getCep());
		enderecoContatoRecuperado.setPais(enderecoContato.getPais());
		enderecoContatoRecuperado.setNomeLogradouro(enderecoContato.getNomeLogradouro());
		enderecoContatoRecuperado.setNumero(enderecoContato.getNumero());
		enderecoContatoRecuperado.setComplemento(enderecoContato.getComplemento());
		enderecoContatoRecuperado.setBairro(enderecoContato.getBairro());
		enderecoContatoRecuperado.setMunicipio(enderecoContato.getMunicipio());
		enderecoContatoRecuperado.setTipoLogradouro(enderecoContato.getTipoLogradouro());		
		enderecoContatoRecuperado.setPrincipal(enderecoContato.isPrincipal());
		enderecoContatoRecuperado.setCorrespondencia(enderecoContato.isCorrespondencia());
		enderecoContatoRecuperado.setRetirada(enderecoContato.isRetirada());
		enderecoContatoRecuperado.setEntrega(enderecoContato.isEntrega());
		
		save(enderecoContatoRecuperado);
		
	}


}
