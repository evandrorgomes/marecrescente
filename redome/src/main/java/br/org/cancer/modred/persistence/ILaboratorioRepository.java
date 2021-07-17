package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Laboratorio;

/**
 * Interface para métodos de persistencia relativos a Laboratorio.
 * @author Filipe Paes
 *
 */
@Repository
public interface ILaboratorioRepository  extends IRepository<Laboratorio, Long> {
	
	@Query("SELECT new Laboratorio(l.id, l.nome, "
			+ " (select count(pe.id) as total from PedidoExame pe "
			+ "     where pe.solicitacao.tipoSolicitacao.id = 2 "
			+ "       and pe.solicitacao.busca.id != null "
			+ "       and pe.laboratorio.id = l.id "
			+ "       and pe.statusPedidoExame.id = 1 ), "
			+ " l.quantidadeExamesCT, m.descricao, u.sigla ) FROM Laboratorio l join l.endereco e join e.municipio m join m.uf u "
			+ " where l.fazExameCT = true "
			+ " order by u.sigla")
	List<Laboratorio> findByFazExameCT();
	
	@Query("SELECT l FROM Laboratorio l "
			+ " where l.fazExameCT = true "
			+ " order by l.nome")
	Page<Laboratorio> findByLaboratoriosFazExameCT(Pageable page);
	
	Laboratorio findByIdRedomeWeb(Long id);

}
