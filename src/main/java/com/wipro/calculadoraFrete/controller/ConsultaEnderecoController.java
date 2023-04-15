package com.wipro.calculadoraFrete.controller;

import com.wipro.calculadoraFrete.exceptions.CepNotFoundException;
import com.wipro.calculadoraFrete.entities.EnderecoResponse;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;
import com.wipro.calculadoraFrete.services.BuscarCep;
import com.wipro.calculadoraFrete.services.ConsultaEnderecoToFreteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/v1")
@Tag(name = "Consulta Endereço", description = "API para consultar endereço por CEP")
public class ConsultaEnderecoController {

    @Autowired
    private ConsultaEnderecoToFreteService consultaEnderecoToFreteService;

    @Autowired
    private BuscarCep buscarCep;

    @Operation(summary = "Consulta endereço por CEP e retorno de frete", description = "Consulta do valor do frete a partir da pesquisa de um CEP informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Irá retornar o CEP e valor do Frete", content = @Content(schema = @Schema(implementation = EnderecoResponse.class))),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado")
    })
    @PostMapping("/consulta-endereco")
    public Mono<ResponseEntity<?>> consultaEndereco(@RequestBody String request) throws CepNotFoundException {

        ViaCepResponse viaCepResponse = buscarCep.buscar(request);

        EnderecoResponse enderecoResponse = consultaEnderecoToFreteService.getdefinirFreteEndereco(viaCepResponse);//Mandando para o service inserir o valor do frete
        return Mono.just(ResponseEntity.ok(enderecoResponse));
    }
}