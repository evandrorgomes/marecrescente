package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ContatoTelefonicoDoador;

/**
 * Camada de acesso a base dados de ContatoTelefonicoDoador.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IContatoTelefonicoDoadorRepository extends IContatoTelefonicoBaseRepository<ContatoTelefonicoDoador> {
    
    /**
     * Lista todos os contatos telefônicos para o doador informado.
     * 
     * @param idDoador identificador do doador.
     * @return lista de telefones associados.
     */
    List<ContatoTelefonicoDoador> findByDoadorId(Long idDoador);
    
	

    /**
     * Retirar todas marcações de principal dos contatos do doador.
	 * 
	 * @param idDoador identificação do doador.
	 * @return quantidade de registros afetados.
     */
    @Modifying
	@Query("update ContatoTelefonicoDoador set principal = false where doador.id = :idDoador")
    int retirarContatoPrincipalDoador(@Param("idDoador") Long idDoador);
}