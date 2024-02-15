package com.codewithProjects.ecom.exceptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message)
    {
        super(message);
    }
}
