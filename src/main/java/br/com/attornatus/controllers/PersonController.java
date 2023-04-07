package br.com.attornatus.controllers;

import br.com.attornatus.dtos.PersonRequestDTO;
import br.com.attornatus.dtos.PersonResponseDTO;
import br.com.attornatus.implementations.PersonService;
import br.com.attornatus.responses.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping
    public ResponseEntity<PersonResponseDTO> create(@Valid @RequestBody PersonRequestDTO pessoaDTO) {
        PersonResponseDTO person = personService.create(pessoaDTO);
        return new SuccessResponse<PersonResponseDTO>().handle(person, this.getClass(), request, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PersonRequestDTO pessoaDTO) {
        PersonResponseDTO person = personService.update(id, pessoaDTO);
        return new SuccessResponse<PersonResponseDTO>().handle(person, this.getClass(), request, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> delete(@PathVariable Long id) {
        PersonResponseDTO person = personService.deleteById(id);
        return new SuccessResponse<PersonResponseDTO>().handle(person, this.getClass(), request, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> findById(@PathVariable Long id) {
        PersonResponseDTO person = personService.findById(id);
        return new SuccessResponse<PersonResponseDTO>().handle(person, this.getClass(), request, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PersonResponseDTO>> findAll() {
        List<PersonResponseDTO> personList = personService.findAll();
        return new SuccessResponse<List<PersonResponseDTO>>().handle(personList, this.getClass(), request, HttpStatus.OK);
    }

}
