package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ValorDnaNmdp;
import br.org.cancer.modred.model.ValorDnaNmdpPk;

/**
 * Repositório para acesso ao banco da entidade LocusExame.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IValorDnaNmdpRepository extends JpaRepository<ValorDnaNmdp, ValorDnaNmdpPk> {

    /**
     * Verifica se existe no banco os valores informados como HLA. Se existirem,
     * são considerados válidos. Os dados foram
     * desnormalizados para facilitar a comparação contra o domínio

     * @param locusCodigo
     * @param valor
     * @return
     */
    Long countByIdLocusCodigoAndValor(String locusCodigo, String valor);
}