package com.example.gabriel.h.controllers;

import com.example.gabriel.h.models.Veiculo;
import com.example.gabriel.h.services.VeiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/veiculos")
public class VeiculosController {

    @Autowired
    private VeiculoService veiculoService;

    // PÁGINA INICIAL - LISTAR VEÍCULOS
    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "veiculos",
                veiculoService.listarTodos()
        );

        return "veiculos/index";
    }

    // ABRIR FORMULÁRIO DE CADASTRO
    @GetMapping("/novo")
    public String novo() {

        return "veiculos/form";
    }

    // CADASTRAR VEÍCULO
    @PostMapping("/salvar")
    public String salvar(
            @RequestParam String placa,
            @RequestParam String modelo,
            @RequestParam String cor,
            @RequestParam String vaga) {

        veiculoService.cadastrar(
                placa,
                modelo,
                cor,
                vaga
        );

        return "redirect:/veiculos";
    }

    // ABRIR FORMULÁRIO DE EDIÇÃO
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Veiculo veiculo = veiculoService.buscarPorId(id);

        if (veiculo == null) {
            return "redirect:/veiculos";
        }

        model.addAttribute("veiculo", veiculo);

        return "veiculos/form";
    }

    // ATUALIZAR VEÍCULO
    @PostMapping("/atualizar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @RequestParam String placa,
            @RequestParam String modelo,
            @RequestParam String cor,
            @RequestParam String vaga) {

        veiculoService.atualizar(
                id,
                placa,
                modelo,
                cor,
                vaga
        );

        return "redirect:/veiculos";
    }

    // EXCLUIR VEÍCULO
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        veiculoService.excluir(id);

        return "redirect:/veiculos";
    }

    // REGISTRAR SAÍDA
    @PostMapping("/saida/{id}")
    public String saida(@PathVariable Long id) {

        veiculoService.registrarSaida(id);

        return "redirect:/veiculos";
    }
}