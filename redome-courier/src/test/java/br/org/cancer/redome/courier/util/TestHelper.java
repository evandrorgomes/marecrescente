package br.org.cancer.redome.courier.util;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.courier.exception.BusinessException;
import br.org.cancer.redome.feign.client.domain.FasesWorkup;
import br.org.cancer.redome.feign.client.domain.TiposSolicitacao;
import br.org.cancer.redome.feign.client.dto.BuscaDTO;
import br.org.cancer.redome.feign.client.dto.CentroTransplanteDTO;
import br.org.cancer.redome.feign.client.dto.DoadorDTO;
import br.org.cancer.redome.feign.client.dto.MatchDTO;
import br.org.cancer.redome.feign.client.dto.PacienteDTO;
import br.org.cancer.redome.feign.client.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.feign.client.dto.TarefaDTO;
import br.org.cancer.redome.feign.client.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.feign.client.dto.UsuarioDTO;

public abstract class TestHelper {
	
	
	public static SolicitacaoWorkupDTO criarSolicitacao(Long id, TiposSolicitacao tipo, UsuarioDTO usuario, MatchDTO match,
			UsuarioDTO usuarioResponsavel, FasesWorkup faseWorkup) {
		return SolicitacaoWorkupDTO.builder().id(id).status(0)
				.tipoSolicitacao(TipoSolicitacaoDTO.builder().id(tipo.getId()).build()).usuario(usuario).match(match)
				.usuarioResponsavel(usuarioResponsavel).faseWorkup(faseWorkup.getId()).build();
	}

	public static MatchDTO criarMatch(Long idMatch, Long idDoador, String identificacaoDoador, BuscaDTO busca) {
		return MatchDTO.builder().id(idMatch)
				.doador(DoadorDTO.builder().id(idDoador).Identificacao(identificacaoDoador).build()).busca(busca)

				.build();
	}

	public static BuscaDTO criarBusca(Long idCentroTransplante, String nomeCentroTransplante, Long rmr) {
		return BuscaDTO.builder()
				.centroTransplante(
						CentroTransplanteDTO.builder().id(idCentroTransplante).nome(nomeCentroTransplante).build())
				.paciente(PacienteDTO.builder().rmr(rmr).build()).build();
	}
	
	public static String montarRetornoListarTarfaJson(List<TarefaDTO> tarefas) {
		
		int totalPages = 0;
		int totalElements = 0;
		String tarefasJson = "";
		if (CollectionUtils.isNotEmpty(tarefas)) {
			totalElements = tarefas.size();
			totalPages = 1;
			tarefasJson = tarefas.stream().map(tarefa -> {
				try {
					return BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa);
				} catch (JsonProcessingException e) {
					throw new BusinessException(e.getMessage());					
				}
			})
			.collect(Collectors.joining(","));
		}
		
		
		
		String retorno = "{\n" + 
				"    \"pageable\": {\n" + 
				"        \"sort\": {\n" + 
				"            \"sorted\": false,\n" + 
				"            \"unsorted\": true,\n" + 
				"            \"empty\": true\n" + 
				"        },\n" + 
				"        \"pageSize\": 1,\n" + 
				"        \"pageNumber\": 0,\n" + 
				"        \"offset\": 0,\n" + 
				"        \"unpaged\": false,\n" + 
				"        \"paged\": true\n" + 
				"    },\n" + 
				"    \"totalElements\": " + totalElements + ",\n" + 
				"    \"totalPages\": " + totalPages + ",\n" + 
				"    \"last\": true,\n" + 
				"    \"first\": true,\n" + 
				"    \"sort\": {\n" + 
				"        \"sorted\": false,\n" + 
				"        \"unsorted\": true,\n" + 
				"        \"empty\": true\n" + 
				"    },\n" + 
				"    \"size\": 1,\n" + 
				"    \"number\": 0,\n" + 
				"    \"numberOfElements\": 0,\n" + 
				"    \"empty\": true,\n" + 
				"    \"content\": [\n" +
				"        " + tarefasJson + "\n" +
				"    ]\n" +
				"}";
		
		return retorno;
	}

	

}
