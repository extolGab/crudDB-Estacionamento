package com.example.gabriel.h.services;

import com.example.gabriel.h.models.Veiculo;

import org.javalite.activejdbc.Base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class VeiculoService {

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

    // LISTAR TODOS OS VEÍCULOS
    public List<Veiculo> listarTodos() {

        try {
            abrirConexao();

            return new ArrayList<>(
                    Veiculo.findAll().orderBy("id DESC")
            );

        } finally {
            fecharConexao();
        }
    }

    // BUSCAR VEÍCULO POR ID
    public Veiculo buscarPorId(Long id) {

        try {
            abrirConexao();

            return Veiculo.findById(id);

        } finally {
            fecharConexao();
        }
    }

    // CADASTRAR VEÍCULO
    public void cadastrar(
            String placa,
            String modelo,
            String cor,
            String vaga) {

        try {
            abrirConexao();

            Veiculo veiculo = new Veiculo();

            veiculo.set("placa", placa.toUpperCase());
            veiculo.set("modelo", modelo);
            veiculo.set("cor", cor);
            veiculo.set("vaga", vaga);

            veiculo.set(
                    "entrada",
                    LocalDateTime.now().toString()
            );

            veiculo.set("saida", null);
            veiculo.set("valor", 0.0);

            veiculo.save();

        } finally {
            fecharConexao();
        }
    }

    // ATUALIZAR VEÍCULO
    public void atualizar(
            Long id,
            String placa,
            String modelo,
            String cor,
            String vaga) {

        try {
            abrirConexao();

            Veiculo veiculo = Veiculo.findById(id);

            if (veiculo == null) {
                throw new RuntimeException("Veículo não encontrado!");
            }

            veiculo.set("placa", placa.toUpperCase());
            veiculo.set("modelo", modelo);
            veiculo.set("cor", cor);
            veiculo.set("vaga", vaga);

            veiculo.save();

        } finally {
            fecharConexao();
        }
    }

    // EXCLUIR VEÍCULO
    public void excluir(Long id) {

        try {
            abrirConexao();

            Veiculo veiculo = Veiculo.findById(id);

            if (veiculo != null) {
                veiculo.delete();
            }

        } finally {
            fecharConexao();
        }
    }

    // REGISTRAR SAÍDA DO VEÍCULO
    public void registrarSaida(Long id) {

        try {
            abrirConexao();

            Veiculo veiculo = Veiculo.findById(id);

            if (veiculo == null) {
                throw new RuntimeException("Veículo não encontrado!");
            }

            if (veiculo.getString("saida") != null) {
                throw new RuntimeException(
                        "A saída deste veículo já foi registrada!"
                );
            }

            LocalDateTime entrada = LocalDateTime.parse(
                    veiculo.getString("entrada")
            );

            LocalDateTime saida = LocalDateTime.now();

            long minutos = Duration.between(
                    entrada,
                    saida
            ).toMinutes();

            // Valor de R$ 10,00 por hora ou fração.
            double horas = Math.max(
                    1,
                    Math.ceil(minutos / 60.0)
            );

            double valor = horas * 10.0;

            veiculo.set("saida", saida.toString());
            veiculo.set("valor", valor);

            veiculo.save();

        } finally {
            fecharConexao();
        }
    }
}