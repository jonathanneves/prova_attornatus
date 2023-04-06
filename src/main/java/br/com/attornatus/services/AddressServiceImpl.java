package br.com.attornatus.services;

import br.com.attornatus.dtos.AddressRequestDTO;
import br.com.attornatus.dtos.AddressResponseDTO;
import br.com.attornatus.dtos.PersonResponseDTO;
import br.com.attornatus.models.Address;
import br.com.attornatus.models.Person;
import br.com.attornatus.repositories.AddressRepository;
import br.com.attornatus.repositories.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressServiceImpl implements br.com.attornatus.implementations.AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AddressResponseDTO create(AddressRequestDTO addressDTO) {
        read(addressDTO.getPersonId());
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setId(null);
        updateMainAddress(addressDTO.getPersonId(), addressDTO.isMain());
        address = addressRepository.save(address);
        return modelMapper.map(address, AddressResponseDTO.class);
    }

    @Override
    public List<AddressResponseDTO> listAddressByPersonId(Long personId) {
        read(personId);
        return addressRepository.findByPersonId(personId).stream().map(it -> modelMapper.map(it, AddressResponseDTO.class)).toList();
    }

    @Override
    public AddressResponseDTO findMainAddressByPersonId(Long personId) {
        read(personId);
        Address address = addressRepository.findByPersonIdAndMainIsTrue(personId);
        return modelMapper.map(address, AddressResponseDTO.class);
    }

    private Person read(Long id) {
        String message = String.format("Não foi encontrado pessoa com ID (%d)!", id);
        return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException(message));
    }

    private void updateMainAddress(Long personId, Boolean main) {
        if(Boolean.TRUE.equals(main)){
            Address oldAddress = addressRepository.findByPersonIdAndMainIsTrue(personId);
            if(oldAddress != null) {
                oldAddress.setMain(false);
                addressRepository.save(oldAddress);
            }
        }
    }
}
