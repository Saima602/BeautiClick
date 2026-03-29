package com.beuticlick.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String resource, Long id, Long salonId) {
        super(resource + " " + id + " does not belong to salon " + salonId);
    }
}
