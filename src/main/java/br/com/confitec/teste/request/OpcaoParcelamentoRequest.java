package br.com.confitec.teste.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OpcaoParcelamentoRequest {
    private Integer quantidadeMinimaParcelas;
    private Integer quantidadeMaximaParcelas;
    private Double juros;
}
