package com.wipro.calculadoraFrete.services;

public interface ViaCEPEvents {

    public void onCEPSuccess(BuscarCep cep);

    public void onCEPError(String cep);
}

