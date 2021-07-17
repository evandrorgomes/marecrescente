package br.org.cancer.modred.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.ArquivoExame;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Rascunho;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IRascunhoRepository;
import br.org.cancer.modred.service.ICidService;
import br.org.cancer.modred.service.IRascunhoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe para metodos de negócio de Rascunho.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class RascunhoService implements IRascunhoService {

	@Autowired
	private IRascunhoRepository rascunhoRepository;

	@Autowired
	private Validator validator;

	@Autowired
	private MappingJackson2HttpMessageConverter customMappingJackson2HttpMessageConverter;

	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	@Autowired
	private ICidService cidService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void salvarOuAtualizar(Paciente paciente, Long idUsuario) {
		Rascunho rascunho = rascunhoRepository.findByUsuarioId(idUsuario);
		if (rascunho == null) {
			rascunho = new Rascunho();
		}
		try {
			String pacienteToJson = 
					customMappingJackson2HttpMessageConverter
						.getObjectMapper()
						.writerWithView(PacienteView.Rascunho.class)
						.writeValueAsString(paciente);
			
			rascunho.setJson(pacienteToJson.getBytes(StandardCharsets.ISO_8859_1));
			rascunho.setUsuario(idUsuario == null ? null : new Usuario(idUsuario));

			List<CampoMensagem> campos = 
					new ConstraintViolationTransformer(validator.validate(rascunho)).transform();

			if (!campos.isEmpty()) {
				throw new ValidationException("erro.validacao", campos);
			}

			rascunhoRepository.save(rascunho);
		}
		catch (JsonProcessingException e) {
			throw new BusinessException("erro.rascunho.parser", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Paciente obterRascunho(Long idUsuario) {
		Rascunho rascunho = rascunhoRepository.findByUsuarioId(idUsuario);
		Paciente paciente = null;
		if (rascunho != null) {
			
			ObjectMapper objectMapper = customMappingJackson2HttpMessageConverter.getObjectMapper();
			try {
				paciente = objectMapper.readerWithView(PacienteView.Rascunho.class).forType(Paciente.class)
						.readValue(new String(rascunho.getJson(), StandardCharsets.ISO_8859_1));

				// recuperando a cid com a descricao, a mesma não é armazenada com o rascunho pois foi internacionalizada
				if (paciente.getDiagnostico() != null) {
					if (paciente.getDiagnostico().getCid() != null
						&& paciente.getDiagnostico().getCid().getCodigo() != null) {

						paciente.getDiagnostico().setCid(cidService.obterCidPeloCodigo(paciente.getDiagnostico().getCid()
							.getCodigo()));
					}
				}
			}
			catch (Exception e) {
				throw new BusinessException("erro.rascunho.parser", e);
			}
		}
		return paciente;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void excluirPorIdDeUsuario(Long idUsuario) {
//		storageService.removerArquivosPorIdUsuario(idUsuario);
		rascunhoRepository.delete(idUsuario);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeDoDraftOqueNaoEstaNoStorage(Long idUsuario, Paciente paciente) {
		if (paciente != null && Optional.ofNullable(paciente.getExames()).isPresent()) {

			paciente.getExames().forEach(exame -> {

				if (Optional.ofNullable(exame.getArquivosExame()).isPresent()) {

					List<ArquivoExame> arquivosExameTemp = new ArrayList<ArquivoExame>(exame.getArquivosExame());

					for (int i = ( exame.getArquivosExame().size() - 1 ); i >= 0; i--) {
						ArquivoExame arquivo = exame.getArquivosExame().get(i);

//						if (storageService.verificarExistenciaArquivoPorIdUsuario(idUsuario, arquivo.getCaminhoArquivo())) {
//							arquivosExameTemp.remove(i);
//						}
					}
					exame.setArquivosExame(arquivosExameTemp);
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void removeDoStorageOqueNaoEstaNoDraft(Long idUsuario, Paciente paciente) {
		if (paciente == null || !Optional.ofNullable(paciente.getExames()).isPresent()) {
			return;
		}
//		List<S3ObjectSummary> arquivosStorage = null; // storageService.localizarArquivosPorIdUsuario(idUsuario);

//		for (S3ObjectSummary objectSummary : arquivosStorage) {
//			boolean existe = false;
//
//			if (Optional.ofNullable(paciente.getExames()).isPresent()) {
//				for (ExamePaciente exame : paciente.getExames()) {
//					if (Optional.ofNullable(exame.getArquivosExame()).isPresent()) {
//						for (ArquivoExame arquivoExame : exame.getArquivosExame()) {
//							String[] nameSplit = objectSummary.getKey().split("/");
//							String somenteNome = nameSplit[nameSplit.length - 1];
//							if (arquivoExame.getCaminhoArquivo().equals(somenteNome)) {
//								existe = true;
//								break;
//							}
//						}
//					}
//				}
//			}
//			if (!existe) {
//				storageService.removerArquivo(objectSummary.getKey());
//			}
//		}
	}
}
