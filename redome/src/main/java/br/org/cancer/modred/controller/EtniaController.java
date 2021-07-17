package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Etnia;
import br.org.cancer.modred.service.IEtniaService;

/**
 * Controlador para etnias.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/etnia")
public class EtniaController {

    @Autowired
    private IEtniaService etniaService;

    /**
     * Método rest obter lista ordenada de etnias.
     * 
     * @return List<Etnia> etnias
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Etnia>> getRacas() {
        return new ResponseEntity<List<Etnia>>(etniaService.listarEtnias(), HttpStatus.OK);
    }
}
