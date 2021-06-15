package com.seshutechie.taxii2lib;

/**
 * Application exception. This is thrown by library in all error conditions.
 */
public class TaxiiAppException extends Exception {
    public TaxiiAppException(String message) {
        super(message);
    }

    public TaxiiAppException(Exception exception) {
        super(exception);
    }
}
