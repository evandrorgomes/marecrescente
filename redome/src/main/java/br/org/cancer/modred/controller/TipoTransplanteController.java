package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.TipoTransplante;
import br.org.cancer.modred.service.ITipoTransplanteService;

/**
 * Controlador para tipo de transplante.
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(value = "/api/tipoTransplante", produces = "application/json;charset=UTF-8")
public class TipoTransplanteController {
    
    @Autowired
    private ITipoTransplanteService tipoTransplanteService;
    
    /**
     * Retorna uma lista de tipo de transplante.
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TipoTransplante>> listarTipoDeTransplante() {
        return new ResponseEntity<List<TipoTransplante>>(tipoTransplanteService.listarTipoTransplante(), HttpStatus.OK);
    }
}
