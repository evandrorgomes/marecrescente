package br.org.cancer.redome.workup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.workup.model.FonteCelula;
import br.org.cancer.redome.workup.service.IFonteCelulaService;

/**
 * Classe de REST controller para fonte de celula.
 * 
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class FonteCelulaController {

    @Autowired
    private IFonteCelulaService fonteCelulaService;

    /**
     * Método para listar fonte de celula.
     * 
     * @return ResponseEntity<List<FonteCelula>> listagem de fonte de celula.
     */
    @GetMapping("/fontescelulas")
    @PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
    public ResponseEntity<List<FonteCelula>> listarFontesCelulas(
    		@RequestParam(value="siglas") String[] siglas) {
        return new ResponseEntity<List<FonteCelula>>(fonteCelulaService.findBySigla(siglas),
                HttpStatus.OK);
    }
    
}
