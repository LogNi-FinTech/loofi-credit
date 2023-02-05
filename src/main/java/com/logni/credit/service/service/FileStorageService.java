package com.logni.credit.service.service;

import com.logni.credit.service.exceptions.LoofiRunTimeException;
import com.logni.credit.service.utilis.constants.CreditErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileStorageService {
    String uploadFolder = "C:\\File\\";

    @Autowired
    public FileStorageService(@Value("${fileStore.basePath}") String uploadFolder) {
        // this.fileStorageLocation = Paths.get(uploadFolder).toAbsolutePath().normalize();

        try {
            // Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new LoofiRunTimeException(
                    CreditErrors.getErrorCode(CreditErrors.LOGNI_CREDIT, CreditErrors.FAILED_TO_CREATE_DIRECTORY),
                    CreditErrors.ERROR_MAP.get(CreditErrors.FAILED_TO_CREATE_DIRECTORY));
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(uploadFolder, fileName);
            log.info(filePath.toString());
            log.info(filePath.toUri().toString());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new LoofiRunTimeException(
                        CreditErrors.getErrorCode(CreditErrors.LOGNI_CREDIT, CreditErrors.FILE_NOT_FOUND),
                        CreditErrors.ERROR_MAP.get(CreditErrors.FILE_NOT_FOUND));
            }
        } catch (MalformedURLException ex) {
            throw new LoofiRunTimeException(
                    CreditErrors.getErrorCode(CreditErrors.LOGNI_CREDIT, CreditErrors.FILE_NOT_FOUND),
                    CreditErrors.ERROR_MAP.get(CreditErrors.FILE_NOT_FOUND));
        }
    }
}