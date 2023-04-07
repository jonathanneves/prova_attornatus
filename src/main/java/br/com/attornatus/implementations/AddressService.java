package br.com.attornatus.implementations;

import br.com.attornatus.dtos.AddressRequestDTO;
import br.com.attornatus.dtos.AddressResponseDTO;

import java.util.List;

public interface AddressService {

    AddressResponseDTO create(AddressRequestDTO addressDTO);

    List<AddressResponseDTO> listAddressByPersonId(Long personId);

    AddressResponseDTO findMainAddressByPersonId(Long personId);

}
