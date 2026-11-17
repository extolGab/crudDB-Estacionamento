package com.example.gabriel.h.config;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Value("${database.driver}")
    private String driver;

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @EventListener(ApplicationReadyEvent.class)
    public void inicializarBanco() {

        try {
            Base.open(driver, url, username, password);

            Base.exec(
                    "CREATE TABLE IF NOT EXISTS veiculos (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "placa TEXT NOT NULL UNIQUE, " +
                            "modelo TEXT NOT NULL, " +
                            "cor TEXT NOT NULL, " +
                            "vaga TEXT NOT NULL, " +
                            "entrada TEXT NOT NULL, " +
                            "saida TEXT, " +
                            "valor REAL DEFAULT 0" +
                            ")"
            );

            Base.exec(
                    "CREATE TABLE IF NOT EXISTS assinantes (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "nome TEXT NOT NULL, " +
                            "email TEXT NOT NULL, " +
                            "telefone TEXT, " +
                            "cpf TEXT, " +
                            "plano TEXT NOT NULL, " +
                            "valor REAL NOT NULL, " +
                            "data_inicio TEXT NOT NULL, " +
                            "data_fim TEXT, " +
                            "status TEXT NOT NULL DEFAULT 'ATIVO', " +
                            "data_cadastro TEXT DEFAULT CURRENT_TIMESTAMP" +
                            ")"
            );

            Base.exec(
                    "CREATE TABLE IF NOT EXISTS produtos (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "nome TEXT NOT NULL, " +
                            "descricao TEXT, " +
                            "categoria TEXT NOT NULL, " +
                            "codigo TEXT NOT NULL UNIQUE, " +
                            "quantidade INTEGER NOT NULL DEFAULT 0, " +
                            "estoque_minimo INTEGER NOT NULL DEFAULT 5, " +
                            "preco REAL NOT NULL, " +
                            "data_cadastro TEXT DEFAULT CURRENT_TIMESTAMP" +
                            ")"
            );

            System.out.println("Banco SQLite inicializado!");
            System.out.println("Tabela veiculos criada/verificada!");

        } catch (Exception e) {

            System.err.println(
                    "Erro ao inicializar banco: " + e.getMessage()
            );

            throw new RuntimeException(e);

        } finally {

            if (Base.hasConnection()) {
                Base.close();
            }
        }
    }
}