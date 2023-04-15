Feature: ConsultaEnderecoService
  Scenario: Consulta de endereço com sucesso
    Given um CEP válido "02022110"
    When o serviço de consulta de endereço é chamado
    Then o serviço retorna um objeto EnderecoResponse com os dados do CEP e frete

  Scenario: Consulta de endereço inválido
    Given um CEP invalido "00000000"
    When o serviço de consulta de endereço é chamado
    Then o serviço lança a exceção CepNotFoundException caso Cep esteja incorreto