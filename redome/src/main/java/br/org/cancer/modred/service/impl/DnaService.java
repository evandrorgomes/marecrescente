package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.genotipo.Alelo;
import br.org.cancer.modred.controller.dto.genotipo.Antigeno;
import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.controller.dto.genotipo.GrupoG;
import br.org.cancer.modred.controller.dto.genotipo.GrupoP;
import br.org.cancer.modred.controller.dto.genotipo.Nmdp;
import br.org.cancer.modred.controller.dto.genotipo.Sorologico;
import br.org.cancer.modred.controller.dto.genotipo.ValorAlelo;
import br.org.cancer.modred.model.ValorG;
import br.org.cancer.modred.model.ValorNmdp;
import br.org.cancer.modred.model.ValorP;
import br.org.cancer.modred.model.ValorSorologico;
import br.org.cancer.modred.persistence.ISplitValorGRepository;
import br.org.cancer.modred.persistence.ISplitValorPRepository;
import br.org.cancer.modred.persistence.IValorGRepository;
import br.org.cancer.modred.persistence.IValorNmdpRepository;
import br.org.cancer.modred.persistence.IValorPRepository;
import br.org.cancer.modred.persistence.IValorSorologicoRepository;
import br.org.cancer.modred.service.IDnaService;

/**
 * @author Pizão
 *
 * Classe que centraliza as funcionalidades envolvendo processamentos de valor HLA.
 * São funções facilitadoras para listagem de valores válidos, criação valores HLA de acordo
 * com o tipo, etc.
 * Centralizado aqui está toda manipulação comum a geração do genótipo.
 */
@Service
@Transactional
public class DnaService implements IDnaService {

	@Autowired
	private IValorNmdpRepository nmdpRepo;

	@Autowired
	private IValorPRepository grupoPRepo;

	@Autowired
	private IValorGRepository grupoGRepo;
	
	@Autowired
	private ISplitValorGRepository splitValorGRepo;
	
	@Autowired
	private ISplitValorPRepository splitValorPRepo;

	@Autowired
	private IValorSorologicoRepository sorologicoRepo;
	
	/**
	 * Guarda o cache das pesquisas pelos códigos (NMPD, Grupo G e Grupo P), somente durante a montagem do genótipo. No final é
	 * descartado.
	 */
	private Map<String, List<String>> cacheNmdp;
	private Map<String, List<String>> cacheGrupoG;
	private Map<String, List<String>> cacheGrupoP;
	private Map<String, List<String>> cacheSorologico;
	private Map<String, List<String>> cacheGrupo;
	private Map<String, List<String>> cacheGrupoNmdp;

	@Override
	public List<String> listarValoresCompativeisNmdp(Nmdp nmdp) {
		return listarValoresCompativeisNmdp(nmdp.getAntigeno(), nmdp.getCodigo());
	}

	@Override
	public List<String> listarValoresCompativeisNmdp(String antigeno, String codigoNmdp) {
		ValorNmdp valorNmdp = nmdpRepo.findById(codigoNmdp).orElse(null);
		if(valorNmdp == null || valorNmdp.getSubTipos().equals("")) {
			System.out.println(valorNmdp.getSubTipos());
		}
		String[] subTipos = valorNmdp.getSubTipos().split("/");

		if (!valorNmdp.getAgrupado()) {
			List<String> subTiposAgrupados = new ArrayList<String>();
			for (String subTipo : subTipos) {
				subTiposAgrupados.add(antigeno + ":" + subTipo);
			}
			return subTiposAgrupados;
		}

		return Arrays.asList(subTipos);
	}
	
	private List<String> obterGrupoCompativel(String codigoLocus, List<String> valoresAlelico) {
		List<String> gruposG = splitValorGRepo.obterGrupo(codigoLocus, valoresAlelico);
		List<String> gruposP = splitValorPRepo.obterGrupo(codigoLocus, valoresAlelico);
		
		gruposG.addAll(gruposP);
		return gruposG;  
	}

	@Override
	public List<String> listarValoresCompativeisGrupoG(String codigoG, String nomeGrupo) {
		ValorG valorG = grupoGRepo.obterValorG(codigoG, nomeGrupo);
		if (valorG != null) {
			return Arrays.asList(valorG.getGrupo().split("/"));
		}
		return null;
	}

	@Override
	public List<String> listarValoresCompativeisGrupoP(String codigoP, String nomeGrupo) {
		ValorP valorP = grupoPRepo.obterValorP(codigoP, nomeGrupo);
		if (valorP != null) {
			return Arrays.asList(valorP.getGrupo().split("/"));
		}
		return null;
	}

	@Override
	public List<String> listarValoresCompativeisSorologico(String codigoLocus, String antigeno) {
		ValorSorologico valorSorologico = sorologicoRepo.obterValorSorologico(codigoLocus, antigeno);
		if (valorSorologico != null) {
			return Arrays.asList(valorSorologico.getValores().split("/"));
		}
		return null;
	}
	
	/**
	 * Lista os valores compatíveis com NMDP informado e guarda em cache, caso exista uma nova consulta para o mesmo.
	 * 
	 * @param nmdp que deverá ser utilizado na pesquisa.
	 * @return lista de valores válidos.
	 */
	private List<String> listarValoresCompativeisSorologicoCacheado(String codigoLocus, String antigeno) {
		List<String> valoresCompativeis = null;

		criarCacheTemporarioSeNecessario();

		if (cacheSorologico.containsKey(codigoLocus + antigeno)) {
			valoresCompativeis = cacheSorologico.get(codigoLocus + antigeno);
		}
		else {
			valoresCompativeis = listarValoresCompativeisSorologico(codigoLocus, antigeno);
			cacheSorologico.put(codigoLocus + antigeno, valoresCompativeis);
		}

		return valoresCompativeis;
	}

	/**
	 * Lista os valores compatíveis com GRUPO G informado e guarda em cache, caso exista uma nova consulta para o mesmo.
	 * 
	 * @param grupoG que deverá ser utilizado na pesquisa.
	 * @return lista de valores válidos.
	 */
	private List<String> listarValoresCompativeisGrupoGCacheado(String codigoLocus, String nomeGrupo) {
		List<String> valoresCompativeis = null;

		criarCacheTemporarioSeNecessario();

		if (cacheGrupoG.containsKey(codigoLocus + nomeGrupo)) {
			valoresCompativeis = cacheGrupoG.get(codigoLocus + nomeGrupo);
		}
		else {
			valoresCompativeis = listarValoresCompativeisGrupoG(codigoLocus, nomeGrupo);
			cacheGrupoG.put(codigoLocus + nomeGrupo, valoresCompativeis);
		}

		return valoresCompativeis;
	}

	/**
	 * Lista os valores compatíveis com GRUPO P informado e guarda em cache, caso exista uma nova consulta para o mesmo.
	 * 
	 * @param grupoP que deverá ser utilizado na pesquisa.
	 * @return lista de valores válidos.
	 */
	private List<String> listarValoresCompativeisGrupoPCacheado(String codigoLocus, String nomeGrupo) {
		List<String> valoresCompativeis = null;

		if (cacheGrupoP.containsKey(codigoLocus + nomeGrupo)) {
			valoresCompativeis = cacheGrupoP.get(codigoLocus + nomeGrupo);
		}
		else {
			valoresCompativeis = listarValoresCompativeisGrupoP(codigoLocus, nomeGrupo);
			cacheGrupoP.put(codigoLocus + nomeGrupo, valoresCompativeis);
		}

		return valoresCompativeis;
	}

	/**
	 * Cria um hashmap temporário para guardar cache dos códigos 
	 * NMDP já consultados para reutilização sem necessidade de ir
	 * ao banco.
	 */
	@Override
	@PostConstruct
	public void criarCacheTemporarioSeNecessario() {
		cacheNmdp = cacheNmdp == null ? new HashMap<>() : cacheNmdp;
		cacheGrupoG = cacheGrupoG == null ? new HashMap<>() : cacheGrupoG;
		cacheGrupoP = cacheGrupoP == null ? new HashMap<>() : cacheGrupoP;
		cacheSorologico = cacheSorologico == null ? new HashMap<>() : cacheSorologico;		
		cacheGrupo = cacheGrupo == null ? new HashMap<>() : cacheGrupo;
		cacheGrupoNmdp = cacheGrupoNmdp == null ? new HashMap<>() : cacheGrupoNmdp;		
	}

	/**
	 * Lista os valores compatíveis com NMDP informado e guarda em cache, caso exista uma nova consulta para o mesmo.
	 * 
	 * @param nmdp que deverá ser utilizado na pesquisa.
	 * @return lista de valores válidos.
	 */
	private List<String> listarValoresCompativeisNmdpCacheado(String antigeno, String codigoNmdp) {
		List<String> valoresCompativeis = null;

		criarCacheTemporarioSeNecessario();

		if (cacheNmdp.containsKey(antigeno + codigoNmdp)) {
			valoresCompativeis = cacheNmdp.get(antigeno + codigoNmdp);
		}
		else {
			valoresCompativeis = listarValoresCompativeisNmdp(antigeno, codigoNmdp);
			cacheNmdp.put(antigeno + codigoNmdp, valoresCompativeis);
		}

		return valoresCompativeis;
	}
	
	private List<String> listarGruposCompativeisNmdpCacheado(String codigoLocus, String antigeno, String codigoNmdp, List<String> valores) {
		List<String> gruposCompativeis = null;

		criarCacheTemporarioSeNecessario();

		if (cacheGrupoNmdp.containsKey(codigoLocus + antigeno + codigoNmdp)) {
			gruposCompativeis = cacheGrupoNmdp.get(codigoLocus + antigeno + codigoNmdp);
		}
		else {
			gruposCompativeis = obterGrupoCompativel(codigoLocus, valores);
			cacheGrupoNmdp.put(codigoLocus + antigeno + codigoNmdp, gruposCompativeis);
		}

		return gruposCompativeis;
	}

	/**
	 * Limpa o cache com os código NMDP + valores.
	 */
	@Override
	public void clearCacheTemporario() {
		cacheNmdp.clear();
		cacheGrupoG.clear();
		cacheGrupoP.clear();
	}
	
	@Override
	public ValorAlelo obterAleloTipado(String codigoLocus, String valorAlelico) {
		ValorAlelo alelo = null;
		List<String> grupos = null;
		
		switch (ComposicaoAlelo.obterTipo(valorAlelico)) {
			case ANTIGENO:
				alelo = new Antigeno(codigoLocus, valorAlelico);
				break;
			case SOROLOGICO:
				List<String> valoresCompativeisSorologicoCacheado = listarValoresCompativeisSorologicoCacheado(codigoLocus,
						valorAlelico);
				alelo = new Sorologico(codigoLocus, valorAlelico, valoresCompativeisSorologicoCacheado);
				break;
			case ALELO:
				grupos = obterGrupoCacheado(codigoLocus, valorAlelico);
				alelo = new Alelo(codigoLocus, valorAlelico, grupos);
				break;
			case NMDP:
				List<String> listarValoresCompativeisNmdpCacheado = listarValoresCompativeisNmdpCacheado(
						Nmdp.obterAntigeno(valorAlelico), Nmdp.obterCodigoNmdp(valorAlelico));
				grupos = listarGruposCompativeisNmdpCacheado(codigoLocus, Nmdp.obterAntigeno(valorAlelico), 
						Nmdp.obterCodigoNmdp(valorAlelico), listarValoresCompativeisNmdpCacheado);
				
				alelo = new Nmdp(codigoLocus, valorAlelico, listarValoresCompativeisNmdpCacheado, grupos);
				break;
			case GRUPO_P:
				List<String> valoresCompativeisGrupoPCacheado = listarValoresCompativeisGrupoPCacheado(codigoLocus, valorAlelico);
				alelo = new GrupoP(codigoLocus, valorAlelico, valoresCompativeisGrupoPCacheado);
				break;
			case GRUPO_G:
				List<String> valoresCompativeisGrupoGCacheado = listarValoresCompativeisGrupoGCacheado(codigoLocus, valorAlelico);
				alelo = new GrupoG(codigoLocus, valorAlelico, valoresCompativeisGrupoGCacheado);
				break;
		}

		return alelo;
	}

	private List<String> obterGrupoCacheado(String codigoLocus, String valorAlelico) {
		List<String> grupos = null;

		criarCacheTemporarioSeNecessario();

		if (cacheGrupo.containsKey(codigoLocus + valorAlelico)) {
			grupos = cacheGrupo.get(codigoLocus + valorAlelico);
		}
		else {
			grupos = obterGrupoCompativel(codigoLocus, Arrays.asList(valorAlelico));
			if (grupos != null && !grupos.isEmpty()) {
				cacheGrupo.put(codigoLocus + valorAlelico, grupos);
			}
		}

		return grupos;
	}

}
