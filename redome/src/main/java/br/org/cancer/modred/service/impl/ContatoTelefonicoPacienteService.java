package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ContatoTelefonico;
import br.org.cancer.modred.model.ContatoTelefonicoPaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.persistence.IContatoTelefonicoPacienteRepository;
import br.org.cancer.modred.service.IContatoTelefonicoPacienteService;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe de negócio envolvendo a entidade ContatoTelefonico.
 * 
 * @author Pizão.
 *
 */
@Service
@Transactional
public class ContatoTelefonicoPacienteService implements IContatoTelefonicoPacienteService {

    @Autowired
    private IContatoTelefonicoPacienteRepository contatoTelefonicoRepository;
    
    @Autowired
	private Validator validator;
    
    @Autowired
	private MessageSource messageSource;

	@Override
	public List<ContatoTelefonicoPaciente> atualizar(Paciente paciente, List<ContatoTelefonicoPaciente> telefones) {
		contatoTelefonicoRepository.deletar(paciente.getRmr());
		
		telefones.forEach(tel -> {
			tel.setPaciente(paciente);
		});

		return contatoTelefonicoRepository.saveAll(telefones);
	}
	
	/**
	 * Método para validar os contatos telefonicos, se foram informados para o paciente.
	 * 
	 * @param telefones lista de telefones do paciente
	 * @param Campos mensagens de erro a serem retornadas
	 */
	private void validarTelefonesContatoInvalido(List<ContatoTelefonicoPaciente> telefones,
			List<CampoMensagem> campos) {
		if (CollectionUtils.isNotEmpty(telefones)) {
			List<CampoMensagem> erros = new ArrayList<CampoMensagem>();
			
			telefones.forEach(telefone ->{
				List<CampoMensagem> errosTelefone = new ConstraintViolationTransformer(validator.validate(telefone)).transform();
				erros.addAll(errosTelefone);
			});
			
			if(CollectionUtils.isNotEmpty(erros)){
				campos.add(new CampoMensagem("contatosTelefonicos", messageSource.getMessage(
						"contatoTelefonico.invalido", null, LocaleContextHolder.getLocale())));
			}
		}
		else {
			campos.add(new CampoMensagem("contatosTelefonicos", messageSource.getMessage(
					"contatoTelefonico.obrigatorio", null, LocaleContextHolder.getLocale())));
		}
	}

	/**
	 * Método para validar se ao menos um contato é principal para o paciente.
	 * 
	 * @param telefones lista de telefones do paciente
	 * @param Campos mensagens de erro a serem retornadas
	 */
	private void validarTelefoneContatoPrincipalObrigatorio(List<ContatoTelefonicoPaciente> telefones,
			List<CampoMensagem> campos) {
		if (telefones != null && !telefones.isEmpty()) {
			Boolean existePrincipal = false;
			for (ContatoTelefonico contato : telefones) {
				if (contato.getPrincipal()) {
					existePrincipal = true;
					break;
				}
			}
			if (!existePrincipal) {
				campos.add(
						new CampoMensagem(
								"contatosTelefonicos",
								messageSource.getMessage("contatoTelefonico.principal.obrigatorio",
										null,
										LocaleContextHolder.getLocale())));
			}
		}
	}

	@Override
	public List<CampoMensagem> validar(List<ContatoTelefonicoPaciente> telefones) {
		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
		validarTelefonesContatoInvalido(telefones, campos);
		validarTelefoneContatoPrincipalObrigatorio(telefones, campos);
		return campos;
	}

}
