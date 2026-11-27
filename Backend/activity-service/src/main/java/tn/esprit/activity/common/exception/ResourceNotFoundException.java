package tn.esprit.activity.common.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String id) {
        super(resource + " avec l'id " + id + " introuvable");
    }
}