package com.convertapi.examples;

import io.github.kmozsi.convertapi.Config;
import io.github.kmozsi.convertapi.ConvertApi;

import java.io.File;
import java.io.IOException;
import static java.lang.System.getenv;

/**
 * Most simple conversion example
 */
public class SimpleConversion {
    public static void main(String[] args) throws IOException {
        Config.setDefaultSecret(getenv("CONVERTAPI_SECRET"));
        String resourcePath = new File(AlternativeConverter.class.getClassLoader().getResource("test.docx").getFile()).getCanonicalPath();
        String tmpDir = System.getProperty("java.io.tmpdir");

        // Simplified file to file conversion example
        ConvertApi.convertFile(resourcePath, tmpDir + "result.pdf");

        // Simplified file to multiple files conversion example
        ConvertApi.convertFileToDir(resourcePath, "jpg", tmpDir);

        // Simplified web site to pdf conversion example
        ConvertApi.convertUrl("http://example.com", tmpDir + "example.pdf");

        // Simplified remote file to local file conversion example
        ConvertApi.convertRemoteFile("https://cdn.convertapi.com/cara/testfiles/document.docx", tmpDir + "demo.pdf");

        // Simplified remote file to local file conversion example
        ConvertApi.convertRemoteFileToDir("https://cdn.convertapi.com/cara/testfiles/document.docx", "jpg", tmpDir);
    }
}
