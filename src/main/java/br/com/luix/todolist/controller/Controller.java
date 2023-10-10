package br.com.luix.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Controller {
    // METODO RETORNO DA FUNÇÃO E PARAMS
    @GetMapping("/")
    public String helloWorld(){
        return "Hello World!";
    }
}
