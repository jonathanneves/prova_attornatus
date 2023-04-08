package br.com.attornatus.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {

    private Long id;
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    private List<AddressResponseDTO> addresses;
}
