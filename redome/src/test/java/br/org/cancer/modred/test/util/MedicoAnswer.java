package br.org.cancer.modred.test.util;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.security.Usuario;

public class MedicoAnswer implements Answer<Medico> {
	
	private Long idMedico;
	private Long idUsuario;
	
	public MedicoAnswer(Long idMedico, Long idUsuario) {
		super();
		this.idMedico = idMedico;
		this.idUsuario = idUsuario;
	}

	@Override
	public Medico answer(InvocationOnMock invocation) throws Throwable {
		Medico medico = new Medico(idMedico);
		medico.setUsuario(new Usuario(idUsuario));
		return medico;
	}
	
	

}
