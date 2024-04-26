package com.luiza.labs.service;

import com.luiza.labs.domain.FileImport;
import com.luiza.labs.domain.HashUtil;
import com.luiza.labs.domain.exception.FileException;
import com.luiza.labs.repository.FileImportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileImportServiceTest {

    @Mock
    private ItemService itemService;

    @Mock
    private FileImportRepository fileImportRepository;

    @InjectMocks
    private FileImportService fileImportService;

    @Test
    void processFileExceptionNullTest() throws IOException, FileException, NoSuchAlgorithmException {
        FileException exception = Assertions.assertThrows(FileException.class, () -> fileImportService.processFile(null));
        Assertions.assertTrue(exception.getMessage().equals("File is null or not is text/plain"));
    }

    @Test
    void processFileExceptionNotPlainTest() throws IOException, FileException, NoSuchAlgorithmException {
        String fileContent = "line 1\nline 2";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/NOT-plain", fileContent.getBytes());

        FileException exception = Assertions.assertThrows(FileException.class, () -> fileImportService.processFile(multipartFile));
        Assertions.assertTrue(exception.getMessage().equals("File is null or not is text/plain"));
    }

    @Test
    void processFileExceptionAlreadyImportedTest() throws IOException, FileException, NoSuchAlgorithmException {
        String fileContent = "line 1\nline 2";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent.getBytes());

        String hash = HashUtil.calculateHash(multipartFile);
        when(fileImportRepository.findByHash(hash)).thenReturn(new FileImport(hash));

        FileException exception = Assertions.assertThrows(FileException.class, () -> fileImportService.processFile(multipartFile));
        Assertions.assertTrue(exception.getMessage().equals("File already imported"));
    }

    @Test
    void processFileTest() throws IOException, FileException, NoSuchAlgorithmException {
        String fileContent = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308\nline 2";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", fileContent.getBytes());

        fileImportService.processFile(multipartFile);
    }
}
