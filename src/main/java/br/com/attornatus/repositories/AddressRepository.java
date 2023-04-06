package br.com.attornatus.repositories;

import br.com.attornatus.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByPersonId(Long id);

    Address findByPersonIdAndMainIsTrue(Long id);
}
