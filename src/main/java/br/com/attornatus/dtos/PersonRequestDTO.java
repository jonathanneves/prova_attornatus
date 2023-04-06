package br.com.attornatus.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    @Size(min = 3, max = 100, message = "Nome deve ter tamanho mínimo 3 e máximo 100.")
    private String name;

    @NotNull(message = "Data de nascimento não pode ser nulo.")
    @Past(message = "Data de nascimento deve ser uma data antiga.")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

}
