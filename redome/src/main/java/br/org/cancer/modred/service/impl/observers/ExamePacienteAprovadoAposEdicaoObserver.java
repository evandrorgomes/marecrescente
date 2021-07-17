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
 * Estratégia executada quando o exame do paciente é aprovado, após ser ajustado pelo avaliador.
 * Para este cenário, uma notificação deve ser enviada para o médico do 
 * paciente.
 * 
 * @author Pizão
 *
 */
@Component
public class ExamePacienteAprovadoAposEdicaoObserver extends EntityObserver<ExamePaciente> {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public boolean condition(ExamePaciente exame) {
		return StatusExame.CONFERIDO.getId().equals(exame.getStatusExame()) && exame.getEditadoPorAvaliador();
	}

	@Override
	public void process(ExamePaciente exame) {
		final String textoNotificacao = AppUtil.getMensagem(messageSource, 
				"exame.paciente.aprovado.com.alteracoes.mensagem",
				DateUtils.getDataFormatadaSemHora(exame.getDataExame()));

		CategoriasNotificacao.EXAME.getConfiguracao()
			.criar()
			.comPaciente(exame.getPaciente().getRmr())
			.paraUsuario(exame.getPaciente().getMedicoResponsavel().getUsuario().getId())
			.comDescricao(textoNotificacao)
			.apply();
	}

}
