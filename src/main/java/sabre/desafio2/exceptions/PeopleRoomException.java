package sabre.desafio2.exceptions;

public class PeopleRoomException extends Exception {
    public final String ERROR = "El tipo de habitación seleccionada no coincide con la cantidad de personas que se alojarán en ella.";

    public PeopleRoomException() {
        super();
    }

    public String getERROR() {
        return ERROR;
    }
}
