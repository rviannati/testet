package br.com.confitec.teste.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class CoberturaRequest {
    private Integer cobertura;
    private Double valor;
    private List<OpcaoParcelamentoRequest> opcaoParcelamentoRequest;


}
