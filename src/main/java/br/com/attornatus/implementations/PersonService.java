package br.com.attornatus.implementations;

import br.com.attornatus.dtos.PersonRequestDTO;
import br.com.attornatus.dtos.PersonResponseDTO;

import java.util.List;

public interface PersonService {

    PersonResponseDTO create(PersonRequestDTO personRequestDTO);
    PersonResponseDTO update(Long id, PersonRequestDTO personRequestDTO);
    PersonResponseDTO deleteById(Long id);
    List<PersonResponseDTO> findAll();
    PersonResponseDTO findById(Long id);
}
