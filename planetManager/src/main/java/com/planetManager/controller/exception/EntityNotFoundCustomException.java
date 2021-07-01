package com.planetManager.controller.exception;

public class EntityNotFoundCustomException extends RuntimeException {

    public EntityNotFoundCustomException(Long id, String className) {
        super(className + " with id: " + id + " does not exist!");
    }
}
