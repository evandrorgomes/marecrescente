package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Municipio;
import br.org.cancer.modred.model.Uf;
import br.org.cancer.modred.service.IMunicipioService;
import br.org.cancer.modred.service.IUfService;

/**
 * Controlador para ufs.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/public/uf")
public class UfController {

    @Autowired
    private IUfService ufService;
    
    @Autowired
    private IMunicipioService municipioService;

    /**
     * Método rest obter lista ordenada de ufs.
     * 
     * @Return ResponseEntity<List<Uf>> lista de ufs
     */
    @GetMapping
    public ResponseEntity<List<Uf>> obterUfs() {
        return new ResponseEntity<>(ufService.listarUf(), HttpStatus.OK);
    }
    
    @GetMapping(value="semnaoinformado")
    public ResponseEntity<List<Uf>> obterUfsSemNaoInformado() {
        return ResponseEntity.ok(ufService.listarUfSemNaoInformado());
    }
    
    @GetMapping(value="{sigla}/municipios")
    public ResponseEntity<List<Municipio>> obterMunicipios(@PathVariable(name="sigla", required=true) String sigla) {
        return ResponseEntity.ok(municipioService.listarMunicipioPorUf(sigla));
    }
    
    
}
