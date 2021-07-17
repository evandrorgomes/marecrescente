package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Motivo;
import br.org.cancer.modred.service.IMotivoService;

/**
 * Classe de REST controller para motivo de evolução paciente.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/motivos", produces = "application/json;charset=UTF-8")
public class MotivoController {

    @Autowired
    private IMotivoService motivoService;

    /**
     * Método para listar motivos de evolução de paciente.
     * 
     * @return ResponseEntity<List<Motivo>> listagem de motivos de evolução
     *         encontrados.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Motivo>> listarMotivosExcetoCadastroInicial() {
        return new ResponseEntity<List<Motivo>>(motivoService.listarMotivosExcetoCadastroInicial(),
                HttpStatus.OK);
    }
}
