package com.wipro.calculadoraFrete.controller;

import com.wipro.calculadoraFrete.entities.EnderecoResponse;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;
import com.wipro.calculadoraFrete.services.ConsultaEnderecoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Controller
@RequestMapping("/v1")
@Api(value = "Consulta de Endereço", tags = "Consulta de Endereço")
public class ConsultaEnderecoController {

    @Autowired
    private ConsultaEnderecoService consultaEnderecoService;

    HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofMillis(5000));

    ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

    private final  WebClient webClient = WebClient.builder()
            .clientConnector(connector)
            .baseUrl("https://viacep.com.br")
            .build();


    @PostMapping("/consulta-endereco")
    @ApiOperation(value = "Realiza a consulta de endereço e devolver o valor do frete", notes = "Realiza a consulta de endereço com base em um CEP informado")
    public Mono<ResponseEntity<?>> consultaEndereco(@RequestBody String request) {
        String cep = request.replaceAll("\\D", "").replace("-", ""); //Limpando dado do usuario
        String url = "/ws/" + cep + "/json/"; //criando url do request

        return webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ViaCepResponse.class)// utilizando WebClient para fazer a requisição e receber um valor, quer será setado em ViaCepResponse.class
                .flatMap(viaCepResponse -> {
                    if (viaCepResponse == null || viaCepResponse.getCep() == null) { //Testando se o cep não foi encontrado ou é null
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("CEP não encontrado"));
                    }
                    EnderecoResponse enderecoResponse = consultaEnderecoService.getEnderecoResponse(viaCepResponse);//Mandando para o service inserir o valor do frete
                    return Mono.just(ResponseEntity.ok(enderecoResponse));
                });
    }
}