package com.esprit.eventservice.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String entite, Long id) {
        super(entite + " introuvable : " + id);
    }
}