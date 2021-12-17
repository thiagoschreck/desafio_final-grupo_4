package sabre.desafio2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class People {
    private String dni;
    private String name;
    private String lastname;
    private String birthDate;
    private String mail;
}
