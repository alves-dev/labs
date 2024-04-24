package com.luiza.labs.controller;

import com.luiza.labs.domain.exception.FileException;
import com.luiza.labs.service.FileImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("api/v1/file")
public class FileController {

    private final FileImportService fileImportService;

    public FileController(FileImportService fileImportService) {
        this.fileImportService = fileImportService;
    }

    @Operation(summary = "Recebe um arquivo .txt",
            description = "Cada linha do arquivo deve representar um produto de um pedido valido",
            tags = {"File"},
            responses = {
                    @ApiResponse(responseCode = "202", description = "Arquivo recebido"),
            })
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) throws FileException, IOException, NoSuchAlgorithmException {
        //TODO: só dar um 202 e ler o arquivo posteriormente
        fileImportService.processFile(file);
        return ResponseEntity.accepted().build();
    }
}
