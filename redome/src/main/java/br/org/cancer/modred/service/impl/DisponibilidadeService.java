package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IDisponibilidadeRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IDisponibilidadeService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de funcionalidades que envolvem a entidade Disponibilidade.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class DisponibilidadeService extends AbstractService<Disponibilidade, Long> implements
		IDisponibilidadeService {

	@Autowired
	private IDisponibilidadeRepository disponibilidadeRepository;
		
	private IPedidoWorkupService pedidoWorkupService;
	
	private IPacienteService pacienteService;
	
	private IPedidoColetaService pedidoColetaService;
	
	@Override
	public IRepository<Disponibilidade, Long> getRepository() {
		return disponibilidadeRepository;
	}
	
	@Override
	public Disponibilidade obterUltimaDisponibilidade(Long idPedidoWorkup){
		 return disponibilidadeRepository.findFirstByPedidoWorkupIdOrderByDataAceiteDesc(idPedidoWorkup);
	}
	
	@Override
	public Disponibilidade responderDisponibilidade(Disponibilidade disponibilidadeAtualizada) {
		Disponibilidade disponibilidadeRecuperada = findById(disponibilidadeAtualizada.getId());
		
		disponibilidadeRecuperada.setDataAceite(LocalDateTime.now());
		return save(disponibilidadeRecuperada);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Disponibilidade obterUltimaDisponibilidadePedidoColeta(Long idPedidoColeta) {
		return disponibilidadeRepository.findFirstByPedidoColetaIdOrderByDataAceiteDesc(idPedidoColeta);
	}	
	
	@Override
	public void incluirDisponibilidadeWorkup(Long idPedidoWorkup, String dataSugerida) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.alterarStatusParaAguardandoCT(idPedidoWorkup);
		
		Disponibilidade disponibilidade = new Disponibilidade();
		disponibilidade.setDescricao(dataSugerida);
		disponibilidade.setPedidoWorkup(pedidoWorkup);
		save(disponibilidade);
		
		criarTarefaSugerirWorkupCentroTransplante(pedidoWorkup);
	}
	
	@Override
	public void incluirDisponibilidadeColeta(Long idPedidoColeta, String dataSugerida) {
		
		PedidoColeta pedidoColeta = pedidoColetaService.alterarStatusParaAguardandoCT(idPedidoColeta);
		
		Disponibilidade disponibilidade = new Disponibilidade();
		disponibilidade.setDescricao(dataSugerida);
		disponibilidade.setPedidoColeta(pedidoColeta);
		save(disponibilidade);
		
		criarTarefaSugerirColetaCentroTransplante(pedidoColeta);
	}
	
	/**
	 * Cria tarefa sugerindo datas de agendamento de workup para
	 * o centro de transplante.
	 * 
	 * @param pedidoWorkup pedido de workup que gerou esta tarefa.
	 */
	private void criarTarefaSugerirWorkupCentroTransplante(PedidoWorkup pedidoWorkup) {		
		final Paciente paciente = pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente();
		final CentroTransplante centroTransplante = pacienteService.obterCentroTransplanteConfirmado(paciente);
		
		TiposTarefa.SUGERIR_DATA_WORKUP.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comParceiro(centroTransplante.getId())
			.comObjetoRelacionado(pedidoWorkup.getId())
			.apply();
	}
	
	/**
	 * Cria tarefa sugerindo datas de agendamento de workup para
	 * o centro de transplante.
	 * 
	 * @param pedidoWorkup pedido de workup que gerou esta tarefa.
	 */
	private void criarTarefaSugerirColetaCentroTransplante(PedidoColeta pedidoColeta) {		
		final Paciente paciente = pedidoColeta.getSolicitacao().getMatch().getBusca().getPaciente();
		final CentroTransplante centroTransplante = pacienteService.obterCentroTransplanteConfirmado(paciente);
		
		TiposTarefa.SUGERIR_DATA_COLETA.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comParceiro(centroTransplante.getId())
			.comObjetoRelacionado(pedidoColeta.getId())
			.apply();
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(Disponibilidade entity) {
		List<CampoMensagem> erros = super.validateEntity(entity);
		if (entity.getPedidoWorkup() == null && entity.getPedidoColeta() == null) {
			CampoMensagem campoMensagem = new CampoMensagem(
					AppUtil.getMensagem(messageSource, "erro.disponibilidade.sem.pedidoworkup.e.sem.pedidocoleta"));
			erros.add(campoMensagem);
		}
				
		return erros;
	}

	/**
	 * @param pedidoWorkupService the pedidoWorkupService to set
	 */
	@Autowired
	public void setPedidoWorkupService(IPedidoWorkupService pedidoWorkupService) {
		this.pedidoWorkupService = pedidoWorkupService;
	}

	/**
	 * @param pacienteService the pacienteService to set
	 */
	@Autowired
	public void setPacienteService(IPacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	/**
	 * @param pedidoColetaService the pedidoColetaService to set
	 */
	@Autowired
	public void setPedidoColetaService(IPedidoColetaService pedidoColetaService) {
		this.pedidoColetaService = pedidoColetaService;
	}
	
	

}
