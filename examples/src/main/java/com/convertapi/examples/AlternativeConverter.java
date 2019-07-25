package com.convertapi.examples;

import io.github.kmozsi.convertapi.Config;
import io.github.kmozsi.convertapi.ConversionResult;
import io.github.kmozsi.convertapi.ConvertApi;
import io.github.kmozsi.convertapi.Param;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.System.getenv;

/**
 * Example of saving Word docx to PDF using alternative OpenOffice converter
 * Conversion is made by using same file parameter and processing two conversions simultaneously
 * https://www.convertapi.com/docx-to-pdf
 * https://www.convertapi.com/docx-to-png
 */
public class AlternativeConverter {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Config.setDefaultSecret(getenv("CONVERTAPI_SECRET"));    //Get your secret at https://www.convertapi.com/a
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));

        System.out.println("Converting DOCX to PDF with OpenOffice converter");
        Param docxFileParam = new Param("file", new File(AlternativeConverter.class.getClassLoader().getResource("test.docx").getFile()).toPath());
        Param converterParam = new Param("converter", "openofficetopdf");

        CompletableFuture<ConversionResult> pdfResult = ConvertApi.convert("docx", "pdf", docxFileParam, converterParam);

        System.out.println("PDF file saved to: " + pdfResult.get().saveFile(tempDir).get());
    }
}
