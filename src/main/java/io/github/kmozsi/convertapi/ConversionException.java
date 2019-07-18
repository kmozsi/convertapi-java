package io.github.kmozsi.convertapi;

public class ConversionException extends RuntimeException {
    private final int httpStatusCode;

    public ConversionException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}