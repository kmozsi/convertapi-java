package io.github.kmozsi.convertapi.examples;

import io.github.kmozsi.convertapi.ConvertApi;

import static java.lang.System.getenv;

/**
 * Most simple conversion example
 */

public class SimpleConversion {
    public static void main(String[] args) {
        ConvertApi.convert("test-files/test.docx", "result.pdf", getenv("CONVERTAPI_SECRET"));
    }
}