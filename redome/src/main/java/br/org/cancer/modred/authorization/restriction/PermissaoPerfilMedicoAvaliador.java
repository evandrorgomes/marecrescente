package br.org.cancer.modred.authorization.restriction;

import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.persistence.IAvaliacaoRepository;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.persistence.IMedicoRepository;

/**
 * @author Pizão
 * 
 * Verifica se o usuário logado é o médico responsável pela avaliação.
 * 
 */
public class PermissaoPerfilMedicoAvaliador implements IRestricaoCustomizada<Avaliacao> {

	private IGenericRepository genericRepository;

	public PermissaoPerfilMedicoAvaliador(IGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public boolean hasPermissao(CustomUser usuario, String recurso, Avaliacao avaliacao) {
		IMedicoRepository repository = (IMedicoRepository) genericRepository.getRepository(Medico.class);
		boolean possui = false;

		if (avaliacao.getMedicoResponsavel() != null) {
			Medico medicoAvaliador = repository.findById(avaliacao.getMedicoResponsavel().getId()).get();
			possui = usuario.getId().equals(medicoAvaliador.getUsuario().getId());
		}
		else {
			//Medico logado = repository.findByUsuarioId(usuario.getId());
			if (usuario.getCentros() != null && usuario.getCentros().size() != 0) {
				possui = usuario.getCentros().stream()
						.anyMatch(centro -> avaliacao.getCentroAvaliador().getId().equals(centro.getId()));
			}			
		}

		return possui;
	}

	@Override
	public Avaliacao castTo(Object entity) {
		IAvaliacaoRepository repository = (IAvaliacaoRepository) genericRepository.getRepository(Avaliacao.class);

		if (entity instanceof Pendencia) {
			return ( (Pendencia) entity ).getAvaliacao();
		}
		else
			if (entity instanceof Evolucao) {
				Evolucao evolucao = (Evolucao) entity;
				return repository.findByPacienteRmr(evolucao.getPaciente().getRmr());
			}
			else
				if (entity instanceof ExamePaciente) {
					ExamePaciente exame = (ExamePaciente) entity;
					return repository.findByPacienteRmr(exame.getPaciente().getRmr());
				}
				else
					if (entity instanceof Paciente) {
						return repository.findByPacienteRmr(( (Paciente) entity ).getRmr());
					}
		return (Avaliacao) entity;
	}

}
