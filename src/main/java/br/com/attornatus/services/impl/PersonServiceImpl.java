package br.com.attornatus.services.impl;

import br.com.attornatus.dtos.PersonRequestDTO;
import br.com.attornatus.dtos.PersonResponseDTO;
import br.com.attornatus.models.Person;
import br.com.attornatus.repositories.PersonRepository;
import br.com.attornatus.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PersonResponseDTO create(PersonRequestDTO personRequestDTO) {
        Person person = modelMapper.map(personRequestDTO, Person.class);
        person = personRepository.save(person);
        return modelMapper.map(person, PersonResponseDTO.class);
    }

    @Override
    public PersonResponseDTO update(Long id, PersonRequestDTO personRequestDTO) {
        Person person = read(id);
        person.setName(personRequestDTO.getName());
        person.setBirthday(personRequestDTO.getBirthday());
        person = personRepository.save(person);
        return modelMapper.map(person, PersonResponseDTO.class);
    }

    @Override
    public PersonResponseDTO deleteById(Long id) {
        PersonResponseDTO person = findById(id);
        personRepository.deleteById(id);
        return modelMapper.map(person, PersonResponseDTO.class);
    }

    @Override
    public List<PersonResponseDTO> findAll() {
        return personRepository.findAll().stream().map(it -> modelMapper.map(it, PersonResponseDTO.class)).toList();
    }

    @Override
    public PersonResponseDTO findById(Long id) {
        return modelMapper.map(read(id), PersonResponseDTO.class);
    }

    private Person read(Long id) {
        String message = String.format("Não foi encontrado pessoa com ID (%d)!", id);
        return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException(message));
    }
}
