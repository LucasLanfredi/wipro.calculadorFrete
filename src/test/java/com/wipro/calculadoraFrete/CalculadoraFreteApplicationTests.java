package com.wipro.calculadoraFrete;

import com.wipro.calculadoraFrete.entities.EnderecoResponse;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;
import com.wipro.calculadoraFrete.services.ConsultaEnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculadoraFreteApplicationTests {

	private ConsultaEnderecoService consultaEnderecoService;

	@BeforeEach
	public void setUp() {
		consultaEnderecoService = new ConsultaEnderecoService();
	}

	@Test
	public void testValorFreteEnderecoResponse() {
//Cenario
		ViaCepResponse viaCepResponse = new ViaCepResponse();
		viaCepResponse.setCep("12345-678");
		viaCepResponse.setLogradouro("Rua dos Testes");
		viaCepResponse.setComplemento("Apto 123");
		viaCepResponse.setBairro("Centro");
		viaCepResponse.setLocalidade("São Paulo");
		viaCepResponse.setUf("SP");
//acao
		EnderecoResponse enderecoResponse = consultaEnderecoService.getdefinirFreteEndereco(viaCepResponse);
//validacao
		assertEquals("12345-678", enderecoResponse.getCep());
		assertEquals("Rua dos Testes", enderecoResponse.getRua());
		assertEquals("Apto 123", enderecoResponse.getComplemento());
		assertEquals("Centro", enderecoResponse.getBairro());
		assertEquals("São Paulo", enderecoResponse.getCidade());
		assertEquals("SP", enderecoResponse.getEstado());
		assertEquals(7.85, enderecoResponse.getFrete(), 0.001); // (foi colocado 0.001 como uma margem de erro para numeros flutuantes do java)
	}

	@Test
	public void testDefinirValorDoFrete() {
		Double frete = consultaEnderecoService.definirValorDoFrete("Sudeste");
		assertEquals(7.85, frete, 0.001);
	}

}
