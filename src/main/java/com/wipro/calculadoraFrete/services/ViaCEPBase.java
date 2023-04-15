package com.wipro.calculadoraFrete.services;

import com.wipro.calculadoraFrete.Exception.CepNotFoundException;
import com.wipro.calculadoraFrete.entities.ViaCepResponse;

public abstract class ViaCEPBase {

        protected String currentCEP;

        protected ViaCEPEvents Events;

        // métodos abstratos
        public abstract ViaCepResponse buscar(String cep) throws CepNotFoundException;

    }
