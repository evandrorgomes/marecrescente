package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.correio.Bairro;
import br.org.cancer.modred.model.correio.Localidade;
import br.org.cancer.modred.model.correio.Logradouro;
import br.org.cancer.modred.model.correio.TipoLogradouro;
import br.org.cancer.modred.model.correio.UnidadeFederativa;

/**
 * Interface que define os serviços do correio.
 * 
 * @author bruno.sousa
 *
 */
public interface ICorreioService {

    /**
     * Lista as unidades federativas.
     * 
     * @param query 
     * @return List<UnidadeFederativaCorreio>
     */
    List<UnidadeFederativa> listarUnidadeFederativa(String query);

    /**
     * Lista as Localidades pela sigla da Unidade Federativa.
     * 
     * @param siglaUnidadeFederativa
     * @param query
     * @return List<LocalidadeCorreio>
     */
    List<Localidade> listarLocalidadePorUnidadeFederativa(String siglaUnidadeFederativa, String query);

    /**
     * Lista os bairros pelo nome da localidade.
     * 
     * @param sigla,
     * @param localidade
     * @param query
     * @return List<BairroCorreio>
     */
    List<Bairro> listarBairroPorLocalidade(String sigla, String localidade, String query);
    
    /**
     * Lista os logradouros por bairro.
     * 
     * @param sigla
     * @param localidade
     * @param bairro
     * @param query
     * @return List<LogradouroCorreio>
     */
    List<Logradouro> listarLogradouroPorBairro(String sigla, String localidade, String bairro, String query);
    
    /**
     * Traz o logradouro pelo numero do cep.
     * 
     * @param cep
     * @return LogradouroCorreio
     */
    Logradouro obterCep(String cep);
    
    /**
     * Traz os tipos de logradouros.
     * 
     * @param query
     * @return List<TipoLogradouroCorreio>
     */
    List<TipoLogradouro> listarTipoLogradouro(String query);
}

