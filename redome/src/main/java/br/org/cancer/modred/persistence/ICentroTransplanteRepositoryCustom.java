package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.ContatoCentroTransplantadorDTO;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Inteface adicional que concentra e abstrai regras de pesquisa sobre os objetos relacionados aos 
 * centros de transplantes da plataforma redome.
 * 
 * Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da
 * entidade CentroTransplante.
 * 
 * @author Thiago Moraes
 *
 */
public interface ICentroTransplanteRepositoryCustom {

    /**
     * Método para recuperar o conjuto de centroTransplantes disponíveis.
     * 
     * @param querystring - conjunto de um ou mais termos que serão utilizados para fazer match simples nos elementos 
     * textuais do centroTransplante.
     *  
     * @param pageable - informações sobre paginação do conjunto que será retornado (informação opcional)
     * @param idFuncaoCentroTransplante - função que o centro de transplante assume
     * 
     * @return Page - um conjunto de centros de transplante conforme os parâmetros informados no 
     * acionamento deste método.
     */
    Page<CentroTransplante> listarCentroTransplantes(String nome, String cnpj, String cnes, Pageable pageable, Long idFuncaoCentroTransplante);
    
    /**
	 * Lista os centros de transplante que exercem a função informada.
	 * 
	 * @param funcaoCentroTransplanteId ID da função do centro de transplante.
	 * @return lista de centros de transplante com seus respectivos endereço e contato.
	 */
    List<ContatoCentroTransplantadorDTO> listarCentrosTransplantePorFuncao(Long funcaoCentroTransplanteId);
    
    /**
     * Lista todos os usuários associados a determinado centro de transplante.
     * 
     * @param centroTransplanteId identificado do centro.
     * @return lista de usuários associados ao centro informado.
     */
    List<Usuario> listarUsuariosPorCentro(Long centroTransplanteId);
}
