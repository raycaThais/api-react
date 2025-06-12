package org.serratec.h2.grupo2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409
public class ContaInativaException extends RuntimeException {
    public ContaInativaException(String message) {
        super(message);
    }
}
