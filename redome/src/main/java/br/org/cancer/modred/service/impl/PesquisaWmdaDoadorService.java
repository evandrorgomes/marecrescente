package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.DoadorWmdaDTO;
import br.org.cancer.modred.controller.dto.PesquisaWmdaDoadorDTO;
import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.LocusExameDTO;
import br.org.cancer.modred.controller.dto.doador.RegistroDTO;
import br.org.cancer.modred.controller.dto.doador.StatusDoadorDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.PesquisaWmdaDoador;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.persistence.IPesquisaWmdaDoadorRepository;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.ICordaoInternacionalService;
import br.org.cancer.modred.service.IDoadorInternacionalService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IPesquisaWmdaDoadorService;
import br.org.cancer.modred.service.IRegistroService;
import br.org.cancer.modred.util.DateUtils;

/**
 * Classe para de negócios para Pesquisa Wmda Doador.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PesquisaWmdaDoadorService implements IPesquisaWmdaDoadorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PesquisaWmdaDoadorService.class);
	
	@Autowired
	private IPesquisaWmdaDoadorRepository pesquisaWmdaDoadorRepository;
	
	@Autowired
	private ICordaoInternacionalService cordaoInternacionalService;

	@Autowired
	private IDoadorInternacionalService doadorInternacionalService;

	@Autowired
	private IDoadorNacionalService doadorNacionalService;
	
	@Autowired
	private IRegistroService registroService; 

	@Autowired
	private IMatchService matchService; 

	@Autowired
	private IBuscaService buscaService; 

	/**
	 * Método que realiza a criação de Pesquisa Wmda Doador.
	 * 
	 * @param List<PesquisaWmdaDoador> pesquisaWmdaDoadores - Lista de dados do doador wmda
	 */
	private PesquisaWmdaDoadorDTO criarPesquisaWmdaDoador(PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDTO) {
		if(pesquisaWmdaDoadorDTO == null) {
			throw new BusinessException("erro.mensagem.pesquisawmda.nulo");
		}
		PesquisaWmdaDoador pesquisaWmdaDoador = PesquisaWmdaDoador.parse(pesquisaWmdaDoadorDTO);
		return PesquisaWmdaDoadorDTO.parse(pesquisaWmdaDoadorRepository.save(pesquisaWmdaDoador));
	}
	
	/**
	 * @param busca
	 * @param doador
	 */
	@Override
	public PesquisaWmdaDoadorDTO manterRotinaPesquisaWmdaDoador(PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto) {
		
		DoadorWmdaDTO doadorWmdaDto = null;
		String logDoador = null;
		
		try {
			Busca busca = this.buscaService.obterBuscaAtivaPorId(pesquisaWmdaDoadorDto.getBuscaId());

			if(pesquisaWmdaDoadorDto.getDonPool().equals(pesquisaWmdaDoadorDto.getDoadorWmdaDto().getDonPool())) {
				doadorWmdaDto = manterDoadorNacionalRedome(busca, pesquisaWmdaDoadorDto.getDoadorWmdaDto());
			}else {
				doadorWmdaDto = manterDoadorInternacional(busca, pesquisaWmdaDoadorDto.getDoadorWmdaDto());
			}
		}
		catch (Exception erro) {
			if(erro instanceof ValidationException) {
				ValidationException validation = (ValidationException) erro;
				logDoador = validation.getErros().stream().map(e -> "Campo " + e.getCampo() + " - " + e.getMensagem()).collect(Collectors.joining(","));
			}else if(erro instanceof NullPointerException) {
				logDoador = "NullPointerException - Ocorreu um erro ao salvar objeto no banco de dados. " + erro.getMessage();
			}else {
				logDoador = erro.getCause().getMessage();
			}
		}
		
		if(logDoador != null) {
			pesquisaWmdaDoadorDto.setLogPesquisaDoador(logDoador);
		}
		PesquisaWmdaDoadorDTO retornoPesquisaWmdaDoadorDto = this.criarPesquisaWmdaDoador(pesquisaWmdaDoadorDto);
		retornoPesquisaWmdaDoadorDto.setDoadorWmdaDto(doadorWmdaDto);
		
		return retornoPesquisaWmdaDoadorDto;
	}

	@Override
	public List<String> obterListaDeIdentificacaoDoadorWmdaExistente(Long pesquisaWmdaId){
		List<PesquisaWmdaDoador> listaPesquisaWmdaDoador = this.pesquisaWmdaDoadorRepository.findByPesquisaWmda(pesquisaWmdaId);
		if(listaPesquisaWmdaDoador != null && !listaPesquisaWmdaDoador.isEmpty()) {
			return listaPesquisaWmdaDoador.stream().map(p -> p.getIdentificacao()).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	/**
	 * @param doadorWmda
	 * @return
	 */
	private DoadorWmdaDTO manterDoadorInternacional(Busca busca, DoadorWmdaDTO doadorWmda) throws Exception {
		
		Registro registroOrigem = this.registroService.obterRegistroPorDonPool(Long.parseLong(doadorWmda.getDonPool()));
		doadorWmda.setRegistroOrigem(registroOrigem.getId());
		
		DoadorInternacional doador = new DoadorInternacional();
		doador.setIdRegistro(doadorWmda.getIdentificacao() != null ? doadorWmda.getIdentificacao() : doadorWmda.getGrid());
		doador.setRegistroOrigem(registroOrigem);
		doador.setGrid(doadorWmda.getGrid());
		
		if(!this.doadorInternacionalService.verificarDoadorWmdaJaExistente(doador)) {

			Long doadorId = null;
			if(doadorWmda.getTipoDoador().equals(TiposDoador.INTERNACIONAL.getId())) {
				doadorId = this.doadorInternacionalService.salvarDoadorInternacionalWmda(converterParaDoadorInternacionalWmda(doadorWmda));
			}else {
				doadorId = this.cordaoInternacionalService.salvarDoadorCordaoInternacionalWmda(converterParaDoadorInternacionalWmda(doadorWmda));
			}
			doadorWmda.getMatchWmdaDto().setIdBusca(busca.getId());
			doadorWmda.getMatchWmdaDto().setIdDoador(doadorId);
			doadorWmda.getMatchWmdaDto().setAtualizarMatch(false);
			
		}else {
			Doador doadorEncontrado = null;
			if(doadorWmda.getTipoDoador().equals(TiposDoador.INTERNACIONAL.getId())) {
				doadorEncontrado = (DoadorInternacional) doadorInternacionalService.obterDoadorInternacionalPorIdRegistro(doadorWmda.getIdentificacao());
			}else {
				doadorEncontrado = (CordaoInternacional) cordaoInternacionalService.obterCordaoInternacionalPorIdRegistro(doadorWmda.getIdentificacao());
			}

			Match match = matchService.obterMatchAtivo(busca.getPaciente().getRmr(), doadorEncontrado.getId());
			doadorWmda.getMatchWmdaDto().setIdBusca(busca.getId());
			doadorWmda.getMatchWmdaDto().setIdDoador(doadorEncontrado.getId());
			
			if(doadorEncontrado != null && match != null) {
				doadorWmda.getMatchWmdaDto().setAtualizarMatch(true);
				doadorWmda.getMatchWmdaDto().setId(match.getId());
			}else {
				doadorWmda.getMatchWmdaDto().setAtualizarMatch(false);
			}
		}
		return doadorWmda;
	}

	/**
	 * @param busca
	 * @param doadorWmda
	 */
	private DoadorWmdaDTO manterDoadorNacionalRedome(Busca busca, DoadorWmdaDTO doadorWmda) {

		DoadorNacional doador = doadorNacionalService.obterDoadorNacionalPorDmr(Long.valueOf(doadorWmda.getIdentificacao()));
		if(doador == null) {
			throw new NullPointerException("Doador Nacional Inexistente!!");
		}
		Match match = matchService.obterMatchAtivo(busca.getPaciente().getRmr(), doador.getId());
		
		doadorWmda.getMatchWmdaDto().setIdBusca(busca.getId());
		doadorWmda.getMatchWmdaDto().setIdDoador(doador.getId());
		
		if(doador != null && match != null) {
			doadorWmda.getMatchWmdaDto().setAtualizarMatch(true);
		}else {
			doadorWmda.getMatchWmdaDto().setAtualizarMatch(false);
		}
		return doadorWmda;
	}
	
	private DoadorCordaoInternacionalDTO converterParaDoadorInternacionalWmda(DoadorWmdaDTO doadorWmda) {
		
		DoadorCordaoInternacionalDTO doadorInternacional = new DoadorCordaoInternacionalDTO();
		doadorInternacional.setDataAtualizacao(LocalDateTime.now());
		doadorInternacional.setIdRegistro(doadorWmda.getIdentificacao());
		doadorInternacional.setAbo(doadorWmda.getAbo());
		doadorInternacional.setGrid(doadorWmda.getGrid() != null ? doadorWmda.getGrid() : null);
		doadorInternacional.setPeso(doadorWmda.getPeso() != null ? doadorWmda.getPeso() : null);
		doadorInternacional.setRegistroOrigem(new RegistroDTO(doadorWmda.getRegistroOrigem()));
		doadorInternacional.setStatusDoador(new StatusDoadorDTO(StatusDoador.ATIVO));
		
		if(doadorWmda.getTipoDoador().equals(TiposDoador.CORDAO_INTERNACIONAL.getId())) {
			doadorInternacional.setQuantidadeTotalCD34(doadorWmda.getQuantidadeTotalCD34());
			doadorInternacional.setQuantidadeTotalTCN(doadorWmda.getQuantidadeTotalTCN());
			doadorInternacional.setVolume(doadorWmda.getVolume());
		}

		doadorInternacional.setSexo(doadorWmda.getSexo());
		if (doadorWmda.getIdade() != null) {
			doadorInternacional.setDataNascimento(DateUtils.calcularDataNascimentoSimulada(doadorWmda.getIdade()));
		}
		else {
			doadorInternacional.setDataNascimento(doadorWmda.getDataNascimento());
		}
		
		if (CollectionUtils.isNotEmpty(doadorWmda.getLocusExame())) {
			doadorInternacional.setLocusExames(doadorWmda.getLocusExame().stream()
				.map(locusExameDto -> {
					return new LocusExameDTO(null, locusExameDto.getCodigo(), locusExameDto.getPrimeiroAlelo(), locusExameDto.getSegundoAlelo());
				})
				.collect(Collectors.toList())
			);
		}
		return doadorInternacional;
	}

}
