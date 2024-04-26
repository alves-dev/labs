package com.luiza.labs.service;

import com.luiza.labs.domain.FileImport;
import com.luiza.labs.domain.HashUtil;
import com.luiza.labs.domain.InputLine;
import com.luiza.labs.domain.Item;
import com.luiza.labs.domain.exception.FileException;
import com.luiza.labs.domain.exception.InputException;
import com.luiza.labs.repository.FileImportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileImportService {

    private final Logger log = LoggerFactory.getLogger(FileImportService.class);

    private final ItemService itemService;
    private final FileImportRepository fileImportRepository;

    public FileImportService(ItemService itemService, FileImportRepository fileImportRepository) {
        this.itemService = itemService;
        this.fileImportRepository = fileImportRepository;
    }

    /**
     * Processa o arquivo e salva os dados no banco de dados
     *
     * @param file
     * @throws FileException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public void processFile(MultipartFile file) throws FileException, IOException, NoSuchAlgorithmException {
        String hash = validFile(file);
        FileImport fileImport = new FileImport(hash);
        fileImportRepository.save(fileImport);

        List<String> listString = readFile(file);

        List<Item> inputLineList = new ArrayList<>();
        int success = 0;
        int fail = 0;
        for (String line : listString) {
            try {
                inputLineList.add(InputLine.parser(line));
                success++;
            } catch (InputException e) {
                fail++;
                log.warn(e.getMessage());
            }
        }
        log.info("Lines read: Success: {} Fail: {}", success, fail);

        itemService.saveAll(inputLineList);
    }

    /**
     * Valida se o arquivo é text/plain e não foi importado anteriormente
     *
     * @param file
     * @return Hash do arquivo
     * @throws FileException            Cado o arquivo seja não seja text/plain ou já tenha sido importado antes
     * @throws IOException              Caso não seja possível ler o arquivo
     * @throws NoSuchAlgorithmException
     */
    private String validFile(MultipartFile file) throws FileException, IOException, NoSuchAlgorithmException {
        if (file == null || !Objects.equals(file.getContentType(), "text/plain")) {
            throw new FileException("File is null or not is text/plain");
        }

        String hash = HashUtil.calculateHash(file);
        var fileImport = fileImportRepository.findByHash(hash);

        if (fileImport != null) {
            throw new FileException("File already imported");
        }
        return hash;
    }

    /**
     * Retorna uma lista com os dados do arquivo
     *
     * @param file Um MultipartFile
     * @return Uma lista de String onde cada item é uma linha do arquivo
     * @throws FileException Caso não seja possível ler o arquivo
     */
    private List<String> readFile(MultipartFile file) throws FileException {
        List<String> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            throw new FileException("Unable to read file");
        }

        return result;
    }
}
