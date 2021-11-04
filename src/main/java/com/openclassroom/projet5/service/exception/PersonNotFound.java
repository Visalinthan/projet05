package com.openclassroom.projet5.service.exception;

public class PersonNotFound extends Throwable {
    public PersonNotFound(Long id) {
        super("person with id " +id+ " is not found");
    }
}
