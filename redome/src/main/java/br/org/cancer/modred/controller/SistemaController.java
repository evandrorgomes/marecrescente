package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.UsuarioView;
import br.org.cancer.modred.model.security.Sistema;
import br.org.cancer.modred.service.ISistemaService;

/**
 * Classe controladora responsável por fazer o dispatch das requisições relacionadas à entidade SISTEMA.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/sistemas", produces = "application/json;charset=UTF-8")
public class SistemaController {

    @Autowired
    private ISistemaService sistemaService;
    
    /**
     * Lista todos os sistemas disponíveis na base do Redome.
     * 
     * @return lista de perfis.
     */
    @RequestMapping(method = RequestMethod.GET)
    @JsonView(UsuarioView.Consultar.class)
    public ResponseEntity<List<Sistema>> listarSistemas(){
        return new ResponseEntity<List<Sistema>>(sistemaService.listarDisponiveisParaRedome(), HttpStatus.OK);
    }

}
