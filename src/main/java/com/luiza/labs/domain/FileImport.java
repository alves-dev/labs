package com.luiza.labs.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Este objeto representa um arquivo importado.
 */
@Document(collection = "file_import")
public class FileImport {

    private UUID id;
    private String hash;
    private LocalDate date;

    public FileImport(String hash) {
        this.id = UUID.randomUUID();
        this.hash = hash;
        this.date = LocalDate.now();
    }

    protected FileImport() {
    }
}
