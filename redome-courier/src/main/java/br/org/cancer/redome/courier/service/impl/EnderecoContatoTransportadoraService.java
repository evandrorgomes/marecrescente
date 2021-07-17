package br.org.cancer.redome.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.courier.model.EnderecoContatoTransportadora;
import br.org.cancer.redome.courier.persistence.IEnderecoContatoTransportadoraRepository;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.service.IEnderecoContatoTransportadoraService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;

/**
 * Classe de negócios de endereco de contato de transportadora.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class EnderecoContatoTransportadoraService extends AbstractService<EnderecoContatoTransportadora, Long> 
implements IEnderecoContatoTransportadoraService  {

	@Autowired
	private IEnderecoContatoTransportadoraRepository enderecoContatoTransportadoraRepository;
	
	
	@Override
	public IRepository<EnderecoContatoTransportadora, Long> getRepository() {
		return enderecoContatoTransportadoraRepository;
	}

	@Override
	public void atualizarEnderecoContato(Long id, EnderecoContatoTransportadora enderecoContato) {
		EnderecoContatoTransportadora enderecoContatoLocalizado= this.findById(enderecoContato.getId());
		
		enderecoContatoLocalizado = enderecoContatoLocalizado.toBuilder()
				.id(id)
				.bairro(enderecoContato.getBairro())
				.cep(enderecoContato.getCep())
				.complemento(enderecoContato.getComplemento())
				.municipio(enderecoContato.getMunicipio())
				.nomeLogradouro(enderecoContato.getNomeLogradouro())
				.tipoLogradouro(enderecoContato.getTipoLogradouro())
				.numero(enderecoContato.getNumero())
				.pais(enderecoContato.getPais())
				.build();
		
/*		
		enderecoContatoLocalizado.setBairro(enderecoContato.getBairro());
		enderecoContatoLocalizado.setCep(enderecoContato.getCep());
		enderecoContatoLocalizado.setComplemento(enderecoContato.getComplemento());
		enderecoContatoLocalizado.setMunicipio(enderecoContato.getMunicipio());
		enderecoContatoLocalizado.setNomeLogradouro(enderecoContato.getNomeLogradouro());
		enderecoContatoLocalizado.setTipoLogradouro(enderecoContato.getTipoLogradouro());
		enderecoContatoLocalizado.setNumero(enderecoContato.getNumero());
		enderecoContatoLocalizado.setPais(enderecoContato.getPais());
*/		
		
		this.save(enderecoContatoLocalizado);
	}

}
