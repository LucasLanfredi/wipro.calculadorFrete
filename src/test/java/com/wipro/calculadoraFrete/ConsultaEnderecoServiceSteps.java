package com.wipro.calculadoraFrete;

import com.wipro.calculadoraFrete.entities.EnderecoResponse;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;
import com.wipro.calculadoraFrete.services.ConsultaEnderecoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Cucumber.class)
@CucumberOptions
public class ConsultaEnderecoServiceSteps {

    private ViaCepResponse viaCepResponse;
    private ConsultaEnderecoService consultaEnderecoService;
    private EnderecoResponse enderecoResponse;

    @Given("um CEP válido {string}")
    public void umCEPValido(String cep) {
        viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep(cep);
    }

    @When("o serviço de consulta de endereço é chamado")
    public void oServicoDeConsultaDeEnderecoEChamado() {
        consultaEnderecoService = new ConsultaEnderecoService();
        enderecoResponse = consultaEnderecoService.getEnderecoResponse(viaCepResponse);
    }

    @Then("o endereço é retornado corretamente com o frete calculado")
    public void oEnderecoERetornadoCorretamenteComOFreteCalculado() {
        assertEquals(viaCepResponse.getCep(), enderecoResponse.getCep());
        assertEquals(viaCepResponse.getLogradouro(), enderecoResponse.getRua());
        assertEquals(viaCepResponse.getComplemento(), enderecoResponse.getComplemento());
        assertEquals(viaCepResponse.getBairro(), enderecoResponse.getBairro());
        assertEquals(viaCepResponse.getLocalidade(), enderecoResponse.getCidade());
        assertEquals(viaCepResponse.getUf(), enderecoResponse.getEstado());
        assertEquals(consultaEnderecoService.definirValorDoFrete("Sudeste"), enderecoResponse.getFrete());
    }
}
