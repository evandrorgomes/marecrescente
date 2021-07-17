package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.EnderecoContato;
import br.org.cancer.modred.persistence.IEnderecoContatoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IEnderecoContatoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de implementação de métodos de negócio de Endereço de Contato.
 * @param <T> entidade que extende de endereço de contato.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class EnderecoContatoService<T extends EnderecoContato> extends AbstractService<T, Long> implements IEnderecoContatoService<T> {

	@Autowired
	private IEnderecoContatoRepository<T> enderecoContatoRepository;
	

	@Override
	public IRepository<T, Long> getRepository() {
		return enderecoContatoRepository;
	}
	
	@Override
	public void excluir(Long idEnderecoContato) {
		EnderecoContato enderecoContato = enderecoContatoRepository.findById(idEnderecoContato).orElse(null);
		if(enderecoContato == null){
			throw new BusinessException("erro.enderecocontato.nao_localizado");
		}
		enderecoContatoRepository.excluirEndereco(idEnderecoContato);
	}

	@Override
	public void atualizar(Long id, T endereco) {
		T enderecoContatoRecuperado = findById(id);
		if (enderecoContatoRecuperado == null) {
			throw new BusinessException("erro.requisicao");
		}
		enderecoContatoRecuperado.setCep(endereco.getCep());
		enderecoContatoRecuperado.setPais(endereco.getPais());
		enderecoContatoRecuperado.setNomeLogradouro(endereco.getNomeLogradouro());
		enderecoContatoRecuperado.setNumero(endereco.getNumero());
		enderecoContatoRecuperado.setComplemento(endereco.getComplemento());
		enderecoContatoRecuperado.setBairro(endereco.getBairro());
		enderecoContatoRecuperado.setMunicipio(endereco.getMunicipio());
		enderecoContatoRecuperado.setTipoLogradouro(endereco.getTipoLogradouro());
		enderecoContatoRecuperado.setPrincipal(endereco.isPrincipal());
		enderecoContatoRecuperado.setCorrespondencia(endereco.isCorrespondencia());
		
		save(enderecoContatoRecuperado);
	}

}
