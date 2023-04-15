package com.wipro.calculadoraFrete.unitarios;

import com.wipro.calculadoraFrete.entities.EnderecoResponse;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;
import com.wipro.calculadoraFrete.services.BuscarCep;
import com.wipro.calculadoraFrete.services.ConsultaEnderecoToFreteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalculadoraFreteApplicationTests {

	@Autowired
	private BuscarCep buscarCep;

	@Autowired
	private ConsultaEnderecoToFreteService consultaEnderecoToFreteService;

	private String cepExemplo = "02022-110";

	@Test
	public void testEnderecoResponse() {
//Cenario
		ViaCepResponse viaCepResponse = new ViaCepResponse();
		viaCepResponse.setCep("02022-110");
		viaCepResponse.setCidade("Rua Tenente Rocha");
		viaCepResponse.setComplemento("");
		viaCepResponse.setBairro("Santana");
		viaCepResponse.setLocalidade("SÃ£o Paulo");
		viaCepResponse.setEstado("SP");
//acao
		ViaCepResponse viaCepCompare = buscarCep.buscar(this.cepExemplo);
//validacao
		assertEquals(viaCepCompare.getCep(), viaCepResponse.getCep());
		assertEquals(viaCepCompare.getRua(), viaCepResponse.getRua());
		assertEquals(viaCepCompare.getComplemento(), viaCepResponse.getComplemento());
		assertEquals(viaCepCompare.getBairro(), viaCepResponse.getBairro());
		assertEquals(viaCepCompare.getCidade(), viaCepResponse.getCidade());
		assertEquals(viaCepCompare.getEstado(), viaCepResponse.getEstado());
	}

	@Test
	public void testValorFreteResponse() {
//Cenario
		ViaCepResponse viaCepResponse = new ViaCepResponse();
		viaCepResponse.setCep("12345-678");
		viaCepResponse.setCidade("Rua dos Testes");
		viaCepResponse.setComplemento("Apto 123");
		viaCepResponse.setBairro("Centro");
		viaCepResponse.setLocalidade("São Paulo");
		viaCepResponse.setEstado("SP");

		EnderecoResponse enderecoResponse = consultaEnderecoToFreteService.getdefinirFreteEndereco(viaCepResponse);

		assertEquals(7.85, enderecoResponse.getFrete(), 0.001); // (foi colocado 0.001 como uma margem de erro para numeros flutuantes do java)
	}


}
