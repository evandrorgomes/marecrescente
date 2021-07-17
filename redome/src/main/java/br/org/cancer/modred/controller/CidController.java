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

import br.org.cancer.modred.model.Cid;
import br.org.cancer.modred.model.EstagioDoenca;
import br.org.cancer.modred.service.ICidService;
import br.org.cancer.modred.service.IEstagioDoencaService;

/**
 * Controlador para cids.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/cid", produces = "application/json;charset=UTF-8")
public class CidController {
    
    @Autowired
    private ICidService cidService;
    @Autowired
    private IEstagioDoencaService estagioDoencaService;
    
    /**
     * Retorna uma lista de cid a partir de um código ou uma descrição.
     * @param textoPesquisa
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cid>> listarCidsPorCodigoOuDescricao(@RequestParam("q") String textoPesquisa) {
        return new ResponseEntity<List<Cid>>(cidService.listarCidPorCodigoOuDescricao(textoPesquisa), HttpStatus.OK);
    }
    
    /**
     * Retorna uma lista de estágios de doença por um cid.
     * 
     * @param String
     *            - cid
     * @return ResponseEntity<List<EstagioDoenca>>
     */
    @RequestMapping(value = "/{id}/estagios", method = RequestMethod.GET)
    public ResponseEntity<List<EstagioDoenca>> listarEstagiosDoenca(@PathVariable(required = true) Long id) {
        return new ResponseEntity<List<EstagioDoenca>>(estagioDoencaService
                .listarEstagiosDoencaPorCid(id), HttpStatus.OK);
    }
}
