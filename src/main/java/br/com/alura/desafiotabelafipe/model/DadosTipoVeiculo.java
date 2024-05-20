package br.com.alura.desafiotabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTipoVeiculo(String codigo,
                               String nome) {
}
