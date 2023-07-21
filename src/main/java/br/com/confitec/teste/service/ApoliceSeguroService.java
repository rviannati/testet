package br.com.confitec.teste.service;

import br.com.confitec.teste.request.CoberturaRequest;
import br.com.confitec.teste.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApoliceSeguroService {
    List<Response> obterParcelamento(CoberturaRequest coberturaRequest) throws Exception;
}
