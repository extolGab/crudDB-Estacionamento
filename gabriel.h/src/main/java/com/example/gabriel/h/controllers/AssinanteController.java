
package com.example.gabriel.h.controllers;

import com.example.gabriel.h.models.Assinante;
import com.example.gabriel.h.services.AssinanteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assinantes")
public class AssinanteController {

    private final AssinanteService assinanteService;

    public AssinanteController(AssinanteService assinanteService) {
        this.assinanteService = assinanteService;
    }

    // LISTAR
    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "assinantes",
                assinanteService.listarTodos()
        );

        return "assinantes/lista";
    }

    // ABRIR FORMULÁRIO DE CADASTRO
    @GetMapping("/novo")
    public String novo() {

        return "assinantes/formulario";
    }

    // CADASTRAR
    @PostMapping("/salvar")
    public String salvar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String cpf,
            @RequestParam String plano,
            @RequestParam double valor,
            @RequestParam String dataInicio,
            @RequestParam(required = false) String dataFim,
            @RequestParam(defaultValue = "ATIVO") String status) {

        assinanteService.cadastrar(
                nome, email, telefone, cpf, plano,
                valor, dataInicio, dataFim, status
        );

        return "redirect:/assinantes";
    }

    // ABRIR FORMULÁRIO DE EDIÇÃO
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Assinante assinante =
                assinanteService.buscarPorId(id);

        if (assinante == null) {
            return "redirect:/assinantes";
        }

        model.addAttribute("assinante", assinante);

        return "assinantes/formulario";
    }

    // ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String cpf,
            @RequestParam String plano,
            @RequestParam double valor,
            @RequestParam String dataInicio,
            @RequestParam(required = false) String dataFim,
            @RequestParam String status) {

        assinanteService.atualizar(
                id, nome, email, telefone, cpf, plano,
                valor, dataInicio, dataFim, status
        );

        return "redirect:/assinantes";
    }

    // EXCLUIR
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        assinanteService.excluir(id);

        return "redirect:/assinantes";
    }
}
