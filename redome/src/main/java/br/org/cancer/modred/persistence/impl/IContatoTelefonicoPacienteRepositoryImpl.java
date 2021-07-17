package br.org.cancer.modred.persistence.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.cancer.modred.persistence.IContatoTelefonicoPacienteRepositoryCustom;

/**
 * Camada de acesso a base dados de ContatoTelefonicoPaciente.
 * 
 * @author bruno.sousa
 *
 */
public class IContatoTelefonicoPacienteRepositoryImpl implements IContatoTelefonicoPacienteRepositoryCustom {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public Integer deletar(Long rmr) {		
		StringBuffer hql = 
				new StringBuffer("delete from ContatoTelefonicoPaciente where paciente.rmr = :rmr");
		
		Query query = entityManager.createQuery(hql.toString())
				.setParameter("rmr", rmr);
		
		return query.executeUpdate();
	}
	
	

}
