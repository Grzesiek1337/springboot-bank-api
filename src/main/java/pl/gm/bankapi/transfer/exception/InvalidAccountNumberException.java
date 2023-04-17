package pl.gm.bankapi.transfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidAccountNumberException extends RuntimeException {
    public InvalidAccountNumberException(String message) {
        super(message);
    }
}
