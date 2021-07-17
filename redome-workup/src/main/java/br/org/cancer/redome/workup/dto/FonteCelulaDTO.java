package br.org.cancer.redome.workup.dto;

import java.io.Serializable;

import br.org.cancer.redome.workup.model.FonteCelula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FonteCelulaDTO implements Serializable {

	private static final long serialVersionUID = -7187947453200114880L;

	private Long id;
	
	private String descricao;
	
	private String sigla;
	
	public static FonteCelulaDTO parse(FonteCelula fonteCelula) {
		FonteCelulaDTO fonteCelulaDTO = null; 
		if(fonteCelula != null) {
			fonteCelulaDTO = FonteCelulaDTO.builder()
					.id(fonteCelula.getId())
					.descricao(fonteCelula.getDescricao())
					.sigla(fonteCelula.getSigla())
					.build();
		}
		return fonteCelulaDTO;		
	}

}
