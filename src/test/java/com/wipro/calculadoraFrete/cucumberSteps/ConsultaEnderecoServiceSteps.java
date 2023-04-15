package com.wipro.calculadoraFrete.cucumberSteps;

import com.wipro.calculadoraFrete.entities.EnderecoResponse;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;
import com.wipro.calculadoraFrete.exceptions.CepNotFoundException;
import com.wipro.calculadoraFrete.services.BuscarCep;
import com.wipro.calculadoraFrete.services.ConsultaEnderecoToFreteService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@CucumberContextConfiguration
public class ConsultaEnderecoServiceSteps {

    @Autowired
    private BuscarCep buscarCep;

    @Autowired
    private ConsultaEnderecoToFreteService consultaEnderecoToFreteService;

    private CepNotFoundException excecao = new CepNotFoundException();

    @Mock
    private String cep;

    @Mock
    private ViaCepResponse viaCepResponse;

    @Given("um CEP válido {string}")
    public void umCEPValido(String cep) {
        this.cep = cep;
    }

    @Given("um CEP invalido {string}")
    public void umCEPInValido(String cep) {
        this.cep = cep;
    }

    @When("o serviço de consulta de endereço é chamado")
    public void o_usuario_busca_pelo_CEP() {
        try {
            this.viaCepResponse = this.buscarCep.buscar(this.cep);
        } catch (CepNotFoundException e) {
            excecao = e;
        }
    }

    @Then("o serviço retorna um objeto EnderecoResponse com os dados do CEP e frete")
    public void o_servico_retorna_um_objeto_ViaCepResponse_com_os_dados_do_CEP() {
        assertNotNull(viaCepResponse);
        EnderecoResponse enderecoResponse = this.consultaEnderecoToFreteService.getdefinirFreteEndereco(viaCepResponse);
        assertEquals(viaCepResponse.getCep(), enderecoResponse.getCep());
        assertEquals(viaCepResponse.getRua(), enderecoResponse.getRua());
        assertEquals(viaCepResponse.getComplemento(), enderecoResponse.getComplemento());
        assertEquals(viaCepResponse.getBairro(), enderecoResponse.getBairro());
        assertEquals(viaCepResponse.getCidade(), enderecoResponse.getCidade());
        assertEquals(viaCepResponse.getEstado(), enderecoResponse.getEstado());
        assertEquals(7.85, enderecoResponse.getFrete(), 0.001);
    }

    @Then("o serviço lança a exceção CepNotFoundException caso Cep esteja incorreto")
    public void o_servico_lanca_a_excecao_CepNotFoundException() {
        assertNotNull(excecao);
        assertEquals(CepNotFoundException.class, excecao.getClass());
    }
}
