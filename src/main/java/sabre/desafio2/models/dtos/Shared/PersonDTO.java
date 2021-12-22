package sabre.desafio2.models.dtos.Shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PersonDTO {
    @NotEmpty(message = "The DNI must be specified")
    private String dni;
    @NotEmpty(message = "The name must be specified")
    private String name;
    @NotEmpty(message = "The last name must be specified")
    private String lastname;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @NotNull(message = "The birth date must be specified")
    private Date birthDate;
    @NotEmpty(message = "The email address must be specified")
    @Email(message = "The specified email address must be valid")
    private String mail;
}
