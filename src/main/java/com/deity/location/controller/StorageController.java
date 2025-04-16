package com.deity.location.controller;

import com.deity.location.common.exceptions.CustomNotFoundException;
import com.deity.location.config.MD5Config;
import com.deity.location.service.GCPStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Storage", description = "operations for Storage")
public class StorageController {

    private final GCPStorageService gcpStorageService;
    private final MD5Config md5Config;

    public StorageController(GCPStorageService gcpStorageService, MD5Config md5Config) {
        this.gcpStorageService = gcpStorageService;
        this.md5Config = md5Config;
    }

    @Operation(
            summary = "Descargar y mostrar una imagen",
            description = "Este endpoint recibe una clave MD5 de la ruta del archivo y el nombre de la imagen, " +
                    "y si la clave es válida, devuelve la imagen desde Google Cloud Storage.",
            parameters = {
                    @Parameter(
                            name = "md5Key",
                            description = "Clave MD5 de la ruta del archivo",
                            required = true,
                            examples = @ExampleObject(value = "9a0364b9e99bb480dd25e1f0284c8555") // Ejemplo de clave MD5
                    ),
                    @Parameter(
                            name = "imageName",
                            description = "Nombre de la imagen en el bucket",
                            required = true,
                            examples = @ExampleObject(value = "sebas.jpeg")
                    )
            }
    )
    @GetMapping("/download/{md5Key}/{imageName}")
    public Mono<ResponseEntity<byte[]>> downloadImage(@PathVariable String md5Key,
                                                      @PathVariable String imageName,
                                                      @Parameter(description = "Tipo de ruta (B o U)", required = true)
                                                      @RequestParam("type") String typeMono) {

        if (!md5Config.isValidMd5(md5Key, imageName, typeMono)) {
            throw new CustomNotFoundException("Clave MD5 no válida para la imagen: " + imageName);
        }

        byte[] fileContent = gcpStorageService.downloadFile(imageName, typeMono);

        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
                        .body(fileContent)
        );
    }

    @Operation(
            summary = "Subir archivo al bucket de GCP Storage",
            description = "Permite subir un archivo al almacenamiento en Google Cloud Storage (Bucket).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Archivo subido exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Parámetros incompletos o incorrectos"),
                    @ApiResponse(responseCode = "500", description = "Error interno al subir el archivo")
            }
    )
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public Mono<ResponseEntity<String>> uploadFile(
            @Parameter(description = "Archivo a subir (formato binario)", required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")))
            @RequestPart("file") Mono<FilePart> filePartMono,

            @Parameter(description = "Nuevo nombre personalizado para el archivo", required = true)
            @RequestPart("customFileName") Mono<String> customFileNameMono,

            @Parameter(description = "Tipo de ruta (B o U)", required = true)
            @RequestPart("type") Mono<String> typeMono) {

        return Mono.zip(filePartMono, customFileNameMono, typeMono)
                .flatMap(tuple -> {
                    FilePart filePart = tuple.getT1();
                    String customFileName = tuple.getT2();
                    String type = tuple.getT3();

                    return gcpStorageService.uploadFile(filePart, customFileName, type);
                })
                .map(filename -> ResponseEntity.ok("Archivo subido exitosamente: " + filename))
                .switchIfEmpty(Mono.error(new CustomNotFoundException("Parámetros incompletos en la solicitud.")));
    }

}