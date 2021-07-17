package br.org.cancer.redome.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.redome.courier.model.ContatoTelefonicoTransportadora;
import br.org.cancer.redome.courier.persistence.IContatoTelefonicoTransportadoraRepository;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.service.IContatoTelefonicoTransportadoraService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;

/**
 * Implementacao de métodos de negocio de contato telefonico de transportadora.
 * @author Filipe Paes
 *
 */
@Service
public class ContatoTelefonicoTransportadoraService extends AbstractService<ContatoTelefonicoTransportadora, Long> 
implements IContatoTelefonicoTransportadoraService{
	
	@Autowired
	private IContatoTelefonicoTransportadoraRepository contatoTelefonicoTransportadoraRepository;

	@Override
	public void atualizar(Long id, ContatoTelefonicoTransportadora contato) {
		ContatoTelefonicoTransportadora contatoTelefonicoTransportadora = this.findById(id);
		contatoTelefonicoTransportadora.setCodArea(contato.getCodArea());
		contatoTelefonicoTransportadora.setCodInter(contato.getCodInter());
		contatoTelefonicoTransportadora.setComplemento(contato.getComplemento());
		contatoTelefonicoTransportadora.setNome(contato.getNome());
		contatoTelefonicoTransportadora.setNumero(contato.getNumero());
		contatoTelefonicoTransportadora.setPrincipal(contato.getPrincipal());
		contatoTelefonicoTransportadora.setTipo(contato.getTipo());
		this.save(contatoTelefonicoTransportadora);
	}

	@Override
	public IRepository<ContatoTelefonicoTransportadora, Long> getRepository() {
		return contatoTelefonicoTransportadoraRepository;
	}

	

}
