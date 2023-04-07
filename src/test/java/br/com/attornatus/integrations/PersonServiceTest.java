package br.com.attornatus.integrations;


import br.com.attornatus.dtos.PersonRequestDTO;
import br.com.attornatus.dtos.PersonResponseDTO;
import br.com.attornatus.implementations.PersonService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    void mustFindAllPersons() {
        PersonRequestDTO dto = PersonRequestDTO.builder().name("João").birthday(LocalDate.of(2000, 2, 2)).build();
        PersonRequestDTO dto2 =  PersonRequestDTO.builder().name("Maria").birthday(LocalDate.of(1999, 3, 10)).build();

        personService.create(dto);
        personService.create(dto2);

        List<PersonResponseDTO> persons = personService.findAll();

        assertEquals(2, persons.size());
        assertEquals("João", persons.get(0).getName());
        assertEquals("Maria", persons.get(1).getName());
        assertEquals("2000-02-02", persons.get(0).getBirthday().toString());
        assertEquals("1999-03-10", persons.get(1).getBirthday().toString());

    }

    @Test
    void mustFindPersonById() {
        PersonResponseDTO person = createPerson();
        PersonResponseDTO personFound = personService.findById(person.getId());

        assertEquals(person.getId(), personFound.getId());
        assertEquals("João", personFound.getName());
        assertEquals("2000-02-02", personFound.getBirthday().toString());
    }

    @Test
    void mustThrowExceptionWhenPersonByIdNotFound() {
        assertThrows(NoSuchElementException.class, () -> personService.findById(5L));
    }

    @Test
    void mustCreatePerson() {
        PersonResponseDTO result = createPerson();

        assertEquals(1L, result.getId());
        assertEquals("João", result.getName());
        assertEquals("2000-02-02", result.getBirthday().toString());
    }

    @Test
    void mustThrowExceptionIfNameIsEmptyWhenCreatePerson() {
        PersonRequestDTO dto = PersonRequestDTO.builder().birthday(LocalDate.of(2000, 1, 1)).build();
        assertThrows(ConstraintViolationException.class, () -> personService.create(dto));
    }

    @Test
    void mustUpdatePerson() {
        PersonResponseDTO person = createPerson();

        PersonRequestDTO dto = PersonRequestDTO.builder()
                .name("Maria")
                .birthday(LocalDate.of(1995, 10, 1))
                .build();

        PersonResponseDTO updatedPerson = personService.update(person.getId(), dto);

        assertEquals(person.getId(), updatedPerson.getId());
        assertEquals("Maria", updatedPerson.getName());
        assertEquals("1995-10-01", updatedPerson.getBirthday().toString());
    }

    @Test
    void mustThrowExceptionWhenUpdatePersonWhoDoesntExist() {
        PersonRequestDTO dto = PersonRequestDTO.builder().name("Lucas").birthday(LocalDate.of(1999, 2, 10)).build();
        assertThrows(NoSuchElementException.class, () -> personService.update(5L, dto));
    }

    @Test
    void mustDeletePerson(){
        PersonResponseDTO person = createPerson();
        personService.deleteById(person.getId());
        assertThrowsExactly(NoSuchElementException.class, () ->personService.findById(person.getId()));
    }

    private PersonResponseDTO createPerson(){
        PersonRequestDTO dto = PersonRequestDTO.builder()
                .name("João")
                .birthday(LocalDate.of(2000, 2, 2))
                .build();
        return personService.create(dto);
    }

}
