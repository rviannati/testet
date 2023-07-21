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
	public void testCalcularPlanoPagamentoComJuros() {
	}

}
