package com.example.gabriel.h.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("veiculos")
public class Veiculo extends Model {

    public Long getId() {
        return getLong("id");
    }

    public String getPlaca() {
        return getString("placa");
    }

    public String getModelo() {
        return getString("modelo");
    }

    public String getCor() {
        return getString("cor");
    }

    public String getVaga() {
        return getString("vaga");
    }

    public String getEntrada() {
        return getString("entrada");
    }

    public String getSaida() {
        return getString("saida");
    }

    public Double getValor() {
        return getDouble("valor");
    }
}