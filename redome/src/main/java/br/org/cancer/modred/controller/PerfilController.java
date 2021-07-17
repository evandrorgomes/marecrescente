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
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.service.IPerfilService;

/**
 * Classe controladora responsável por fazer o dispatch das requisições relacionadas à manutenção de perfis de acesso da 
 * plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
@RestController
@RequestMapping(value = "/api/perfis", produces = "application/json;charset=UTF-8")
public class PerfilController {

    @Autowired
    private IPerfilService perfilService;
    
    /**
     * Lista todos os perfis disponíveis na base do Redome.
     * 
     * @return lista de perfis.
     */
    @RequestMapping(method = RequestMethod.GET)
    @JsonView(UsuarioView.Consultar.class)
    public ResponseEntity<List<Perfil>> listarPerfis(){
        return new ResponseEntity<List<Perfil>>(perfilService.listarDisponiveisParaRedome(), HttpStatus.OK);
    }

}
