package br.com.alura.desafiotabelafipe.principal;

import br.com.alura.desafiotabelafipe.model.DadosModelos;
import br.com.alura.desafiotabelafipe.model.DadosTabelaFipe;
import br.com.alura.desafiotabelafipe.model.DadosTipoVeiculo;
import br.com.alura.desafiotabelafipe.service.ConsumoAPI;
import br.com.alura.desafiotabelafipe.service.ConverteDados;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_API_CARROS = "https://parallelum.com.br/fipe/api/v1/carros/marcas/";

    public void exibeMenuInicial() {
        System.out.println("----------------------------------------");
        System.out.println("Selecione o tipo de veículo desejado");
        System.out.println("1 - Carro");
        System.out.println("2 - Caminhão");
        System.out.println("3 - Moto");
        System.out.println("4 - Sair");
        System.out.println("----------------------------------------");

        var tipoVeiculo = leitura.nextLine();

        try {
            switch (Integer.parseInt(tipoVeiculo)) {
                case 1:
                    exibeModelosDeCarros();
                    break;
                case 2:
                    exibeModelosDeCaminhoes();
                    break;
                case 3:
                    exibeModelosDeMotos();
                    break;
                case 4:
                    System.out.println("Até Logo!!!");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Opção inválida");
        }

    }

    private void exibeModelosDeCarros() {
        var json = consumoAPI.obterDados(URL_API_CARROS);
        var dados = conversor.obterDados(json, DadosTipoVeiculo[].class);
        System.out.println("Selecione a marca do carro desejada pelo código indicado");
        Arrays.stream(dados)
                .sorted(Comparator.comparing(DadosTipoVeiculo::codigo))
                .forEach(d -> {
            System.out.println("Código: " + d.codigo() + " | " +
                "Nome: " + d.nome());
        });

        var codigoSelecionado = leitura.nextLine();

        json = consumoAPI
                .obterDados(URL_API_CARROS + codigoSelecionado + "/modelos/");
        var dadosModelos = conversor.obterDados(json, DadosModelos.class);

        dadosModelos.modelos().stream().forEach(d -> {
            System.out.println("Código: " + d.codigo() + " | " +
                    "Nome: " + d.nome());
        });
        System.out.println("Digite um trecho do modelo do veículo que deseja pesquisar");

        var trechoModelo = leitura.nextLine();
        dadosModelos.modelos().stream()
                .filter(d -> d.nome().toUpperCase().contains(trechoModelo.toUpperCase()))
                .forEach(d -> {
                        System.out.println("Código: " + d.codigo() + " | " +
                                "Nome: " + d.nome());
                });
        System.out.println("Selecione um dos modelos acima pelo código");

        var codigoModelo = leitura.nextLine();

        json = consumoAPI
                .obterDados(URL_API_CARROS + codigoSelecionado + "/modelos/"
                    + codigoModelo + "/anos/");
        var dadosAnos = conversor.obterDados(json, DadosTipoVeiculo[].class);

        Arrays.stream(dadosAnos).forEach(d -> {
            var jsonFipe = consumoAPI
                    .obterDados(URL_API_CARROS + codigoSelecionado + "/modelos/"
                            + codigoModelo + "/anos/" + d.codigo());
            var dadosTabelaFipe = conversor.obterDados(jsonFipe, DadosTabelaFipe.class);
            System.out.println(dadosTabelaFipe.modelo()
                    + " ano: " + dadosTabelaFipe.anoModelo()
                    + " valor: " + dadosTabelaFipe.valor()
                    + " combustível: " + dadosTabelaFipe.combustivel());
        });
    }

    private void exibeModelosDeCaminhoes() {
        System.out.println("Caminhões");
    }

    private void exibeModelosDeMotos() {
        System.out.println("Motos");
    }

}
