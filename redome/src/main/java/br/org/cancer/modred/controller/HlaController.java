package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.service.IValorGService;
import br.org.cancer.modred.service.IValorNmdpService;
import br.org.cancer.modred.service.IValorPService;

/**
 * Controlador para obter o conjunto de valores de NMDP, P e G .
 * 
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(value = "/api/hla", produces = "application/json;charset=UTF-8")
public class HlaController {

    @Autowired
    private IValorNmdpService valorNmdpService;
    
    @Autowired
    private IValorPService valorPService;
    
    @Autowired
    private IValorGService valorGService;

    /**
     * Método rest obter conjunto de valores de um código nmdp.
     * 
     * @return String grupo de valores
     */
    @RequestMapping(value = "nmdp/{codigo}", method = RequestMethod.GET)
    public ResponseEntity<String> getSubTiposNmdp(@PathVariable(name = "codigo", required = true) String codigo) {
        return new ResponseEntity<String>(valorNmdpService.obterSubTipos(codigo), HttpStatus.OK);
    }
    
    /**
     * Método rest obter conjunto de valores de um grupo P.
     * 
     * @return String grupo de valores
     */
    @RequestMapping(value = "p", method = RequestMethod.GET)
    public ResponseEntity<String> getGrupoValorP(@RequestParam(name="locus", required=true) String locusCodigo, @RequestParam(name="grupo", required=false) String nomeGrupo) {
        return new ResponseEntity<String>(valorPService.obterGrupo(locusCodigo, nomeGrupo), HttpStatus.OK);
    }
    
    /**
     * Método rest obter conjunto de valores de um grupo G.
     * 
     * @return String grupo de valores
     */
    @RequestMapping(value = "g", method = RequestMethod.GET)
    public ResponseEntity<String> getGrupoValorG(@RequestParam(name="locus", required=true) String locusCodigo, @RequestParam(name="grupo", required=false) String nomeGrupo) {
        return new ResponseEntity<String>(valorGService.obterGrupo(locusCodigo, nomeGrupo), HttpStatus.OK);
    }
    
}
