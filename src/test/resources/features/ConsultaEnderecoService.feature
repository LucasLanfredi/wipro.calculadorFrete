Feature: ConsultaEnderecoService
  Scenario: Consulta de endereço com sucesso
    Given um CEP válido "12345-678"
    When o serviço de consulta de endereço é chamado
    Then o endereço é retornado corretamente com o frete calculado