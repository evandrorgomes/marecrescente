package br.org.cancer.redome.tarefa.service.impl;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.redome.tarefa.dto.LocusExameHlaWmdaDTO;
import br.org.cancer.redome.tarefa.dto.PacienteWmdaDTO;
import br.org.cancer.redome.tarefa.dto.ResultadoDoadorWmdaDTO;
import br.org.cancer.redome.tarefa.dto.ResultadoPesquisaWmdaDTO;
import br.org.cancer.redome.tarefa.dto.WmdaDto;
import br.org.cancer.redome.tarefa.dto.WmdaDto.WmdaDna;
import br.org.cancer.redome.tarefa.dto.WmdaDto.WmdaHla;
import br.org.cancer.redome.tarefa.dto.WmdaDto.WmdaIdm;
import br.org.cancer.redome.tarefa.dto.WmdaSearchDto;
import br.org.cancer.redome.tarefa.dto.WmdaSearchDto.WmdaRequests;
import br.org.cancer.redome.tarefa.integracao.client.impl.RestWmdaClient;
import br.org.cancer.redome.tarefa.model.Locus;
import br.org.cancer.redome.tarefa.service.IWmdaService;

/**
 * Implementacao da interface {@link IWmdaService}
 * @author Filipe Paes
 *
 */
@Service
public class WmdaService implements IWmdaService {
	
	private static final String PARAM_ALGORITHM = "F";
	private static final Long PARAM_MATCH_ENGINE = 1L;

	private static final String URL_PATIENTS = "/patients";
	private static final String URL_SEARCHES = "/searches";
	private static final String URL_MATCH_ENGINE = "/matchengine/";
	private static final String URL_RESULTS = "/results/";
	
	private static final String COMENTARIO_CADASTRO_API = "Paciente cadastrado via API";
	
	@Autowired
	private ObjectMapper jsonObjectMapper;
	
	@Autowired
	private RestWmdaClient restWmdaClient;
	
	@Override
	public ResultadoPesquisaWmdaDTO criarPacienteWmda(PacienteWmdaDTO paciente) throws IOException {
		
		ResultadoPesquisaWmdaDTO resPesquisaDto = null; 
		
		String resWmda = restWmdaClient.post(URL_PATIENTS, gerarObjetoWmda(paciente));

		resPesquisaDto = jsonObjectMapper.readValue(resWmda, ResultadoPesquisaWmdaDTO.class);
			
		return resPesquisaDto;
	} 
	
	@Override
	public ResultadoPesquisaWmdaDTO atualizarPacienteWmda(PacienteWmdaDTO paciente) throws IOException {

		ResultadoPesquisaWmdaDTO resPesquisaDto = null; 

		String resWmda = restWmdaClient.put(URL_PATIENTS+"/" + paciente.getWmdaId(), gerarObjetoWmda(paciente));

		resPesquisaDto = jsonObjectMapper.readValue(resWmda, ResultadoPesquisaWmdaDTO.class);
		
		return resPesquisaDto;
	}

	@Override
	public ResultadoPesquisaWmdaDTO buscarSearchIdPorWmdaId(String wmdaId, String tipoBusca) {

		ResultadoPesquisaWmdaDTO resPesquisaDto = null; 

		String resWmda = restWmdaClient.post(URL_PATIENTS+"/" + wmdaId + URL_SEARCHES, gerarObjetoSearchWmda(tipoBusca));

		try {

			resPesquisaDto = jsonObjectMapper.readValue(resWmda, ResultadoPesquisaWmdaDTO.class);
	
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return resPesquisaDto;
	}

	@Override
	public ResultadoPesquisaWmdaDTO buscarSearchResultsIdPorWmdaId(String wmdaId) {
		
		ResultadoPesquisaWmdaDTO resPesquisaDto = null;
		
		String resWmda = restWmdaClient.get(URL_PATIENTS+"/" + wmdaId + URL_SEARCHES);

		try {
		
			resPesquisaDto = jsonObjectMapper.readValue(resWmda, ResultadoPesquisaWmdaDTO.class);

		} catch (IOException e) {
			e.printStackTrace();
		} 
		return resPesquisaDto;
	} 

	@Override
	public ResultadoPesquisaWmdaDTO buscarSearchResultsDoadores(String wmdaId, Long searchResultId) throws IOException {
		
		ResultadoPesquisaWmdaDTO resPesquisaDto = null;
		
		String resWmda = restWmdaClient.get(URL_PATIENTS+"/" + wmdaId + URL_MATCH_ENGINE + PARAM_MATCH_ENGINE +
				URL_RESULTS + searchResultId + "?algorithm=" + PARAM_ALGORITHM);

		try {
			resPesquisaDto = jsonObjectMapper.readValue(resWmda, ResultadoPesquisaWmdaDTO.class);

//			resPesquisaDto.setJsonWmda(jsonObjectMapper.writeValueAsString(resWmda));
			if(resPesquisaDto.getDonors() != null && !resPesquisaDto.getDonors().isEmpty()) {
				converterJsonDonors(resPesquisaDto.getDonors());
			}else {
				resPesquisaDto.setDonors(new ArrayList<>());
			}
		
		} catch (IOException e) {
			throw new IOException("Erro na comunicação com o servidor WMDA");
		}
		return resPesquisaDto;
	}

	@Override
	public ResultadoPesquisaWmdaDTO converterMatchsDoadoresWmdaParaResultadoPesquisa(String jsonWmda) throws IOException {
		ResultadoPesquisaWmdaDTO resPesquisaDto = null;

		resPesquisaDto = jsonObjectMapper.readValue(jsonWmda, ResultadoPesquisaWmdaDTO.class);
		if(resPesquisaDto.getDonors() != null && !resPesquisaDto.getDonors().isEmpty()) {
			converterJsonDonors(resPesquisaDto.getDonors());
		}else {
			resPesquisaDto.setDonors(new ArrayList<>());
		}
		return resPesquisaDto;
	}
	
	/**
	 * @return
	 */
	private WmdaSearchDto gerarObjetoSearchWmda(String tipoBusca) {
		WmdaSearchDto wmdaSearchDto = new WmdaSearchDto();
		wmdaSearchDto.setRepeatable(true);
		List<WmdaRequests> requests = new ArrayList<>();
		WmdaRequests request = wmdaSearchDto.new WmdaRequests(PARAM_MATCH_ENGINE, tipoBusca);
		requests.add(request);
		wmdaSearchDto.setRequests(requests);
		return wmdaSearchDto;
	} 
	
	private WmdaDto gerarObjetoWmda(PacienteWmdaDTO paciente) {
		WmdaDto wmdaDto = new WmdaDto();
		
		wmdaDto.setpId(String.valueOf(paciente.getRmr()));

		WmdaDna dnaA = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_A, paciente.getLocusExame());
		WmdaDna dnaB = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_B, paciente.getLocusExame());
		WmdaDna dnaC = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_C, paciente.getLocusExame());
		WmdaDna dnaDrb1 = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_DRB1, paciente.getLocusExame());
		WmdaDna dnaDqb1 = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_DQB1, paciente.getLocusExame());
		WmdaDna dnaDpa1 = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_DPA1, paciente.getLocusExame()); 
		WmdaDna dnaDpb1 = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_DPB1, paciente.getLocusExame());
		WmdaDna dnaDrb3 = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_DRB3, paciente.getLocusExame());
		WmdaDna dnaDrb4 = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_DRB4, paciente.getLocusExame());
		WmdaDna dnaDrb5 = obterValoresLocus(wmdaDto.new WmdaDna(),Locus.LOCUS_DRB5, paciente.getLocusExame());
		
		WmdaHla wmdaHla = wmdaDto.new WmdaHla();
		wmdaHla.setA(dnaA != null? wmdaDto.new WmdaFieldsDna(dnaA):null);
		wmdaHla.setB(dnaB != null? wmdaDto.new WmdaFieldsDna(dnaB):null);
		wmdaHla.setC(dnaC != null? wmdaDto.new WmdaFieldsDna(dnaC):null);
		wmdaHla.setDpa1(dnaDpa1 != null? wmdaDto.new WmdaFieldsDna(dnaDpa1):null);
		wmdaHla.setDpb1(dnaDpb1 != null? wmdaDto.new WmdaFieldsDna(dnaDpb1):null);
		wmdaHla.setDrb1(dnaDrb1 != null? wmdaDto.new WmdaFieldsDna(dnaDrb1):null);
		wmdaHla.setDqb1(dnaDqb1 != null? wmdaDto.new WmdaFieldsDna(dnaDqb1):null);
		wmdaHla.setDrb3(dnaDrb3 != null? wmdaDto.new WmdaFieldsDna(dnaDrb3):null);
		wmdaHla.setDrb4(dnaDrb4 != null? wmdaDto.new WmdaFieldsDna(dnaDrb4):null);
		wmdaHla.setDrb5(dnaDrb5 != null? wmdaDto.new WmdaFieldsDna(dnaDrb5):null);
		wmdaDto.setHla(wmdaHla);
		
		WmdaIdm idm = wmdaDto.new WmdaIdm();
		idm.setCmvStatus("N");
		wmdaDto.setIdm(idm);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		wmdaDto.setBirthDate(formatter.format(paciente.getDataNascimento()));
		wmdaDto.setPool("BR");
		wmdaDto.setAbo(paciente.getAbo().substring(0, paciente.getAbo().length() - 1));
		wmdaDto.setRhesus(paciente.getAbo().substring(paciente.getAbo().length() - 1).equals("+")? "P":"N");
		
		wmdaDto.setWeight(paciente.getPeso().intValue());
		wmdaDto.setSex(paciente.getSexo());
		wmdaDto.setLegalTerms(Boolean.TRUE);
		wmdaDto.setComment(COMENTARIO_CADASTRO_API);
		
		return wmdaDto;
	}

	private WmdaDna obterValoresLocus(WmdaDna wmdaDna, String nomeLocus, List<LocusExameHlaWmdaDTO> valoresGenotipo) {
		List<LocusExameHlaWmdaDTO> valores = valoresGenotipo.stream().filter(g -> nomeLocus.equals(g.getLocus())).collect(Collectors.toList());
		if(!valores.isEmpty()) {
			wmdaDna.setField1(valores.get(0).getPrimeiroAlelo());			
			wmdaDna.setField2(valores.get(0).getSegundoAlelo());
			return wmdaDna;
		}
		return null;
	}
	
	/**
	 * @param List<DoadorDrWmdaDTO> donors
	 */
	public void converterJsonDonors(List<ResultadoDoadorWmdaDTO> donors) {
		donors.forEach(x -> {
			String str = "";
			try {
				str = jsonObjectMapper.writeValueAsString(x);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			x.setJsonDonors(str);
		});
	} 

}
