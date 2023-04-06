package br.com.attornatus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "public_area")
    private String publicArea;

    @NotNull
    @Size(min = 8, max = 8)
    private String cep;

    @NotNull
    @Size(min = 2, max = 10)
    private String number;

    @NotBlank
    private String city;

    @NotNull
    private Boolean main;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id", nullable=false)
    private Person person;
}
