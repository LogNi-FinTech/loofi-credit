package com.logni.credit.service.utilis.constants;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtil {
	   public static String saveFile(String uploadFolder, MultipartFile multipartFile) {
	      try {
	         String fileName = UUID.randomUUID().toString();
	         byte[] bytes = multipartFile.getBytes();
	         Path path = Paths.get(uploadFolder, fileName);
	         Files.write(path, bytes);
	         return fileName;
	      } catch (Exception e) {
	         throw new RuntimeException(e);
	      }
	   }
	}