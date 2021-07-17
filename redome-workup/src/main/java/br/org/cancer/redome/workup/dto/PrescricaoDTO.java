package br.org.cancer.redome.workup.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.model.PrescricaoCordao;
import br.org.cancer.redome.workup.model.PrescricaoMedula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para prescrição.
 * 
 * @author ergomes
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrescricaoDTO {

	private Long id;

	private Long idTipoPrescricao;
	
	private Long idDoador;

	private Long idTipoDoador;
	
	private Long rmr;

	private Long idCentroTransplante;
	
	private PrescricaoMedulaDTO medula;
	
	private PrescricaoCordaoDTO cordao;
	
	private List<ArquivoPrescricaoDTO> arquivosPrescricao;

	private List<ArquivoPrescricaoDTO> arquivosPrescricaoJustificativa;
	
	
	public static PrescricaoDTO parse(Prescricao prescricao, SolicitacaoDTO solicitacao) {

		PrescricaoDTO prescricaoDTO = null;
		
		List<ArquivoPrescricaoDTO> arquivosPrescricao = prescricao.getArquivosPrescricao().stream()
				.filter(arquivo -> !arquivo.getJustificativa())
				.map(ArquivoPrescricaoDTO::new).collect(Collectors.toList());

		List<ArquivoPrescricaoDTO> arquivosPrescricaoJustificativa = prescricao.getArquivosPrescricao().stream()
				.filter(arquivo -> arquivo.getJustificativa())
				.map(ArquivoPrescricaoDTO::new).collect(Collectors.toList());
		
		if(prescricao.isPrescricaoMedula()) {

			PrescricaoMedula prescricaoMedula = (PrescricaoMedula) prescricao;
			
			PrescricaoMedulaDTO retornoMedula = PrescricaoMedulaDTO.parse(prescricaoMedula);

			prescricaoDTO = PrescricaoDTO.builder()
					.id(prescricao.getId())
					.idTipoPrescricao(prescricao.getTipoPrescricao())
					.idDoador(solicitacao.getMatch().getDoador().getId())
					.idTipoDoador(solicitacao.getMatch().getDoador().getIdTipoDoador())
					.rmr(solicitacao.getMatch().getBusca().getPaciente().getRmr())
					.medula(retornoMedula)
					.arquivosPrescricao(arquivosPrescricao)
					.arquivosPrescricaoJustificativa(arquivosPrescricaoJustificativa)
					.build();
		}else {
		
			PrescricaoCordao prescricaoCordao = (PrescricaoCordao) prescricao;

			PrescricaoCordaoDTO retornoCordao = PrescricaoCordaoDTO.parse(prescricaoCordao);

			prescricaoDTO = PrescricaoDTO.builder()
					.id(prescricao.getId())
					.idTipoPrescricao(prescricao.getTipoPrescricao())
					.idDoador(solicitacao.getMatch().getDoador().getId())
					.idTipoDoador(solicitacao.getMatch().getDoador().getIdTipoDoador())
					.rmr(solicitacao.getMatch().getBusca().getPaciente().getRmr())
					.idCentroTransplante(solicitacao.getMatch().getBusca().getCentroTransplante().getId())
					.cordao(retornoCordao)
					.arquivosPrescricao(arquivosPrescricao)
					.arquivosPrescricaoJustificativa(arquivosPrescricaoJustificativa)
					.build();
		}
		return prescricaoDTO;
	}
}
