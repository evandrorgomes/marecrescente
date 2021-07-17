package br.org.cancer.modred.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.controller.dto.genotipo.ValorAlelo;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.GenotipoDoador;
import br.org.cancer.modred.model.IValorGenotipoBusca;
import br.org.cancer.modred.model.IValorGenotipoExpandido;
import br.org.cancer.modred.model.ValorGenotipoBuscaDoador;
import br.org.cancer.modred.model.ValorGenotipoDoador;
import br.org.cancer.modred.model.ValorGenotipoDoadorPK;
import br.org.cancer.modred.model.ValorGenotipoExpandidoDoador;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorGenotipoDoadorRepository;
import br.org.cancer.modred.service.IDnaService;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IValorGenotipoBuscaDoadorService;
import br.org.cancer.modred.service.IValorGenotipoDoadorService;

/**
 * Classe que guarda as funcionalidades envolvendo a entidade Genotipo.
 * 
 * @author Pizão
 * 
 * @param <T> Qualquer classe do tipo Exame.
 *
 */
@Service
@Transactional
public class ValorGenotipoDoadorService<T extends Exame> extends ValorGenotipoService<ValorGenotipoDoador, ValorGenotipoDoadorPK> implements IValorGenotipoDoadorService<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValorGenotipoDoadorService.class);

	@Autowired
	private IValorGenotipoDoadorRepository valorGenotipoDoadorRepository;

	@Autowired
	private IExameDoadorService<T> exameDoadorService;
	
	@Autowired
	private IValorGenotipoBuscaDoadorService valorGenotipoBuscaDoadorService;
	
	@Autowired
	private ValorGenotipoExpandidoDoadorService valorGenotipoExpandidoDoadorService;
	
	@Autowired
	private IGenotipoDoadorService<T> genotipoDoadorService;

	@Autowired
	private IDnaService dnaService;
	
	@Override
	public IRepository<ValorGenotipoDoador, ValorGenotipoDoadorPK> getRepository() {
		return valorGenotipoDoadorRepository;
	}
	
	@Override
	public ValorGenotipoDoador criarInstanciaValorGenotipo(ValorAlelo alelo) {
		return new ValorGenotipoDoador(alelo);
	}
	
	@Override
	public IValorGenotipoBusca criarInstanciaValorGenotipoBusca() {
		return new ValorGenotipoBuscaDoador();
	}

	@Override
	public IValorGenotipoExpandido criarInstanciaValorGenotipoExpandido() {
		return new ValorGenotipoExpandidoDoador();
	}
	
	@Override
	public List<ValorGenotipoDoador> gerarGenotipo(Long idDoador) {
		if (idDoador == null) {
			LOGGER.error("idDoador não foi informado para obtenção do genótipo do doador internacional.");
			throw new BusinessException("erro.requisicao");
		}

		List<T> exames = exameDoadorService.listarInformacoesExames(idDoador);
		return gerarGenotipo(exames);

	}
	
	@Override
	public void deletarValoresPorGenotipo(Long idGenotipo) {
		valorGenotipoDoadorRepository.deletarValoresPorGenotipo(idGenotipo);		
	}

	@SuppressWarnings("unchecked")	
	@Override
	public List<ValorGenotipoDoador> salvar(
			List<ValorGenotipoDoador> valoresGenotipos, GenotipoDoador genotipo, Doador doador) {

		/* Alterar depois ##Evandro## */
		valoresGenotipos.stream().forEach(valorGenotipo -> {
			
			valorGenotipo.setTipoDoador(doador.getTipoDoador());
			genotipo.setDoador(doador);
			valorGenotipo.setGenotipo(genotipo);
			
			ValorAlelo alelo = dnaService.obterAleloTipado(valorGenotipo.getId().getLocus().getCodigo(), valorGenotipo.getAlelo()); 
			valorGenotipo.setTipo(alelo.getComposicaoAlelo().getPeso());

			
//			List<ValorGenotipoBuscaDoador> valoresGenotipoBusca = 
//					(List<ValorGenotipoBuscaDoador>) gerarGenotipoBusca(valorGenotipo);
//			valoresGenotipoBusca.forEach(valorGenotipoBusca -> {
//				valorGenotipoBusca.setTipoDoador(doador.getTipoDoador());
//			});
//			valorGenotipoBuscaDoadorService.saveAll(valoresGenotipoBusca);
			
			
//			List<ValorGenotipoExpandidoDoador> valoresGenotipoExpandidoDoador = 
//					(List<ValorGenotipoExpandidoDoador>) gerarGenotipoExpandido(valorGenotipo, doador);
//			valoresGenotipoExpandidoDoador.forEach(valorGenotipoExpandidoDoador -> {				
//				valorGenotipoExpandidoDoador.setTipoDoador(doador.getTipoDoador());
//				valorGenotipoExpandidoDoador.setDoador(doador);
//			});
//			valorGenotipoExpandidoDoadorService.saveAll(valoresGenotipoExpandidoDoador);
		});
		
		return valorGenotipoDoadorRepository.saveAll(valoresGenotipos);

	}
	
	@Override
	public List<GenotipoDTO> obterGenotipoDoadorDto(Long idDoador) {
		final GenotipoDoador genotipoDoador = genotipoDoadorService.obterGenotipoPorDoador(idDoador);
		return obterGenotipoDto(genotipoDoador);
	}
	
	@Override
	public List<ValorGenotipoDoador> listarPorGenotipoDoadorId(Long idGenotipoDoador) {		
		return valorGenotipoDoadorRepository.listarPorGenotipoDoadorId(idGenotipoDoador);
	}

	@Override
	public Boolean existemValoresComDivergencia(Long idDoador) {
		return valorGenotipoDoadorRepository.existemValoresComDivergencia(idDoador);
	}

}
