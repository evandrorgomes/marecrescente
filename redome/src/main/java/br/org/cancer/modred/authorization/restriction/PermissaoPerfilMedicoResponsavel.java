package br.org.cancer.modred.authorization.restriction;

import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IBuscaRepository;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.persistence.IPacienteRepository;
import br.org.cancer.modred.persistence.IUsuarioRepository;

/**
 * Verifica se o usuário logado é o médico responsável pelo cadastro do
 * paciente.
 * 
 * @author Cintia Oliveira
 *
 */
public class PermissaoPerfilMedicoResponsavel implements IRestricaoCustomizada<Paciente> {

    private IGenericRepository genericRepository;

    public PermissaoPerfilMedicoResponsavel(IGenericRepository genericRepository) {
        this.genericRepository = genericRepository;
    }

    private IPacienteRepository getPacienteRepository() {
        return (IPacienteRepository) genericRepository.getRepository(Paciente.class);
    }

    @Override
    public boolean hasPermissao(CustomUser usuario, String recurso, Paciente paciente) {
        boolean resultado = false;

        if (paciente != null) {

            if (paciente.getMedicoResponsavel() == null) {
                paciente = getPacienteRepository().findById(paciente.getRmr()).get();
            }

            resultado = paciente.getMedicoResponsavel()
                    .getUsuario().getId().equals(usuario.getId());
            
            if (!resultado) {
                IUsuarioRepository repository = (IUsuarioRepository) genericRepository.getRepository(Usuario.class);
    			Usuario usuarioLogado = repository.findById(usuario.getId()).get();
    			if (usuarioLogado.getCentroTransplanteUsuarios() != null && !usuarioLogado.getCentroTransplanteUsuarios().isEmpty()) {
	            	IBuscaRepository buscaRepository = (IBuscaRepository) genericRepository.getRepository(Busca.class);
	            	Busca buscaAtiva = buscaRepository.findByPacienteRmrAndStatusBuscaAtiva(paciente.getRmr(), true);
	            	if (buscaAtiva != null) {
	            		Long centroBusca = buscaRepository.obterCentroTransplanteIdPorBuscaId(buscaAtiva.getId());
	    			
	            		resultado = usuarioLogado.getCentroTransplanteUsuarios()
	                		.stream()
	                		.anyMatch(centro -> centro.getCentroTransplante().getId().equals(centroBusca));
	            	}
    			}
            }
        }

        return resultado;
    }

    @Override
    public Paciente castTo(Object entity) {
        if (entity instanceof Avaliacao) {
                Avaliacao avaliacao = (Avaliacao) entity;
                return avaliacao.getPaciente();
        }
        else if (entity instanceof Pendencia) {
            Pendencia pendencia = (Pendencia) entity;
            return pendencia.getAvaliacao().getPaciente();
        }
        else if (entity instanceof Evolucao) {
            Evolucao evolucao = (Evolucao) entity;
            return evolucao.getPaciente();
        }
        else if (entity instanceof ExamePaciente) {
            ExamePaciente exame = (ExamePaciente) entity;
            return exame.getPaciente();
        }
        return (Paciente) entity;
    }

}
