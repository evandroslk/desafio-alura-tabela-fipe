package br.com.alura.desafiotabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTipoVeiculo(Integer codigo,
                               String nome) {
}
