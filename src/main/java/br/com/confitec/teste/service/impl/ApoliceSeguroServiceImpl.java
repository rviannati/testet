package br.com.confitec.teste.service.impl;

import br.com.confitec.teste.request.CoberturaRequest;
import br.com.confitec.teste.request.OpcaoParcelamentoRequest;
import br.com.confitec.teste.response.Response;
import br.com.confitec.teste.service.ApoliceSeguroService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ApoliceSeguroServiceImpl implements ApoliceSeguroService {

    private ApoliceSeguroService dataService;

    public ApoliceSeguroServiceImpl(ApoliceSeguroService dataService) {
        super();
        this.dataService = dataService;
    }

    private double valorTotal;
    private int quantidadeParcelas;
    private double taxaJuros;

    public List<Response> obterParcelamento(CoberturaRequest coberturaRequest) throws Exception {
        List<Response> responses = new ArrayList<>();

        for(OpcaoParcelamentoRequest p : coberturaRequest.getOpcaoParcelamentoRequest()) {
            Response responseMaxima = calcularPlanoPagamento(coberturaRequest.getValor(), p.getQuantidadeMaximaParcelas(), p.getJuros());
            Response responseMinima = calcularPlanoPagamento(coberturaRequest.getValor(), p.getQuantidadeMinimaParcelas(), p.getJuros());
            responses.add(responseMaxima);
            responses.add(responseMinima);
        }

        return responses;

    }


    private Response calcularPlanoPagamento(double valorTotal, int quantidadeParcelas, double taxaJuros) throws Exception {

        this.valorTotal = valorTotal;
        this.quantidadeParcelas = quantidadeParcelas;
        this.taxaJuros = taxaJuros;


        // Verificar se as entradas são válidas (não negativas)
        if (quantidadeParcelas <= 0 || taxaJuros < 0) {
            throw new Exception("Quantidade de parcelas e taxa de juros devem ser valores positivos.");
        }

        // Verificar se é necessário aplicar juros
        if (taxaJuros > 0) {
            double valorParcela = calcularParcelaComJuros();
            return gerarPlanoPagamento(valorParcela);
        } else {
            double valorParcela = calcularParcelaSemJuros();
            return gerarPlanoPagamento(valorParcela);
        }
    }

    private double calcularParcelaComJuros() {
        double taxaJurosComposta = Math.pow(1 + taxaJuros, quantidadeParcelas);
        return valorTotal * (taxaJurosComposta * taxaJuros) / (taxaJurosComposta - 1);
    }

    private double calcularParcelaSemJuros() {
        return valorTotal / quantidadeParcelas;
    }

    public Response gerarPlanoPagamento(double valorParcela) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        StringBuilder planoPagamento = new StringBuilder();

        double restoDivisao = valorTotal % quantidadeParcelas;
        double valorParcelaComResto = valorParcela + restoDivisao;

        Response response = Response.builder()
            .valorParcelamentoTotal(valorTotal)
            .quantidadeParcelas(quantidadeParcelas)
            .valorPrimeiraParcela(valorParcelaComResto)
            .valorDemaisParcelas(valorParcela)
            .build();




        return response;
    }
}
