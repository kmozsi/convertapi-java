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
 * Example of extracting first page from PDF and then chaining conversion PDF page to JPG.
 * https://www.convertapi.com/pdf-to-extract
 * https://www.convertapi.com/pdf-to-jpg
 */

public class CreatePdfThumbnail {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
         Config.setDefaultSecret(getenv("CONVERTAPI_SECRET"));    //Get your secret at https://www.convertapi.com/a
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));

        System.out.println("Creating PDF thumbnail");

        CompletableFuture<ConversionResult> pdfFirstPageResult = ConvertApi.convert("pdf", "extract",
                new Param("file",  new File(AlternativeConverter.class.getClassLoader().getResource("test.pdf").getFile()).toPath()),
                new Param("pagerange", "1")
        );

        CompletableFuture<ConversionResult> thumbnailResult = ConvertApi.convert("pdf", "jpg",
                new Param("file", pdfFirstPageResult),
                new Param("scaleimage", "true"),
                new Param("scaleproportions", "true"),
                new Param("imageheight", 300)
        );

        System.out.println("JPG thumbnail file saved to: " + thumbnailResult.get().saveFile(tempDir).get());
    }
}