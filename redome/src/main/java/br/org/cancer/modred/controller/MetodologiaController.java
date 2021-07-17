package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Metodologia;
import br.org.cancer.modred.service.IMetodologiaService;

/**
 * Controlador para metodologias.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/metodologia")
public class MetodologiaController {

    @Autowired
    private IMetodologiaService metodologiaService;

    /**
     * Método rest obter lista de metodologias.
     * 
     * @Return ResponseEntity<List<Metodologia>> lista de metodologias
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Metodologia>> listarMetodologias() {
        return new ResponseEntity<List<Metodologia>>(metodologiaService.listarMetodologias(),
                HttpStatus.OK);
    }
}