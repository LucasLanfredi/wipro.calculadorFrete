package com.wipro.calculadoraFrete.services;

import com.wipro.calculadoraFrete.entities.EnderecoResponse;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConsultaEnderecoToFreteService {

    private Map<String, String> regiaoPorEstado = new HashMap<>();

    private Map<String, Double> fretePorRegiao = new HashMap<>();

    public ConsultaEnderecoToFreteService() {
        fretePorRegiao.put("Sudeste", 7.85);
        fretePorRegiao.put("Centro-Oeste", 12.50);
        fretePorRegiao.put("Nordeste", 15.98);
        fretePorRegiao.put("Sul", 17.30);
        fretePorRegiao.put("Norte", 20.83);

        regiaoPorEstado.put("SP", "Sudeste");
        regiaoPorEstado.put("RJ", "Sudeste");
        regiaoPorEstado.put("MG", "Sudeste");
        regiaoPorEstado.put("ES", "Sudeste");

        regiaoPorEstado.put("DF", "Centro-Oeste");
        regiaoPorEstado.put("GO", "Centro-Oeste");
        regiaoPorEstado.put("MT", "Centro-Oeste");
        regiaoPorEstado.put("MS", "Centro-Oeste");

        regiaoPorEstado.put("BA", "Nordeste");
        regiaoPorEstado.put("SE", "Nordeste");
        regiaoPorEstado.put("AL", "Nordeste");
        regiaoPorEstado.put("PE", "Nordeste");
        regiaoPorEstado.put("PB", "Nordeste");
        regiaoPorEstado.put("RN", "Nordeste");
        regiaoPorEstado.put("CE", "Nordeste");
        regiaoPorEstado.put("PI", "Nordeste");
        regiaoPorEstado.put("MA", "Nordeste");

        regiaoPorEstado.put("PR", "Sul");
        regiaoPorEstado.put("SC", "Sul");
        regiaoPorEstado.put("RS", "Sul");

        regiaoPorEstado.put("AM", "Norte");
        regiaoPorEstado.put("RR", "Norte");
        regiaoPorEstado.put("AP", "Norte");
        regiaoPorEstado.put("PA", "Norte");
        regiaoPorEstado.put("TO", "Norte");
        regiaoPorEstado.put("RO", "Norte");
        regiaoPorEstado.put("AC", "Norte");

    }

    public EnderecoResponse getdefinirFreteEndereco(ViaCepResponse viaCepResponse){

        String estado = viaCepResponse.getEstado();
        String regiao = regiaoPorEstado.get(estado);
        Double frete = definirValorDoFrete(regiao);

        return new EnderecoResponse(
                viaCepResponse.getCep(),
                viaCepResponse.getRua(),
                viaCepResponse.getComplemento(),
                viaCepResponse.getBairro(),
                viaCepResponse.getCidade(),
                viaCepResponse.getEstado(),
                frete
        );
    }

    public Double definirValorDoFrete(String regiao) {
        return fretePorRegiao.get(regiao);
    }
}
