package sabre.desafio2.models.dtos.Shared;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class StatusDTO {
    @NotNull(message = "The status code must be specified")
    private int code;
    @NotEmpty(message = "The status message must be specified")
    private String message;
}
