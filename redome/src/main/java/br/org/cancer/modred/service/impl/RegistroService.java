package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.doador.RegistroDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Pais;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.persistence.IRegistroRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IRegistroService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.NotEquals;
import br.org.cancer.modred.service.impl.custom.AbstractService;


/**
 * Implementação dos métodos de negócio de DestinoColeta.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class RegistroService extends AbstractService<Registro, Long> implements IRegistroService {

	@Autowired
	private IRegistroRepository registroRepository;
	
	@Override
	public IRepository<Registro, Long> getRepository() {
		return registroRepository;
	}

	@Override
	public List<Registro> obterTodosRegistrosOrdenados() {
		return registroRepository.findAllByOrderByNomeAsc();
	}

	@Override
	public List<Registro> obterTodosRegistrosInternacionais() {
		return this.find(new NotEquals<Long>("pais.id", Pais.BRASIL));
	}

	@Override
	public RegistroDTO obterRegistroPorSigla(String sigla) {
		final Registro registro = this.findOne(new Equals<String>("sigla", sigla));
		if (registro != null) {
			return new RegistroDTO(registro.getId(), registro.getNome(), registro.getSigla() );
		}
		throw new BusinessException("mensagem.nenhum.registro.encontrado", "Registro (" + sigla + ")");
	}
	
	@Override
	public Registro obterRegistroPorDonPool(Long donPool) {
		return this.registroRepository.findByDonPool(donPool).orElse(null);
	}

}
