package sabre.desafio2.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class PeopleDTO {
    private String dni;
    private String name;
    private String lastname;
    private String birthDate;
    @Email(message = "Por favor ingrese un e-mail válido")
    private String mail;
}
