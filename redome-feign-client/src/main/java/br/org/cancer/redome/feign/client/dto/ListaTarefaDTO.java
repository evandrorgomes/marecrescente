package br.org.cancer.redome.feign.client.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import br.org.cancer.redome.feign.client.domain.StatusProcesso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@Getter
public class ListaTarefaDTO {
	
	private List<Long> perfilResponsavel;
	private Long idUsuarioResponsavel; 
	private List<Long> parceiros;
	private List<Long> idsTiposTarefa; 
	private List<Long> statusTarefa;
	@Default
	private Long statusProcesso = StatusProcesso.ANDAMENTO.getId();
	private Long processoId;
	private Boolean inclusivoExclusivo; 
	private Pageable pageable;
	private Long relacaoEntidadeId; 
	private Long rmr;
	private Long idDoador;
	private Long tipoProcesso;
	@Default
	private Boolean atribuidoQualquerUsuario = false; 
	private Long tarefaPaiId;
	@Default
	private Boolean tarefaSemAgendamento = true; 
	private Long idUsuarioLogado;
		
	
	public static List<Long> perfis(Map<Long, List<Long>> dados) {
		List<Long> listaPerfis = new ArrayList<>();
		dados.forEach((key, value) -> {
			listaPerfis.add(key);
		});
		return listaPerfis;
	}

	public static List<Long> tipos(Map<Long, List<Long>> dados) {
		List<Long> listaTipos = new ArrayList<>();
		dados.forEach((key, value) -> {
			value.forEach(tipo ->{
				listaTipos.add(tipo);
			});
		});
		return listaTipos;
	}

}
