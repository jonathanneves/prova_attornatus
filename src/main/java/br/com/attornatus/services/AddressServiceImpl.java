package br.com.attornatus.services;

import br.com.attornatus.dtos.AddressRequestDTO;
import br.com.attornatus.dtos.AddressResponseDTO;
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
        Person person = findPersonById(addressDTO.getPersonId());
        Address address = updateMainAddress(addressDTO);
        address.setPerson(person);
        address = addressRepository.save(address);
        return modelMapper.map(address, AddressResponseDTO.class);
    }

    @Override
    public List<AddressResponseDTO> listAddressByPersonId(Long personId) {
        findPersonById(personId);
        return addressRepository.findByPersonId(personId).stream().map(it -> modelMapper.map(it, AddressResponseDTO.class)).toList();
    }

    @Override
    public AddressResponseDTO findMainAddressByPersonId(Long personId) {
        findPersonById(personId);
        Address address = addressRepository.findByPersonIdAndMainIsTrue(personId);
        return modelMapper.map(address, AddressResponseDTO.class);
    }

    private Person findPersonById(Long id) {
        String message = String.format("Não foi encontrado pessoa com ID (%d)!", id);
        return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException(message));
    }

    private Address updateMainAddress(AddressRequestDTO addressDTO) {
        Address oldAddress = addressRepository.findByPersonIdAndMainIsTrue(addressDTO.getPersonId());
        if(Boolean.TRUE.equals(addressDTO.isMain())) {
            if(oldAddress != null){
                oldAddress.setMain(false);
                addressRepository.save(oldAddress);
            }
        }else{
            if(oldAddress == null){
                addressDTO.setMain(true);
            }
        }
        return modelMapper.map(addressDTO, Address.class);
    }
}
