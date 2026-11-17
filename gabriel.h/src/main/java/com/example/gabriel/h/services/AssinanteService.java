
package com.example.gabriel.h.services;

import com.example.gabriel.h.models.Assinante;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class AssinanteService {

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

    // LISTAR TODOS OS ASSINANTES
    public List<Assinante> listarTodos() {

        try {
            abrirConexao();

            return new ArrayList<>(
                    Assinante.findAll().orderBy("id DESC")
            );

        } finally {
            fecharConexao();
        }
    }

    // BUSCAR ASSINANTE POR ID
    public Assinante buscarPorId(Long id) {

        try {
            abrirConexao();

            return Assinante.findById(id);

        } finally {
            fecharConexao();
        }
    }

    // CADASTRAR ASSINANTE
    public void cadastrar(
            String nome,
            String email,
            String telefone,
            String cpf,
            String plano,
            double valor,
            String dataInicio,
            String dataFim,
            String status) {

        try {
            abrirConexao();

            Assinante assinante = new Assinante();

            assinante.set("nome", nome);
            assinante.set("email", email);
            assinante.set("telefone", telefone);
            assinante.set("cpf", cpf);
            assinante.set("plano", plano);
            assinante.set("valor", valor);
            assinante.set("data_inicio", dataInicio);
            assinante.set("data_fim", dataFim);
            assinante.set("status", status);

            assinante.save();

        } finally {
            fecharConexao();
        }
    }

    // ATUALIZAR ASSINANTE
    public void atualizar(
            Long id,
            String nome,
            String email,
            String telefone,
            String cpf,
            String plano,
            double valor,
            String dataInicio,
            String dataFim,
            String status) {

        try {
            abrirConexao();

            Assinante assinante = Assinante.findById(id);

            if (assinante == null) {
                throw new RuntimeException(
                        "Assinante não encontrado!"
                );
            }

            assinante.set("nome", nome);
            assinante.set("email", email);
            assinante.set("telefone", telefone);
            assinante.set("cpf", cpf);
            assinante.set("plano", plano);
            assinante.set("valor", valor);
            assinante.set("data_inicio", dataInicio);
            assinante.set("data_fim", dataFim);
            assinante.set("status", status);

            assinante.save();

        } finally {
            fecharConexao();
        }
    }

    // EXCLUIR ASSINANTE
    public void excluir(Long id) {

        try {
            abrirConexao();

            Assinante assinante = Assinante.findById(id);

            if (assinante != null) {
                assinante.delete();
            }

        } finally {
            fecharConexao();
        }
    }
}
