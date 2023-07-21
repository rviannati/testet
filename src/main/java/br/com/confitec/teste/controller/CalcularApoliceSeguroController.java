package br.com.confitec.teste.controller;

import br.com.confitec.teste.request.CoberturaRequest;
import br.com.confitec.teste.response.Response;
import br.com.confitec.teste.service.ApoliceSeguroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/confitec/teste/")
public class CalcularApoliceSeguroController {

    private ApoliceSeguroService apoliceSeguroService;

        @PostMapping("/parcelamento")
        public ResponseEntity<List<Response>> parcelamento(@RequestBody CoberturaRequest coberturaRequest) throws Exception {
            List<Response> aulas = apoliceSeguroService.obterParcelamento(coberturaRequest);
            return ResponseEntity.ok(aulas);
        }
}
