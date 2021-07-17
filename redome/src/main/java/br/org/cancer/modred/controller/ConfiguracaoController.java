package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.service.IConfiguracaoService;

/**
 * Controlador para configuracao.
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ConfiguracaoController {
    
    @Autowired
    private IConfiguracaoService configuracaoService;
        
    /**
     * Retorna uma configuracao a partir de uma chave.
     * @param chave
     * @return ResponseEntity<List<Configuracao>>
     */
    @RequestMapping(value= "/api/configuracao/{chave}", method = RequestMethod.GET)
    public ResponseEntity<Configuracao> obterConfiguracaoPorChave(@PathVariable("chave") String chave) {
        return new ResponseEntity<Configuracao>(configuracaoService.obterConfiguracao(chave), HttpStatus.OK);
    }
    
    /**
     * Retorna uma lista de configuracao a partir de multiplas chaves.
     * 
     * @param String[] - chaves
     * @return ResponseEntity<List<Configuracao>>
     */
    @RequestMapping(value="/public/configuracao", method = RequestMethod.GET)
    public ResponseEntity<List<Configuracao>> listarConfiguracoes(@RequestParam(name="lista", required = true) String...lista) {
        return new ResponseEntity<List<Configuracao>>(configuracaoService.listarConfiguracao(lista), HttpStatus.OK);
    }
}
