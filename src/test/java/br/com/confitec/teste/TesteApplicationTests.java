package br.com.confitec.teste;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TesteApplicationTests {

	@Mock
	private ApoliceSeguroService apoliceMock;

	@BeforeEach
	public void setUp() {
		// Inicializar os mocks antes de cada teste
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCalcularPlanoPagamentoSemJuros() throws Exception {

		OpcaoParcelamentoRequest opcaoParcelamentoRequest = new OpcaoParcelamentoRequest(2,4,0.00);
		List<OpcaoParcelamentoRequest> opcaoParcelamentoRequestList = new ArrayList<>();
		opcaoParcelamentoRequestList.add(opcaoParcelamentoRequest);
		CoberturaRequest coberturaRequest = new CoberturaRequest(1,100d,opcaoParcelamentoRequestList);

		List<Response> responseResult = new ArrayList<>();
		Response responseMinimo = new Response(2,50d,50d,100d);
		Response responseMaximo = new Response(4,25d,25d,100d);

		responseResult.add(responseMaximo);
		responseResult.add(responseMinimo);

		apoliceMock = mock(ApoliceSeguroService.class);
		when(apoliceMock.obterParcelamento(coberturaRequest)).thenReturn(responseResult);

		ApoliceSeguroServiceImpl apoliceSeguroService = new ApoliceSeguroServiceImpl(apoliceMock);
		List<Response> result = apoliceSeguroService.obterParcelamento(coberturaRequest);


		Assertions.assertEquals(result.get(0).getQuantidadeParcelas(), 4);
		Assertions.assertEquals(result.get(0).getValorPrimeiraParcela(), 25);
		Assertions.assertEquals(result.get(0).getValorDemaisParcelas(), 25);
		Assertions.assertEquals(result.get(0).getValorParcelamentoTotal(), 100);

		Assertions.assertEquals(result.get(1).getQuantidadeParcelas(), 2);
		Assertions.assertEquals(result.get(1).getValorPrimeiraParcela(), 50);
		Assertions.assertEquals(result.get(1).getValorDemaisParcelas(), 50);
		Assertions.assertEquals(result.get(1).getValorParcelamentoTotal(), 100);

	}

	@Test
	public void testCalcularPlanoPagamentoComJuros() throws Exception {

		OpcaoParcelamentoRequest opcaoParcelamentoRequest = new OpcaoParcelamentoRequest(2,4,0.05);
		List<OpcaoParcelamentoRequest> opcaoParcelamentoRequestList = new ArrayList<>();
		opcaoParcelamentoRequestList.add(opcaoParcelamentoRequest);
		CoberturaRequest coberturaRequest = new CoberturaRequest(1,100d,opcaoParcelamentoRequestList);

		List<Response> responseResult = new ArrayList<>();
		Response responseMinimo = new Response(2,53.78d,53.78d,100d);
		Response responseMaximo = new Response(4,28.20d,28.20d,100d);

		responseResult.add(responseMaximo);
		responseResult.add(responseMinimo);

		apoliceMock = mock(ApoliceSeguroService.class);
		when(apoliceMock.obterParcelamento(coberturaRequest)).thenReturn(responseResult);

		ApoliceSeguroServiceImpl apoliceSeguroService = new ApoliceSeguroServiceImpl(apoliceMock);
		List<Response> result = apoliceSeguroService.obterParcelamento(coberturaRequest);



		Assertions.assertEquals(result.get(0).getQuantidadeParcelas(), 4);
		Assertions.assertEquals(decimalFormat(result.get(0).getValorPrimeiraParcela()), "28,2");
		Assertions.assertEquals(decimalFormat(result.get(0).getValorDemaisParcelas()), "28,2");
		Assertions.assertEquals(result.get(0).getValorParcelamentoTotal(), 100);

		Assertions.assertEquals(result.get(1).getQuantidadeParcelas(), 2);
		Assertions.assertEquals(decimalFormat(result.get(1).getValorPrimeiraParcela()), "53,78");
		Assertions.assertEquals(decimalFormat(result.get(1).getValorDemaisParcelas()), "53,78");
		Assertions.assertEquals(result.get(1).getValorParcelamentoTotal(), 100);

	}

	private String decimalFormat(Double valorPrimeiraParcela) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		return decimalFormat.format(valorPrimeiraParcela);
	}

}
