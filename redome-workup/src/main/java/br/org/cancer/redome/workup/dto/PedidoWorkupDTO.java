package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.org.cancer.redome.workup.model.ArquivoPedidoWorkup;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class PedidoWorkupDTO implements Serializable {

	private static final long serialVersionUID = 3841109119515428856L;
	
	private Long id;
	private Long idTipo;
	private Long idStatus;
	private Long idSolicitacao;
	private Long idCentroTransplante;	
	private Long idCentroColeta;
	private LocalDate dataCondicionamento;
	private LocalDate dataInfusao;
	private LocalDate dataExame;
	private LocalDate dataResultado;
	private LocalDate dataInternacao;
	private LocalDate dataColeta;
	private Boolean criopreservacao;
	private String observacaoPlanoWorkup;
	private String observacaoAprovaPlanoWorkup;
	private String identificacao;
	private Long rmr;
	private List<PedidoAdicionalWorkupDTO> pedidosAdicionaisWorkup;
	private List<ArquivoPedidoWorkupDTO> arquivosPedidoWorkup;
	private Long idFonteOpcao1;
	private Long idFonteOpcao2;
	
	public static PedidoWorkupDTO parse(PedidoWorkup pedidoWorkup, SolicitacaoDTO solicitacao, List<ArquivoPedidoWorkup> arquivosPedido) {
		
		List<PedidoAdicionalWorkupDTO> pedidosAdicionais = new ArrayList<>();
		
		List<ArquivoPedidoWorkupDTO> arquivos = arquivosPedido.stream()
				.map(ArquivoPedidoWorkupDTO::new).collect(Collectors.toList());
		
		if(!pedidoWorkup.getPedidosAdicionaisWorkup().isEmpty()) {
			pedidosAdicionais = pedidoWorkup.getPedidosAdicionaisWorkup().stream()
				.map(PedidoAdicionalWorkupDTO::new).collect(Collectors.toList());
		}
		
		String identificacao = solicitacao.getMatch().getDoador().getIdentificacao(); 
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();		
		
		return PedidoWorkupDTO.builder()
			.id(pedidoWorkup.getId())
			.idTipo(pedidoWorkup.getTipo())
			.idStatus(pedidoWorkup.getStatus())
			.idSolicitacao(solicitacao.getId())
			.idCentroTransplante(pedidoWorkup.getCentroTransplante())
			.idCentroColeta(pedidoWorkup.getCentroColeta())
			.dataExame(pedidoWorkup.getDataExame())
			.dataResultado(pedidoWorkup.getDataResultado())
			.dataInternacao(pedidoWorkup.getDataInternacao())
			.dataColeta(pedidoWorkup.getDataColeta())
			.dataCondicionamento(pedidoWorkup.getDataCondicionamento())
			.dataInfusao(pedidoWorkup.getDataInfusao())
			.observacaoPlanoWorkup(pedidoWorkup.getObservacaoPlanoWorkup())
			.observacaoAprovaPlanoWorkup(pedidoWorkup.getObservacaoAprovaPlanoWorkup())
			.rmr(rmr)
			.identificacao(identificacao)
			.arquivosPedidoWorkup(arquivos)
			.pedidosAdicionaisWorkup(pedidosAdicionais)
			.build();
	}
}
