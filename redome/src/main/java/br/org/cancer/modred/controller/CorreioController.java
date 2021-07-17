package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.correio.Bairro;
import br.org.cancer.modred.model.correio.Localidade;
import br.org.cancer.modred.model.correio.TipoLogradouro;
import br.org.cancer.modred.model.correio.UnidadeFederativa;
import br.org.cancer.modred.service.ICorreioService;
import br.org.cancer.modred.vo.CepCorreioTransformer;
import br.org.cancer.modred.vo.CepCorreioVO;

/**
 * Controlador para o servico de endereço do correio.
 * 
 * @author Bruno Sousa
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class CorreioController {

    @Autowired
    private ICorreioService correioSevice;

    /**
     * Retorna lista de estados.
     * 
     * @return ResponseEntity<List<UnidadeFederativaCorreio>>
     */
    @RequestMapping(value = "/api/correio", method = RequestMethod.GET)
    public ResponseEntity<List<UnidadeFederativa>> listaUnidadeFederativa(@RequestParam(name = "q",
                                                                                        required = false) String query) {
        return new ResponseEntity<List<UnidadeFederativa>>(correioSevice.listarUnidadeFederativa(
                query), HttpStatus.OK);
    }

    /**
     * Retorna lista de estados por sigla.
     * 
     * @param String
     *            - sigla
     * @return ResponseEntity<List<LocalidadeCorreio>>
     */
    @RequestMapping(value = "/api/correio/{sigla}", method = RequestMethod.GET)
    public ResponseEntity<List<Localidade>> listaLocalidadesPorUnidadeFederativa(
            @PathVariable("sigla") String sigla, @RequestParam(name = "q",
                                                               required = false) String query) {
        return new ResponseEntity<List<Localidade>>(correioSevice
                .listarLocalidadePorUnidadeFederativa(sigla, query), HttpStatus.OK);
    }

    /**
     * Retorna uma lista de bairros por sigla do estado e por municipio.
     * 
     * @param String
     *            - sigla
     * @param String
     *            - localidade
     * @return ResponseEntity<List<BairroCorreio>>
     */
    @RequestMapping(value = "/api/correio/{sigla}/{localidade}", method = RequestMethod.GET)
    public ResponseEntity<List<Bairro>> listaBairroPorLocalidade(
            @PathVariable("sigla") String sigla, @PathVariable("localidade") String localidade,
            @RequestParam(name = "q", required = false) String query) {
        return new ResponseEntity<List<Bairro>>(correioSevice.listarBairroPorLocalidade(sigla,
                localidade, query), HttpStatus.OK);
    }

    /**
     * @param sigla
     *            s
     * @param localidade
     *            l
     * @param bairro
     *            b
     * @param query
     *            q
     * @return ResponseEntity<List<CepCorreioVO>>
     */
    @RequestMapping(value = "/api/correio/{sigla}/{localidade}/{bairro}", method = RequestMethod.GET)
    public ResponseEntity<List<CepCorreioVO>> listaLogradouroPorBairro(
            @PathVariable("sigla") String sigla, @PathVariable("localidade") String localidade,
            @PathVariable("bairro") String bairro, @RequestParam(name = "q",
                                                                 required = false) String query) {
        return new ResponseEntity<List<CepCorreioVO>>(CepCorreioTransformer.transformar(
                correioSevice.listarLogradouroPorBairro(sigla, localidade, bairro, query))
                .transformar(), HttpStatus.OK);
    }

    /**
     * Retorna uma lista de logradouros por CEP.
     * 
     * @param String
     *            - cep
     * @return ResponseEntity<CepCorreioVO>
     */
    @RequestMapping(value = "/public/correio/cep", method = RequestMethod.GET)
    public ResponseEntity<CepCorreioVO> getLogradouroPorCep(@RequestParam(
                                                                          required = true) String cep) {
        return new ResponseEntity<CepCorreioVO>(CepCorreioTransformer.transformar(correioSevice
                .obterCep(cep)).transformar(), HttpStatus.OK);
    }

    /**
     * Retorna uma lista de tipos de logradouros.
     * 
     * @param String
     *            - cep
     * @return ResponseEntity<CepCorreioVO>
     */
    @RequestMapping(value = "/api/correio/tipologradouro", method = RequestMethod.GET)
    public ResponseEntity<List<TipoLogradouro>> getTipoLogradouro(@RequestParam(name = "q",
                                                                                required = false) String query) {
        return new ResponseEntity<List<TipoLogradouro>>(correioSevice.listarTipoLogradouro(query),
                HttpStatus.OK);
    }
}
