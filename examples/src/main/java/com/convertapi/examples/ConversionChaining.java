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
 * Short example of conversions chaining, the PDF pages extracted and saved as separated JPGs and then ZIP'ed
 * https://www.convertapi.com/doc/chaining
 */

public class ConversionChaining {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Config.setDefaultSecret(getenv("CONVERTAPI_SECRET"));    //Get your secret at https://www.convertapi.com/a

        System.out.println("Converting PDF to JPG and compressing result files with ZIP");
        CompletableFuture<ConversionResult> jpgResult = ConvertApi.convert("docx", "jpg", new Param("file", new File(AlternativeConverter.class.getClassLoader().getResource("test.docx").getFile()).toPath()));
        System.out.println("ConvertApi.convert is not blocking method, proceeding to ZIP conversion");

        CompletableFuture<ConversionResult> zipResult = ConvertApi.convert("jpg", "zip", new Param("files", jpgResult));

        System.out.println("Saving result file (blocking operation)");
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
        CompletableFuture<Path> path = zipResult.get().saveFile(tempDir);

        System.out.println("DOCX -> JPG conversion cost: " + jpgResult.get().conversionCost());
        System.out.println("DOCX -> JPG conversion result file count: " + jpgResult.get().fileCount());
        System.out.println("JPG -> ZIP conversion cost: " + zipResult.get().conversionCost());
        System.out.println("ZIP file saved to: " + path.get().toString());
    }
}