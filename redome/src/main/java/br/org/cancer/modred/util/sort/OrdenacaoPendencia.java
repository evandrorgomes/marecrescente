package br.org.cancer.modred.util.sort;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.data.jpa.domain.Specification;

import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.domain.StatusPendencias;

/**
 * @author Pizão
 * 
 * Cria a ordenação e o filtro para ser utilizado na listagem de pendências.
 * 
 */
public class OrdenacaoPendencia implements Specification<Pendencia> {

    private Long[] statusPendenciaId;
    private Long avaliacaoId;
    
    /**
     * Cria a ordena considerando os status informados.
     * 
     * @param avaliacaoId avaliação que servirá como filtro das pendências.
     * @param statusPendenciaId Lista de status pendência a ser considerado na ordenação.
     */
    private OrdenacaoPendencia(Long avaliacaoId, Long...statusPendenciaId) {
        this.statusPendenciaId = statusPendenciaId;
        this.avaliacaoId = avaliacaoId;
    }
    
    /**
     * Cria a ordenação para o perfil de médico responsável.
     * 
     * @param avaliacaoId avaliação que servirá como filtro das pendências.
     * @return instância da especificação definido para o médico responsável.
     */
    public static OrdenacaoPendencia getOrdenacaoMedico(Long avaliacaoId){
        return new OrdenacaoPendencia(
                avaliacaoId,
                StatusPendencias.ABERTA.getId(), StatusPendencias.RESPONDIDA.getId(),
                StatusPendencias.FECHADA.getId(), StatusPendencias.CANCELADA.getId());
    }
    
    /**
     * Cria a ordenação para o perfil de médico avaliador.
     * 
     * @param avaliacaoId avaliação que servirá como filtro das pendências.
     * @return instância da especificação definido para o médico avaliador.
     */
    public static OrdenacaoPendencia getOrdenacaoAvaliador(Long avaliacaoId){
        return new OrdenacaoPendencia(
                avaliacaoId,
                StatusPendencias.RESPONDIDA.getId(), StatusPendencias.ABERTA.getId(),
                StatusPendencias.FECHADA.getId(), StatusPendencias.CANCELADA.getId());
    }
    
    @Override
    public Predicate toPredicate(Root<Pendencia> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Case<Object> selectCase = criteriaBuilder.selectCase();
        
        
        if(ArrayUtils.isNotEmpty(statusPendenciaId)){
            int ordem = 0;
            
            for (Long statusId : statusPendenciaId) {
                Expression<Boolean> condicao = 
                        criteriaBuilder.equal(root.get("statusPendencia").get("id"), statusId);
                selectCase.when(condicao, ordem++);
            }
            List<Order> ordens = new ArrayList<Order>();            
            ordens.add(new OrderImpl(selectCase.otherwise(0), true));
            ordens.add(criteriaBuilder.asc(root.get("dataCriacao")));
            
            query.orderBy(ordens);
            
        }
        
        return criteriaBuilder.equal(root.get("avaliacao").get("id"), avaliacaoId);
    }

}
