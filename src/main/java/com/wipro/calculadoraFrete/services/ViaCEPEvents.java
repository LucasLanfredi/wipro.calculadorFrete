package com.wipro.calculadoraFrete.services;

public interface ViaCEPEvents {

    void onCEPSuccess(BuscarCep cep);

    void onCEPError(String cep);
}

