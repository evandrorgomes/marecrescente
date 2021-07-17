package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.service.IPermissaoService;

/**
 * Classe controladora responsável por fazer o dispatch das requisições relacionadas à manutenção das permissões 
 * de acesso da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
@RestController
@RequestMapping(value = "/api/permissoes", produces = "application/json;charset=UTF-8")
public class PermissaoController {

    
    @Autowired
    private IPermissaoService permissaoService;

    @Autowired
    private MessageSource messageSource;
}
