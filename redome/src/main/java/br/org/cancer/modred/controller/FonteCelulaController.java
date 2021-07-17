package br.org.cancer.modred.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.FonteCelula;
import br.org.cancer.modred.service.IFonteCelulaService;

/**
 * Classe de REST controller para fonte de celula.
 * 
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(value = "/api/fontescelulas", produces = "application/json;charset=UTF-8")
public class FonteCelulaController {

    @Autowired
    private IFonteCelulaService fonteCelulaService;

    /**
     * Método para listar fonte de celula.
     * 
     * @return ResponseEntity<List<FonteCelula>> listagem de fonte de celula.
     */
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO') || hasPermission('CADASTRAR_DOADOR_INTERNACIONAL')")
    public ResponseEntity<List<FonteCelula>> listarFontesCelulas(
    		@QueryParam(value="siglas") String[] siglas) {
        return new ResponseEntity<List<FonteCelula>>(fonteCelulaService.findBySigla(siglas),
                HttpStatus.OK);
    }
}
