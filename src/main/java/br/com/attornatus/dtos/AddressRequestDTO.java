package br.com.attornatus.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {

    @NotBlank(message = "Logradouro não pode ser vazio ou nulo.")
    private String publicArea;

    @Size(min = 9, max = 9, message = "CEP deve ter tamanho igual à 9.")
    private String cep;

    @Size(min = 2, max = 10, message = "Número deve ter tamanho máximo 10.")
    private String number;

    @NotBlank(message = "Cidade não pode ser vazio ou nulo.")
    private String city;

    private boolean main = true;

    @NotNull(message = "Id da Pessoa não pode ser nulo.")
    private Long personId;

}
