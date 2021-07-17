package br.org.cancer.modred.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.cancer.modred.model.RespostaFormularioDoador;
import br.org.cancer.modred.persistence.IRespostaFormularioDoadorRepositoryCustom;

/**
 * Classe customisada de persistência para a RespostaFormularioDoador. 
 * 
 * @author bruno.sousa 
 */
public class IRespostaFormularioDoadorRepositoryImpl implements IRespostaFormularioDoadorRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	@Override
	public RespostaFormularioDoador findFirstByformularioDoadorPedidoContatoIdAndDoadorIdAndTokenOrderByFormularioDoadorDataRespostaDesc(
			Long idPedidoContato, Long idDoador, String token) {
		
		StringBuilder hql = new StringBuilder("SELECT new RespostaFormularioDoador(r.id, r.token, r.resposta) from RespostaFormularioDoador r ");
		hql.append("join r.formularioDoador fd join fd.doadorNacional d  ");
		hql.append("where fd.pedidoContato = :idPedidoContato and d.idDoador = :idDoador and r.token = :token ");
		hql.append("order by fd.dataResposta desc ");
		
		Query query = entityManager.createQuery(hql.toString()).setMaxResults(1)
				.setParameter("idPedidoContato", idPedidoContato)
				.setParameter("idDoador", idDoador)
				.setParameter("token", token);
		
		@SuppressWarnings("unchecked")
		List<RespostaFormularioDoador> lista = query.getResultList();
		if (!lista.isEmpty()) {
			return lista.get(0);
		}
		
		return null;
	}

	@Override
	public RespostaFormularioDoador findFirstByformularioDoadorPedidoWorkupIdAndDoadorIdAndTokenOrderByFormularioDoadorDataRespostaDesc(
			Long idPedidoWorkup, Long idDoador, String token) {
		
		StringBuilder hql = new StringBuilder("SELECT new RespostaFormularioDoador(r.id, r.token, r.resposta) from RespostaFormularioDoador r ");
		hql.append("join r.formularioDoador fd join fd.doadorNacional d  ");
		hql.append("where fd.pedidoWorkup = :idPedidoWorkup and d.id = :idDoador and r.token = :token ");
		hql.append("order by fd.dataResposta desc ");
		
		Query query = entityManager.createQuery(hql.toString()).setMaxResults(1)
				.setParameter("idPedidoWorkup", idPedidoWorkup)
				.setParameter("idDoador", idDoador)
				.setParameter("token", token);
		
		@SuppressWarnings("unchecked")
		List<RespostaFormularioDoador> lista = query.getResultList();
		if (!lista.isEmpty()) {
			return lista.get(0);
		}
		
		return null;
	}
	
}
