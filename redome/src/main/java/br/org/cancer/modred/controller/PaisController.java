package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Pais;
import br.org.cancer.modred.service.IPaisService;
import br.org.cancer.modred.vo.CodigoInternacionalVO;

/**
 * Controlador para País.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value="/public/pais")
public class PaisController {

    @Autowired
    private IPaisService paisService;

    /**
     * Método rest obter lista ordenada de paises.
     * 
     * @Return ResponseEntity<List<Pais>> lista de países
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Pais>> getPais() {
        return new ResponseEntity<List<Pais>>(paisService.listarPais(), HttpStatus.OK);
    }
    
    /**
     * Método para listar os códigos internacionais.
     * 
     * @return ResponseEntity<List<CodigoInternacionalVO>>
     */
    @RequestMapping(value="codigoInternacional", method = RequestMethod.GET)
    public ResponseEntity<List<CodigoInternacionalVO>> getCodigoInternacional() {
        return new ResponseEntity<List<CodigoInternacionalVO>>(paisService.listarCodigoInternacional(), HttpStatus.OK);
    }
}
