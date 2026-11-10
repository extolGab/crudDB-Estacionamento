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