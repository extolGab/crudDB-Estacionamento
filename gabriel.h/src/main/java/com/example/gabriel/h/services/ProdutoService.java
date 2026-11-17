
package com.example.gabriel.h.services;

import com.example.gabriel.h.models.Produto;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Value("${database.driver}")
    private String driver;

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    private void abrirConexao() {
        Base.open(driver, url, username, password);
    }

    private void fecharConexao() {
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    // LISTAR TODOS OS PRODUTOS
    public List<Produto> listarTodos() {

        try {
            abrirConexao();

            return new ArrayList<>(
                    Produto.findAll().orderBy("id DESC")
            );

        } finally {
            fecharConexao();
        }
    }

    // BUSCAR POR ID
    public Produto buscarPorId(Long id) {

        try {
            abrirConexao();

            return Produto.findById(id);

        } finally {
            fecharConexao();
        }
    }

    // CADASTRAR PRODUTO
    public void cadastrar(
            String nome,
            String descricao,
            String categoria,
            String codigo,
            int quantidade,
            int estoqueMinimo,
            double preco) {

        try {
            abrirConexao();

            Produto produto = new Produto();

            produto.set("nome", nome);
            produto.set("descricao", descricao);
            produto.set("categoria", categoria);
            produto.set("codigo", codigo);
            produto.set("quantidade", quantidade);
            produto.set("estoque_minimo", estoqueMinimo);
            produto.set("preco", preco);

            produto.save();

        } finally {
            fecharConexao();
        }
    }

    // ATUALIZAR PRODUTO
    public void atualizar(
            Long id,
            String nome,
            String descricao,
            String categoria,
            String codigo,
            int quantidade,
            int estoqueMinimo,
            double preco) {

        try {
            abrirConexao();

            Produto produto = Produto.findById(id);

            if (produto == null) {
                throw new RuntimeException(
                        "Produto não encontrado!"
                );
            }

            produto.set("nome", nome);
            produto.set("descricao", descricao);
            produto.set("categoria", categoria);
            produto.set("codigo", codigo);
            produto.set("quantidade", quantidade);
            produto.set("estoque_minimo", estoqueMinimo);
            produto.set("preco", preco);

            produto.save();

        } finally {
            fecharConexao();
        }
    }

    // EXCLUIR PRODUTO
    public void excluir(Long id) {

        try {
            abrirConexao();

            Produto produto = Produto.findById(id);

            if (produto != null) {
                produto.delete();
            }

        } finally {
            fecharConexao();
        }
    }

    // LISTAR PRODUTOS COM ESTOQUE BAIXO
    public List<Produto> listarEstoqueBaixo() {

        try {
            abrirConexao();

            return new ArrayList<>(
                    Produto.where(
                            "quantidade <= estoque_minimo"
                    ).orderBy("quantidade ASC")
            );

        } finally {
            fecharConexao();
        }
    }
}
