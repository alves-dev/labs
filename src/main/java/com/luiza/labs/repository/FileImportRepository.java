package com.luiza.labs.repository;

import com.luiza.labs.domain.FileImport;
import com.luiza.labs.domain.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileImportRepository extends MongoRepository<FileImport, String> {

   FileImport findByHash(String hash);
}