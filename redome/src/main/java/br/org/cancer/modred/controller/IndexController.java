package br.org.cancer.modred.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controlador responsável pelo redirecionamento para a página inicial.
 * 
 * @author Cintia Oliveira
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String getIndexPage() {
        return "index";
    }
}