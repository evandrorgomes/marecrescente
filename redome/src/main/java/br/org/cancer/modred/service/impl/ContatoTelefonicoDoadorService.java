package br.org.cancer.modred.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.domain.TipoTelefone;
import br.org.cancer.modred.persistence.IContatoTelefonicoDoadorRepository;
import br.org.cancer.modred.service.IContatoTelefonicoDoadorService;
import br.org.cancer.modred.service.IContatoTelefonicoService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe de negócio envolvendo a entidade ContatoTelefonicoDoador.
 * 
 * @author Pizão.
 *
 */
@Service
@Transactional
public class ContatoTelefonicoDoadorService implements IContatoTelefonicoDoadorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContatoTelefonicoDoadorService.class);

	@Autowired
	private IContatoTelefonicoDoadorRepository repository;
		
	private MessageSource messageSource;
	
	@Autowired
	private IContatoTelefonicoService contatoTelefonicoService;
		
	private IDoadorNacionalService doadorService;

	

	@Override
	public List<ContatoTelefonicoDoador> listarTelefones(Long idDoador) {
		return repository.findByDoadorId(idDoador);
	}

	@Override
	public ContatoTelefonicoDoador obterTelefoneCelularParaSMS(Long idDoador) {
		List<ContatoTelefonicoDoador> telefones = listarTelefones(idDoador);
		ContatoTelefonicoDoador celular = null;

		if (CollectionUtils.isNotEmpty(telefones)) {
			List<ContatoTelefonicoDoador> celulares = telefones.stream().filter(tel -> {
				return TipoTelefone.CELULAR.getCodigo().equals(tel.getTipo());
			}).collect(Collectors.toList());

			if (CollectionUtils.isNotEmpty(celulares)) {
				Optional<ContatoTelefonicoDoador> celularPrincipal = celulares.stream().filter(tel -> {
					return tel.getPrincipal();
				}).findFirst();

				celular = celularPrincipal.isPresent() ? celularPrincipal.get() : celulares.get(0);
			}
		}

		if (celular == null) {
			// Logando, caso seja necessário identificar o motivo de não agendamento do SMS.
			LOGGER.info("Doador " + idDoador + " não possui número de celular registrado.");
		}

		return celular;
	}

	/* (non-Javadoc)
	 * @see br.org.cancer.modred.service.IContatoTelefonicoDoadorService#salvar(br.org.cancer.modred.model.ContatoTelefonicoDoador)
	 */
	@Override
	public RetornoInclusaoDTO salvar(ContatoTelefonicoDoador contato) {
		if (Boolean.TRUE.equals(contato.getPrincipal())) {
			boolean jaTemContatoPrincipal = 
					doadorService.verificarDoadorTemContatoPrincipal(contato.getDoador().getId());

			if (jaTemContatoPrincipal) {
				repository.retirarContatoPrincipalDoador(contato.getDoador().getId());
			}
		}
		
		validar(contato);
		ContatoTelefonicoDoador contatoTelefonico = repository.save(contato);
		String mensagemSucesso = AppUtil.getMensagem(messageSource, "contatoTelefonico.incluido.sucesso");
		return new RetornoInclusaoDTO(contatoTelefonico.getId(), mensagemSucesso);
	}

	/* (non-Javadoc)
	 * @see br.org.cancer.modred.service.IContatoTelefonicoDoadorService#validar(br.org.cancer.modred.model.ContatoTelefonicoDoador)
	 */
	@Override
	public void validar(ContatoTelefonicoDoador contatoDoador) {
		contatoTelefonicoService.validar(contatoDoador);
	}
	
	@Override
	public ContatoTelefonicoDoador obterContatoTelefonicoDoador(Long id) {
		return repository.findById(id).get();
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param doadorService the doadorService to set
	 */
	@Autowired
	public void setDoadorService(IDoadorNacionalService doadorService) {
		this.doadorService = doadorService;
	}
	
	
}
