package br.org.cancer.redome.workup.dto;

import java.io.Serializable;

import br.org.cancer.redome.workup.model.TipoAmostra;
import br.org.cancer.redome.workup.model.TipoAmostraPrescricao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TipoAmostraPrescricaoDTO implements Serializable {

	private static final long serialVersionUID = -3154373327322708497L;

	private Long id;
	
	private Integer ml;
	
	private TipoAmostra tipoAmostra;
	
	private String descricaoOutrosExames;
	
	public TipoAmostraPrescricaoDTO(TipoAmostraPrescricao amostra) {
		this.id = amostra.getId();
		this.ml = amostra.getMl();
		this.tipoAmostra = amostra.getTipoAmostra();
		this.descricaoOutrosExames = amostra.getDescricaoOutrosExames();
	}

	
}
