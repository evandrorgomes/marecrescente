package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoDoador;
import br.org.cancer.modred.model.ValorGenotipoDoadorPK;

/**
 * Interface para acesso ao banco envolvendo a entidade ValorGenotipo.
 * 
 * @author Pizão
 *
 */
@Repository
@Transactional
public interface IValorGenotipoDoadorRepository extends IRepository<ValorGenotipoDoador, ValorGenotipoDoadorPK> {

	@Modifying
	@Query(value = "DELETE FROM VALOR_GENOTIPO_DOADOR WHERE GEDO_ID = :idGenotipo", nativeQuery=true)
	void deletarValoresPorGenotipo(@Param("idGenotipo") Long idGenotipo);

	@Query(value = "select new ValorGenotipoDoador(vgd.id.locus.codigo, vgd.alelo, vgd.posicao, gd.doador.id, vgd.tipoDoador ) "
			+ "from ValorGenotipoDoador vgd join vgd.genotipo gd where vgd.genotipo.id = :idGenotipoDoador" )
	List<ValorGenotipoDoador> listarPorGenotipoDoadorId(@Param("idGenotipoDoador") Long idGenotipoDoador);
	
	
	@Query(value = "SELECT DECODE (COUNT (V.VAGD_IN_DIVERGENTE), 0, 'false', 'true') FROM VALOR_GENOTIPO_DOADOR V INNER JOIN GENOTIPO_DOADOR G ON (V.GEDO_ID = G.GEDO_ID)\n" + 
			"WHERE G.DOAD_ID = :idDoador", nativeQuery=true)
	Boolean existemValoresComDivergencia(@Param("idDoador") Long idDoador);


}
