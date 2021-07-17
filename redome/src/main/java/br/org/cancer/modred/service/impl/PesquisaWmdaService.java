package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.MatchWmdaDTO;
import br.org.cancer.modred.controller.dto.PesquisaWmdaDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.PesquisaWmda;
import br.org.cancer.modred.model.domain.StatusPesquisaWmda;
import br.org.cancer.modred.persistence.IPesquisaWmdaRepository;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IPesquisaWmdaService;
import br.org.cancer.modred.service.IQualificacaoMatchService;

/**
 * Classe para de negócios para Pesquisa Wmda.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class PesquisaWmdaService implements IPesquisaWmdaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PesquisaWmdaService.class);
	private static final String TIPO_BUSCA_DOADOR_MEDULA = "DR";
	private static final String TIPO_BUSCA_DOADOR_CORDAO = "CB";
	
	@Autowired
	private IPesquisaWmdaRepository pesquisaWmdaRepository;
	
	@Autowired
	private IMatchService matchService; 

	@Autowired
	private IQualificacaoMatchService qualificacaoMatchService; 
	
	/**
	 * Método que faz chamada de Repositório e realiza a criação de Pesquisa Wmda.
	 * 
	 * @param Objeto Entity PesquisaWmda
	 * @return PesquisaWmda - Objeto Entity PesquisaWmda  
	 */
	@Override
	public PesquisaWmdaDTO criarPesquisaWmda(PesquisaWmdaDTO pesquisaWmdaDto) {

		if(pesquisaWmdaDto == null) {
			throw new BusinessException("erro.mensagem.pesquisawmda.nulo");
		}
		
		pesquisaWmdaDto.setDtEnvio(LocalDateTime.now());
		PesquisaWmda pesquisaWmdaRecuperada = new PesquisaWmda().parse(pesquisaWmdaDto);

		return this.save(pesquisaWmdaRecuperada);
	}
	
	/**
	 * Método que faz chamada de Repositório e realiza a consulta da Pesquisa Wmda.
	 * 
	 * @param Long Id - identificador da pesquisa wmda.
	 * @return PesquisaWmda - Objeto Entity PesquisaWmda.
	 */
	@Override
	public PesquisaWmdaDTO obterPesquisaWmda(Long id) {
		
		if (id != null) {
			PesquisaWmda pesquisaWmda = pesquisaWmdaRepository.findById(id).orElse(null);

			if (pesquisaWmda != null) {
				return PesquisaWmdaDTO.parse(pesquisaWmda);
			}
		}
		else {
			throw new BusinessException("erro.mensagem.pesquisawmda.id.invalido");
		}

		throw new BusinessException("mensagem.nenhum.registro.encontrado", "registro");
	}

	
	/**
	 * Método que faz chamada de Repositório e realiza a consulta da Pesquisa Wmda.
	 * 
	 * @param Long Id - identificador da pesquisa wmda.
	 * @return PesquisaWmda - Objeto Entity PesquisaWmda.
	 */
	@Override
	public PesquisaWmdaDTO obterPesquisaWmdaPorBuscaIdEStatusAbertoEProcessadoErro(Long buscaId) {
		List<PesquisaWmda> listaPesquisaRecuperada = 
				pesquisaWmdaRepository.findByBuscaAndStatusIn(buscaId, 
						Arrays.asList(StatusPesquisaWmda.ABERTO.getId(), StatusPesquisaWmda.PROCESSADO_COM_ERRO.getId()));
		if (listaPesquisaRecuperada != null && !listaPesquisaRecuperada.isEmpty()) {
			return listaPesquisaRecuperada.stream()
					.map(PesquisaWmdaDTO::parse).findFirst().orElse(null);
		}
		return null;
	}
	
	public PesquisaWmdaDTO save(PesquisaWmda pesquisaWmda) {
		PesquisaWmda pesquisaWmdaNova = pesquisaWmdaRepository.save(pesquisaWmda);
		return PesquisaWmdaDTO.parse(pesquisaWmdaNova);
	}
	
	@Override
	public void manterRotinaMatchWmda(MatchWmdaDTO matchWmdaDto) {
		try {
			if(matchWmdaDto.isAtualizarMatch()) {
				Match match = matchService.obterMatch(matchWmdaDto.getId());
				atualizarMatchDoadorWmda(matchWmdaDto, match);
			}else {
				criarMatchDoadorWmda(matchWmdaDto);
			}
			
		}catch (Exception e) {
			LOGGER.error("Erro na criação do match wmda.", e);
		}
	}

	/**
	 * @param doadorWmda
	 */
	private void criarMatchDoadorWmda(MatchWmdaDTO matchWmdaDto) {
		Match matchCriado = this.matchService.criarMatchWmda(matchWmdaDto);
		this.qualificacaoMatchService.criarListaDeQualificacaoMatch(matchCriado, matchWmdaDto.getQualificacoes());
	}

	/**
	 * @param doadorWmda
	 * @param match
	 */
	private void atualizarMatchDoadorWmda(MatchWmdaDTO matchWmdaDto, Match match) {
		Match matchAtualizado = matchService.atualizarMatchWmda(match, matchWmdaDto);
		this.qualificacaoMatchService.atualizarListaDeQualificacaoMatch(matchAtualizado, matchWmdaDto.getQualificacoes());
	}

	/**
	 * @param doadorWmda
	 * @param pesquisaWmda
	 */
	@Override
	public void atualizarPesquisaWmda(Long pesquisaWmdaId, PesquisaWmdaDTO pesquisaWmdaDto) {
		PesquisaWmda pesquisaWmdaRecuperada = pesquisaWmdaRepository.findById(pesquisaWmdaId).orElse(null);
		pesquisaWmdaRepository.save(pesquisaWmdaRecuperada.parse(pesquisaWmdaDto));
	}

	@Override
	public List<PesquisaWmdaDTO> obterListaDePesquisaWmdaDeMedulaParaBusca(Long buscaId) {
		return this.listaPesquisaWmdaPorBuscaIdEStatusId(buscaId, 
				StatusPesquisaWmda.PROCESSADO.getId(), TIPO_BUSCA_DOADOR_MEDULA);
	}

	@Override
	public List<PesquisaWmdaDTO> obterListaDePesquisaWmdaDeCordaoParaBusca(Long buscaId) {
		return this.listaPesquisaWmdaPorBuscaIdEStatusId(buscaId, 
				StatusPesquisaWmda.PROCESSADO.getId(), TIPO_BUSCA_DOADOR_CORDAO);
	}
	
	private List<PesquisaWmdaDTO> listaPesquisaWmdaPorBuscaIdEStatusId(Long buscaId, Integer statusId, String tipo) {
		List<PesquisaWmda> listaPesquisaRecuperada = pesquisaWmdaRepository.findByBuscaAndStatus(buscaId, statusId);
		if (listaPesquisaRecuperada != null && !listaPesquisaRecuperada.isEmpty()) {
			return listaPesquisaRecuperada.stream()
					.filter(pesquisa -> pesquisa.getTipo().equals(tipo))
					.map(PesquisaWmdaDTO::parse).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}


}
