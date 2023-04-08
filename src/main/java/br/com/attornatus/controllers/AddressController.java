package br.com.attornatus.controllers;

import br.com.attornatus.dtos.AddressRequestDTO;
import br.com.attornatus.dtos.AddressResponseDTO;
import br.com.attornatus.services.AddressService;
import br.com.attornatus.responses.SuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> create(@Valid @RequestBody AddressRequestDTO addressDTO) {
        AddressResponseDTO address = addressService.create(addressDTO);
        return new SuccessResponse<AddressResponseDTO>().handle(address, this.getClass(), request, HttpStatus.CREATED);
    }

    @GetMapping("/pessoa/{personId}")
    public ResponseEntity<List<AddressResponseDTO>> findAllByPersonId(@PathVariable Long personId) {
        List<AddressResponseDTO> addressList = addressService.listAddressByPersonId(personId);
        return new SuccessResponse<List<AddressResponseDTO>>().handle(addressList, this.getClass(), request, HttpStatus.OK);
    }

    @GetMapping("/main/pessoa/{personId}")
    public ResponseEntity<AddressResponseDTO> findMainAddressByPersonId(@PathVariable Long personId) {
        AddressResponseDTO address = addressService.findMainAddressByPersonId(personId);
        return new SuccessResponse<AddressResponseDTO>().handle(address, this.getClass(), request, HttpStatus.OK);
    }

}
