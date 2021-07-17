package br.org.cancer.redome.feign.client.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class TransportadoraUsuarioDTO implements Serializable {

	private static final long serialVersionUID = -3536391404140874363L;
	
	private Long idTransportadora = null;
	
	private List<Long> usuarios = null;
	

}
