package br.com.attornatus.integrations;

import br.com.attornatus.dtos.AddressRequestDTO;
import br.com.attornatus.dtos.AddressResponseDTO;
import br.com.attornatus.dtos.PersonRequestDTO;
import br.com.attornatus.dtos.PersonResponseDTO;
import br.com.attornatus.implementations.AddressService;
import br.com.attornatus.implementations.PersonService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
class AddressServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @Test
    @DirtiesContext
    void mustCreateAddress() {
        AddressRequestDTO addressRequestDTO = newAddressRequestDTO();
        AddressResponseDTO address = addressService.create(addressRequestDTO);

        assertEquals(1L, address.getId());
        assertEquals("88111-222", address.getCep());
        assertEquals("Tubarão", address.getCity());
        assertEquals("Casa A", address.getPublicArea());
        assertEquals("1234", address.getNumber());
        assertTrue(address.isMain());

    }

    @Test
    void mustThrowExceptionWhenPersonDoesntExistWhenCreateAddress(){
        AddressRequestDTO addressRequestDTO = newAddressRequestDTO();
        addressRequestDTO.setPersonId(99L);
        assertThrows(NoSuchElementException.class, () -> addressService.create(addressRequestDTO));
    }

    @Test
    void mustThrowExcepetionWhenCreateAddressIfCepLengthLessThanNine(){
        AddressRequestDTO addressRequestDTO = newAddressRequestDTO();
        addressRequestDTO.setCep("189-1");
        assertThrows(ConstraintViolationException.class, () -> addressService.create(addressRequestDTO));
    }

    @Test
    @DirtiesContext
    void mustUpdateMainOldAddressToFalseWhenCreateNewMainAddress(){
        AddressRequestDTO oldAddressRequestDTO = newAddressRequestDTO();
        addressService.create(oldAddressRequestDTO);

        AddressRequestDTO newAddressRequestDTO = AddressRequestDTO.builder()
                .cep("98765-123")
                .city("Laguna")
                .publicArea("Casa B")
                .number("9876")
                .main(true)
                .personId(oldAddressRequestDTO.getPersonId())
                .build();
       addressService.create(newAddressRequestDTO);

       AddressResponseDTO addressResponseDTO = addressService.findMainAddressByPersonId(oldAddressRequestDTO.getPersonId());

       assertEquals(2L, addressResponseDTO.getId());
       assertEquals("98765-123", addressResponseDTO.getCep());
       assertEquals("Laguna", addressResponseDTO.getCity());
       assertEquals("Casa B", addressResponseDTO.getPublicArea());
       assertEquals("9876", addressResponseDTO.getNumber());
       assertTrue(addressResponseDTO.isMain());
    }

    @Test
    @DirtiesContext
    void mustUpdateMainNewAddressToTrueWhenDoesntExistMainAddress(){
        AddressRequestDTO addressRequestDTO = newAddressRequestDTO();
        addressRequestDTO.setMain(false);
        AddressResponseDTO address = addressService.create(addressRequestDTO);

        assertEquals(1L, address.getId());
        assertEquals("88111-222", address.getCep());
        assertEquals("Tubarão", address.getCity());
        assertEquals("Casa A", address.getPublicArea());
        assertEquals("1234", address.getNumber());
        assertTrue(address.isMain());
    }

    @Test
    @DirtiesContext
    void mustListAddressByPersonId(){
        AddressRequestDTO addressDTO1 = newAddressRequestDTO();
        AddressRequestDTO addressDTO2 = AddressRequestDTO.builder()
                .cep("98765-123")
                .city("Laguna")
                .publicArea("Casa B")
                .number("9876")
                .main(true)
                .personId(addressDTO1.getPersonId())
                .build();
        addressService.create(addressDTO1);
        addressService.create(addressDTO2);
        List<AddressResponseDTO> listDTO = addressService.listAddressByPersonId(addressDTO1.getPersonId());

        assertEquals(2, listDTO.size());
        assertEquals("88111-222", listDTO.get(0).getCep());
        assertEquals("Tubarão", listDTO.get(0).getCity());
        assertFalse(listDTO.get(0).isMain());

        assertEquals("98765-123", listDTO.get(1).getCep());
        assertEquals("Laguna", listDTO.get(1).getCity());
        assertTrue(listDTO.get(1).isMain());
    }

    @Test
    void mustThrowExceptionWhenPersonIdDoesntExistsToListAddress(){
        assertThrows(NoSuchElementException.class, () -> addressService.listAddressByPersonId(99L));
    }

    @Test
    @DirtiesContext
    void mustReturnMainAddressByPersonId(){
        AddressRequestDTO addressDTO1 = newAddressRequestDTO();
        AddressRequestDTO addressDTO2 = AddressRequestDTO.builder()
                .cep("98765-123")
                .city("Laguna")
                .publicArea("Casa B")
                .number("9876")
                .main(false)
                .personId(addressDTO1.getPersonId())
                .build();
        addressService.create(addressDTO1);
        addressService.create(addressDTO2);
        AddressResponseDTO address = addressService.findMainAddressByPersonId(addressDTO1.getPersonId());
        assertEquals(1L, address.getId());
        assertEquals("88111-222", address.getCep());
        assertEquals("Tubarão", address.getCity());
        assertEquals("Casa A", address.getPublicArea());
        assertEquals("1234", address.getNumber());
        assertTrue(address.isMain());

    }

    @Test
    void mustThrowExceptionWhenPersonIdDoesntExistsToMainAddress(){
        assertThrows(NoSuchElementException.class, () -> addressService.listAddressByPersonId(99L));
    }

    private PersonResponseDTO createPerson(){
        PersonRequestDTO dto = PersonRequestDTO.builder().name("João").birthday(LocalDate.of(2000, 2, 2)).build();
        return personService.create(dto);
    }

    private AddressRequestDTO newAddressRequestDTO(){
        PersonResponseDTO personResponseDTO = createPerson();
        return AddressRequestDTO.builder()
                .cep("88111-222")
                .city("Tubarão")
                .publicArea("Casa A")
                .number("1234")
                .main(true)
                .personId(personResponseDTO.getId())
                .build();
    }
}
