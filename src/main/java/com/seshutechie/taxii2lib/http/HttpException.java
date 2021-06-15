package com.seshutechie.taxii2lib.http;

public class HttpException extends Exception {
    public HttpException(String message) {
        super(message);
    }

    public HttpException(Exception exception) {
        super(exception);
    }
}
