package br.com.confitec.teste.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Response {

    private Integer quantidadeParcelas;
    private Double valorPrimeiraParcela;
    private Double valorDemaisParcelas;
    private Double valorParcelamentoTotal;

}
