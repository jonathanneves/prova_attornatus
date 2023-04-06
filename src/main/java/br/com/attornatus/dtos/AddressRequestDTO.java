package br.com.attornatus.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {

    @NotBlank(message = "Logradouro não pode ser vazio ou nulo.")
    private String publicArea;

    @Size(min = 8, max = 8, message = "CEP deve ter tamanho igual à 8.")
    private String cep;

    @Size(min = 2, max = 10, message = "Número deve ter tamanho máximo 10.")
    private String number;

    @NotBlank(message = "Cidade não pode ser vazio ou nulo.")
    private String city;

    private boolean main;

    @NotNull(message = "Id da Pessoa não pode ser nulo.")
    private Long personId;

}
