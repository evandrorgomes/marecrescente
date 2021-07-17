package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.HemoEntidade;
import br.org.cancer.modred.service.IHemoEntidadeService;

/**
 * Controlador para definição de chamadas REST envolvendo a entidade HemoEntidade.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/hemoentidades")
public class HemoEntidadeController {
    
    @Autowired
    private IHemoEntidadeService hemoService;
    
    /**
     * Lista as hemocentros e hemonúcleos próximos ao doador informado.
     * 
     * @param uf uf.
     * @return lista de hemoentidades.
     */
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasPermission('CONTACTAR_FASE_3')")
    public ResponseEntity<List<HemoEntidade>> listarHemoEntidades(@RequestParam(name="uf") String uf) {
        return new ResponseEntity<List<HemoEntidade>>(
        		hemoService.listarHemoEntidadesPorUf(uf), HttpStatus.OK);
    }
}
