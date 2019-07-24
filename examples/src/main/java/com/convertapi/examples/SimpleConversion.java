package com.convertapi.examples;

import com.convertapi.client.Config;
import com.convertapi.client.ConvertApi;
import java.io.File;

import static java.lang.System.getenv;

/**
 * Most simple conversion example
 */

public class SimpleConversion {
    public static void main(String[] args) {
        ConvertApi.convert(new File(AlternativeConverter.class.getClassLoader().getResource("test.docx").getFile()).getPath(), "result.pdf", getenv("CONVERTAPI_SECRET"));
    }
}
