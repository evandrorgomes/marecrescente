package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.CondicaoPaciente;
import br.org.cancer.modred.service.ICondicaoPacienteService;

/**
 * Classe de REST controller para condição paciente.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/condicaopaciente", produces = "application/json;charset=UTF-8")
public class CondicaoPacienteController {
    
    @Autowired
    private ICondicaoPacienteService condicaoPacienteService;
    
    /**
     * Método para listar condições de paciente.
     * @return ResponseEntity<List<CondicaoPaciente>> listagem de condicoes de paciente e resposta HTTP.
     */
    @RequestMapping(method = RequestMethod.GET)
//    @JsonView(EvolucaoView.ListaEvolucao.class)
    public ResponseEntity<List<CondicaoPaciente>> listarCondicoesPaciente() {
        return new ResponseEntity<List<CondicaoPaciente>>(condicaoPacienteService.listarCondicoesPaciente(), HttpStatus.OK);
    } 
}