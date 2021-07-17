package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.EnderecoContatoPaciente;
import br.org.cancer.modred.model.Pais;
import br.org.cancer.modred.persistence.IEnderecoContatoPacienteRepository;
import br.org.cancer.modred.service.IEnderecoContatoPacienteService;

/**
 * Classe de negócio envolvendo a entidade EnderecoContato.
 * 
 * @author Pizão.
 *
 */
@Service
@Transactional
public class EnderecoContatoPacienteService implements IEnderecoContatoPacienteService {

	@Autowired
	private IEnderecoContatoPacienteRepository endContatoRepository;

	@Override
	public EnderecoContatoPaciente atualizar(EnderecoContatoPaciente endContato) {
		normalizarEnderecoContato(endContato);
		return endContatoRepository.save(endContato);
	}

	@Override
	public void normalizarEnderecoContato(EnderecoContatoPaciente enderecoContato) {
		boolean moraNoBrasil = Pais.BRASIL.equals(enderecoContato.getPais().getId());
		if (moraNoBrasil) {
			enderecoContato.setEnderecoEstrangeiro(null);
		}
		else {
			enderecoContato.setCep(null);
			enderecoContato.setMunicipio(null);
			enderecoContato.setBairro(null);
			enderecoContato.setTipoLogradouro(null);
			enderecoContato.setNomeLogradouro(null);
			enderecoContato.setNumero(null);
			enderecoContato.setComplemento(null);
		}
	}

}
