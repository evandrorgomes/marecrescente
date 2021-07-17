package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ConsultaTarefasWorkupDTO implements Serializable {
	
	private static final long serialVersionUID = -5842538290951828221L;

	/*##### Parametros da lista de consulta #####*/ 
	
	private Long idTarefa;
	private Long idTipoTarefa;
	private Long statusTarefa;
	private Long idPrescricao;
	private Long idDoador;
	private Long idPedidoWorkup;
	private Long idCentroTransplante;
	private String nomePaciente;
	private LocalDateTime dataNascimentoPaciente;
	private Long idTipoPrescricao; 
	private Long idResultadoWorkup;
	private Long idPedidoColeta;
	private Long idPedidoLogistica;
	private Long idPedidoAdicional;
	private String nomeUsuarioResponsavelTarefa;
	
	
	/*##### Campos da lista de consulta *#####/ 
	
	/*# Centro de Coleta e transplante #*/
	
	private Long rmr;
	private String identificacaoDoador;
	private String registroOrigem;
	private String tipoPrescricao;
	private LocalDateTime dataSugWorkup;
	private LocalDateTime dataSugColeta;
	private LocalDateTime dataWorkup;
	private LocalDateTime dataColeta;
	private String acao;


	/*# Analista de workup #*/

	private String nomeCentroTransplante;
	private String medicoResponsavelPrescricao;
	private LocalDateTime dataPrescricao;

	
	
	/**
	 * Método que cria uma instância de ConsultaTarefasWorkupDTO com os dados de um vetor. 
	 * 
	 * @param object - dados da view ConsultaTarefasWorkupDTO
	 * @return ConsultaTarefasWorkupDTO
	 */
	public static ConsultaTarefasWorkupDTO popularViewTarefaWorkup(Object[] object) {
		
		ConsultaTarefasWorkupDTO view = new ConsultaTarefasWorkupDTO();

		if (object[0] != null) {
			view.setRmr(Long.parseLong(object[0].toString()));			
		}
		if (object[1] != null) {
			view.setIdentificacaoDoador(object[1].toString());			
		}
		if (object[2] != null) {
			view.setRegistroOrigem(object[2].toString());			
		}
		if (object[3] != null) {
			view.setTipoPrescricao(object[3].toString());
		}
		if (object[4] != null) {
			view.setDataSugWorkup(((Timestamp) object[4]).toLocalDateTime());
		}
		if (object[5] != null) {
			view.setDataSugColeta(((Timestamp) object[5]).toLocalDateTime());
		}
		if (object[6] != null) {
			view.setDataWorkup(((Timestamp) object[6]).toLocalDateTime());
		}
		if (object[7] != null) {
			view.setDataColeta(((Timestamp) object[7]).toLocalDateTime());
		}
		if (object[8] != null) {
			view.setAcao(object[8].toString());			
		}
		if (object[9] != null) {
			view.setNomeCentroTransplante(object[9].toString());
		}
		if (object[10] != null) {
			view.setMedicoResponsavelPrescricao(object[10].toString());			
		}
		if (object[11] != null) {
			view.setDataPrescricao(((Timestamp) object[11]).toLocalDateTime());
		}
		if (object[12] != null) {
			view.setIdTarefa(Long.parseLong(object[12].toString()));			
		}
		if (object[13] != null) {
			view.setIdTipoTarefa(Long.parseLong(object[13].toString()));			
		}
		if (object[14] != null) {
			view.setStatusTarefa(Long.parseLong(object[14].toString()));			
		}
		if (object[15] != null) {
			view.setIdPrescricao(Long.parseLong(object[15].toString()));			
		}
		if (object[16] != null) {
			view.setIdPedidoWorkup(Long.parseLong(object[16].toString()));			
		}
		if (object[17] != null) {
			view.setIdCentroTransplante(Long.parseLong(object[17].toString()));			
		}
		if (object[18] != null) {
			view.setNomePaciente(object[18].toString());			
		}
		if (object[19] != null) {
			view.setDataNascimentoPaciente(((Timestamp) object[19]).toLocalDateTime());
		}
		if (object[20] != null) {
			view.setIdTipoPrescricao(Long.parseLong(object[20].toString()));			
		}
		if (object[21] != null) {
			view.setIdResultadoWorkup(Long.parseLong(object[21].toString()));			
		}
		if (object[22] != null) {
			view.setIdPedidoColeta(Long.parseLong(object[22].toString()));			
		}
		if (object[23] != null) {
			view.setIdPedidoLogistica(Long.parseLong(object[23].toString()));			
		}
		if (object[24] != null) {
			view.setIdPedidoAdicional(Long.parseLong(object[24].toString()));			
		}
		if (object[25] != null) {
			view.setIdDoador(Long.parseLong(object[25].toString()));			
		}
		if (object[26] != null) {
			view.setNomeUsuarioResponsavelTarefa(object[26].toString());			
		}
				
		return view;		
	}

	
	/**
	 * Método que cria uma instância de ConsultaTarefasWorkupDTO com os dados de um vetor. 
	 * 
	 * @param object - dados da view ConsultaTarefasWorkupDTO
	 * @return ConsultaTarefasWorkupDTO
	 */
	public static ConsultaTarefasWorkupDTO popularViewSolicitacaofaWorkup(Object[] object) {
		
		ConsultaTarefasWorkupDTO view = new ConsultaTarefasWorkupDTO();
				
		if (object[0] != null) {
			view.setRmr(Long.parseLong(object[0].toString()));			
		}
		if (object[1] != null) {
			view.setIdentificacaoDoador(object[1].toString());			
		}
		if (object[2] != null) {
			view.setRegistroOrigem(object[2].toString());			
		}
		if (object[3] != null) {
			view.setTipoPrescricao(object[3].toString());
		}
		if (object[4] != null) {
			view.setDataSugWorkup(((Timestamp) object[4]).toLocalDateTime());
		}
		if (object[5] != null) {
			view.setDataSugColeta(((Timestamp) object[5]).toLocalDateTime());
		}
		if (object[6] != null) {
			view.setDataWorkup(((Timestamp) object[6]).toLocalDateTime());
		}
		if (object[7] != null) {
			view.setDataColeta(((Timestamp) object[7]).toLocalDateTime());
		}
		if (object[8] != null) {
			view.setAcao(object[8].toString());			
		}
		if (object[9] != null) {
			view.setNomeCentroTransplante(object[9].toString());
		}
		if (object[10] != null) {
			view.setMedicoResponsavelPrescricao(object[10].toString());			
		}
		if (object[11] != null) {
			view.setDataPrescricao(((Timestamp) object[11]).toLocalDateTime());
		}
				
		return view;		
	}

	
}
