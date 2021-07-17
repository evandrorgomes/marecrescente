package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Municipio;
import br.org.cancer.modred.service.IMunicipioService;

/**
 * Classe controladora responsável por fazer o dispatch das requisições relacionadas à entidade SISTEMA.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/municipios", produces = "application/json;charset=UTF-8")
public class MunicipioController {

    @Autowired
    private IMunicipioService municipioService;
    
    /**
     * Lista todos os municipios disponíveis na base do Redome.
     * 
     * @return lista de municipios.
     */    
    @GetMapping()
    public ResponseEntity<List<Municipio>> listarMunicipios(){
        return ResponseEntity.ok(municipioService.listarMunicipiosOrdenadoPorDescricao());
    }

}
