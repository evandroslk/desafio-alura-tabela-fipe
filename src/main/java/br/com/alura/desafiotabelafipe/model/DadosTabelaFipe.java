package br.com.alura.desafiotabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTabelaFipe (@JsonAlias("Modelo") String modelo,
                               @JsonAlias("AnoModelo") Integer anoModelo,
                               @JsonAlias("Combustivel") String combustivel,
                               @JsonAlias("Valor") String valor) {
}
