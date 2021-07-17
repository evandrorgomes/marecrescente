package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.StatusExame;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.DateUtils;

/**
 * Estratégia executada quando o exame do paciente é descartado.
 * Para este cenário, uma notificação deve ser enviada para o médico do 
 * paciente.
 * 
 * @author Pizão
 *
 */
@Component
public class ExamePacienteReprovadoObserver extends EntityObserver<ExamePaciente> {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public boolean condition(ExamePaciente exame) {
		return StatusExame.DESCARTADO.getId().equals(exame.getStatusExame());
	}

	@Override
	public void process(ExamePaciente exame) {
		final String textoNotificacao = AppUtil.getMensagem(messageSource,
				"exame.paciente.descarte.mensagem",
				DateUtils.getDataFormatadaSemHora(exame.getDataExame()),
				exame.getMotivoDescarte().getDescricao());
		
		CategoriasNotificacao.EXAME.getConfiguracao()
			.criar()
			.comPaciente(exame.getPaciente().getRmr())
			.paraUsuario(exame.getPaciente().getMedicoResponsavel().getUsuario().getId())
			.comDescricao(textoNotificacao)
			.apply();
		
	}

}
