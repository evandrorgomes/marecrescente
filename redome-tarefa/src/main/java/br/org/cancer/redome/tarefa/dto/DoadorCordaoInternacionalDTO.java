package br.org.cancer.redome.tarefa.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto para DoadorInternacional.
 * 
 * @author ergomes
 *
 */
@Data 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class DoadorCordaoInternacionalDTO  implements Serializable {

	private static final long serialVersionUID = -2947224767593007295L;

	private Long tipoDoador;

	private Long rmrAssociado;
	
	private Long id;
	
	private LocalDate dataCadastro;
	
	private RegistroDTO registroOrigem;
	
	private String sexo;
	
	private String abo;
	
	private LocalDate dataNascimento;
	
	private LocalDateTime dataAtualizacao;
	
//	private StatusDoadorDTO statusDoador;
	
	private LocalDate dataRetornoInatividade;
	
	private BigDecimal peso;
	
	private Integer idade;
	
	private String grid;

	private String idRegistro;
	
	private RegistroDTO registroPagamento;

	private Boolean cadastradoEmdis;
	
	private List<String> ressalvas;
	
//	private List<LocusExameWmdaDTO> locusExames;
	
//	private List<ValorGenotipoDTO> valoresGenotipo;
	
	private BigDecimal quantidadeTotalTCN;

	private BigDecimal quantidadeTotalCD34;

	private BigDecimal volume;
		
	
}
