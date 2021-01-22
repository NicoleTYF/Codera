package com.codeEra.code_era.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A exception called when {@link com.codeEra.code_era.controller.BugController} or 
 * {@link com.codeEra.code_era.controller.FixController} Update and Delete APIs 
 * cannot find objects with the input ID parameters. 
 */
@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	/** Empty Constructor */
    public ResourceNotFoundException() {
        super();
    }

    /** 
     * Constructor to customise the throwback message 
     * @param message the content of the error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /** 
     * Constructor to customise the throwback message and 
     * the exact function that causes the error. 
     * @param message the content of the error message 
     * @param cause the function that causes this error 
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}