package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.TipoExameView;
import br.org.cancer.modred.model.TipoExame;
import br.org.cancer.modred.service.ITipoExameService;

/**
 * Controlador para tipo de exame.
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(value = "/api/tiposexame", produces = "application/json;charset=UTF-8")
public class TipoExameController {
    
    @Autowired
    private ITipoExameService tipoExameService;
    
    /**
     * Retorna uma lista de tipo de transplante.
     * 
     * @return
     */
    @RequestMapping(value="/fase2nacional", method = RequestMethod.GET)
    @JsonView(TipoExameView.Listar.class)
    public ResponseEntity<List<TipoExame>> listarTipoDeExameFase2Nacional() {
        return new ResponseEntity<List<TipoExame>>(tipoExameService.listarTipoExameFase2Nacional(), HttpStatus.OK);
    }
}
