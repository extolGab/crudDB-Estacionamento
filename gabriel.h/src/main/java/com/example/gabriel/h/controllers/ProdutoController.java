
package com.example.gabriel.h.controllers;

import com.example.gabriel.h.models.Produto;
import com.example.gabriel.h.services.ProdutoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // LISTAR
    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "produtos",
                produtoService.listarTodos()
        );

        return "produtos/lista";
    }

    // NOVO PRODUTO
    @GetMapping("/novo")
    public String novo() {
        return "produtos/formulario";
    }

    // CADASTRAR
    @PostMapping("/salvar")
    public String salvar(
            @RequestParam String nome,
            @RequestParam(required = false) String descricao,
            @RequestParam String categoria,
            @RequestParam String codigo,
            @RequestParam int quantidade,
            @RequestParam int estoqueMinimo,
            @RequestParam double preco) {

        produtoService.cadastrar(
                nome, descricao, categoria, codigo,
                quantidade, estoqueMinimo, preco
        );

        return "redirect:/produtos";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Produto produto = produtoService.buscarPorId(id);

        if (produto == null) {
            return "redirect:/produtos";
        }

        model.addAttribute("produto", produto);

        return "produtos/formulario";
    }

    // ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public String atualizar(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam(required = false) String descricao,
            @RequestParam String categoria,
            @RequestParam String codigo,
            @RequestParam int quantidade,
            @RequestParam int estoqueMinimo,
            @RequestParam double preco) {

        produtoService.atualizar(
                id, nome, descricao, categoria, codigo,
                quantidade, estoqueMinimo, preco
        );

        return "redirect:/produtos";
    }

    // EXCLUIR
    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        produtoService.excluir(id);

        return "redirect:/produtos";
    }

    // ESTOQUE BAIXO
    @GetMapping("/estoque-baixo")
    public String estoqueBaixo(Model model) {

        model.addAttribute(
                "produtos",
                produtoService.listarEstoqueBaixo()
        );

        return "produtos/lista";
    }
}
