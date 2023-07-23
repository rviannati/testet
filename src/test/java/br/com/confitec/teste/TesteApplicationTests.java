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

		/*
	Teste com o calculo sem juros
	 */
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

	/*
	Teste com o calculo com juros
	 */
	@Test
	public void testCalcularPlanoPagamentoComJuros() throws Exception {

		OpcaoParcelamentoRequest opcaoParcelamentoRequest = new OpcaoParcelamentoRequest(2,4,0.05);
		List<OpcaoParcelamentoRequest> opcaoParcelamentoRequestList = new ArrayList<>();
		opcaoParcelamentoRequestList.add(opcaoParcelamentoRequest);
		CoberturaRequest coberturaRequest = new CoberturaRequest(1,100d,opcaoParcelamentoRequestList);

		List<Response> responseResult = new ArrayList<>();
		Response responseMinimo = new Response(2,53.78,53.78,100d);
		Response responseMaximo = new Response(4,28.20,28.20,100d);

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

	/*
        Teste com o calculo com juros e rateio de parcelas
    */
	@Test
	public void testCalcularPlanoPagamentoComJurosRateio() throws Exception {

		OpcaoParcelamentoRequest opcaoParcelamentoRequest = new OpcaoParcelamentoRequest(3,7,0.05);
		List<OpcaoParcelamentoRequest> opcaoParcelamentoRequestList = new ArrayList<>();
		opcaoParcelamentoRequestList.add(opcaoParcelamentoRequest);
		CoberturaRequest coberturaRequest = new CoberturaRequest(1,100d,opcaoParcelamentoRequestList);

		List<Response> responseResult = new ArrayList<>();
		Response responseMinimo = new Response(3,37.72,36.72,100d);
		Response responseMaximo = new Response(7,19.28,17.28,100d);

		responseResult.add(responseMaximo);
		responseResult.add(responseMinimo);

		apoliceMock = mock(ApoliceSeguroService.class);
		when(apoliceMock.obterParcelamento(coberturaRequest)).thenReturn(responseResult);

		ApoliceSeguroServiceImpl apoliceSeguroService = new ApoliceSeguroServiceImpl(apoliceMock);
		List<Response> result = apoliceSeguroService.obterParcelamento(coberturaRequest);


		Assertions.assertEquals(result.get(0).getQuantidadeParcelas(), 7);
		Assertions.assertEquals(decimalFormat(result.get(0).getValorPrimeiraParcela()), "19,28");
		Assertions.assertEquals(decimalFormat(result.get(0).getValorDemaisParcelas()), "17,28");
		Assertions.assertEquals(result.get(0).getValorParcelamentoTotal(), 100);

		Assertions.assertEquals(result.get(1).getQuantidadeParcelas(), 3);
		Assertions.assertEquals(decimalFormat(result.get(1).getValorPrimeiraParcela()), "37,72");
		Assertions.assertEquals(decimalFormat(result.get(1).getValorDemaisParcelas()), "36,72");
		Assertions.assertEquals(result.get(1).getValorParcelamentoTotal(), 100);

	}

	/*
        Teste com o calculo sem juros e rateio de parcelas
    */
	@Test
	public void testCalcularPlanoPagamentoSemJurosRateio() throws Exception {

		OpcaoParcelamentoRequest opcaoParcelamentoRequest = new OpcaoParcelamentoRequest(3,7,0.00);
		List<OpcaoParcelamentoRequest> opcaoParcelamentoRequestList = new ArrayList<>();
		opcaoParcelamentoRequestList.add(opcaoParcelamentoRequest);
		CoberturaRequest coberturaRequest = new CoberturaRequest(1,100d,opcaoParcelamentoRequestList);

		List<Response> responseResult = new ArrayList<>();
		Response responseMinimo = new Response(3,34.33,33.33,100d);
		Response responseMaximo = new Response(7,16.29,14.29,100d);

		responseResult.add(responseMaximo);
		responseResult.add(responseMinimo);

		apoliceMock = mock(ApoliceSeguroService.class);
		when(apoliceMock.obterParcelamento(coberturaRequest)).thenReturn(responseResult);

		ApoliceSeguroServiceImpl apoliceSeguroService = new ApoliceSeguroServiceImpl(apoliceMock);
		List<Response> result = apoliceSeguroService.obterParcelamento(coberturaRequest);


		Assertions.assertEquals(result.get(0).getQuantidadeParcelas(), 7);
		Assertions.assertEquals(decimalFormat(result.get(0).getValorPrimeiraParcela()), "16,29");
		Assertions.assertEquals(decimalFormat(result.get(0).getValorDemaisParcelas()), "14,29");
		Assertions.assertEquals(result.get(0).getValorParcelamentoTotal(), 100);

		Assertions.assertEquals(result.get(1).getQuantidadeParcelas(), 3);
		Assertions.assertEquals(decimalFormat(result.get(1).getValorPrimeiraParcela()), "34,33");
		Assertions.assertEquals(decimalFormat(result.get(1).getValorDemaisParcelas()), "33,33");
		Assertions.assertEquals(result.get(1).getValorParcelamentoTotal(), 100);

	}

	private String decimalFormat(Double valor) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		return decimalFormat.format(valor);
	}


}
