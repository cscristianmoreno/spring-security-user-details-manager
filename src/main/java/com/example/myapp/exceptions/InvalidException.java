package com.example.myapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseBody
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Ocurrió un error")
public class InvalidException extends Exception {
    
    public InvalidException(String message) {
        super(message);
    }
}
