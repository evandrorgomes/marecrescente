package br.org.cancer.modred.persistence.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.cancer.modred.model.HistoricoStatusPaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.StatusPaciente;
import br.org.cancer.modred.persistence.IHistoricoStatusPacienteRepositoryCustom;
import br.org.cancer.modred.util.DateUtils;

/**
 * Classe de métodos customizados de histórico de status de paciente.
 * @author Filipe Paes
 *
 */
public class IHistoricoStatusPacienteRepositoryImpl implements IHistoricoStatusPacienteRepositoryCustom {

	private static final Logger LOGGER = LoggerFactory.getLogger(IHistoricoStatusPacienteRepositoryImpl.class);

	@Autowired
	EntityManager entityManager;	
	
	@Override
	public HistoricoStatusPaciente obterUltimoStatusPaciente(Paciente paciente) {
		LOGGER.info("Buscando status no histórrico de status de paciente...");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT * FROM( ")
				.append("select h.hisp_id,h.stpa_id,h.hisp_dt_alteracao, h.paci_nr_rmr from historico_status_paciente h ")
				.append(", status_paciente s where h.stpa_id = s.stpa_id ")
				.append("and h.paci_nr_rmr = :rmr ")
				.append("order by cast(h.hisp_dt_alteracao as date) desc, s.stpa_nr_ordem ASC ")
				.append(") WHERE ROWNUM = 1 ");
		
		Query query = entityManager.createNativeQuery(sqlBuffer.toString());
		query.setParameter("rmr", paciente.getRmr());
		@SuppressWarnings("unchecked")
		Object[] result = query.getSingleResult() != null ? (Object[]) query.getSingleResult() : null;
		
		if (result != null) {				
			HistoricoStatusPaciente historicoStatus  = new HistoricoStatusPaciente();
			historicoStatus.setId(Long.parseLong(result[0].toString()));
			historicoStatus.setStatus(new StatusPaciente(Long.parseLong(result[1].toString())));
			historicoStatus.setDataAlteracao(DateUtils.converterParaLocalDateTime(((java.util.Date)result[2])));
			historicoStatus.setPaciente(new Paciente(Long.parseLong(result[3].toString())));
			return historicoStatus;	
		}
		else{
			return null;
		}
	}

}
