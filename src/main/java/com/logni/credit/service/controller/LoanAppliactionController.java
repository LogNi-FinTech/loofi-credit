package com.logni.credit.service.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.logni.credit.service.model.LoanApplication;
import com.logni.credit.service.repository.LoanApplicationRepository;
import com.logni.credit.service.service.FileStorageService;
import com.logni.credit.service.service.LoanAppliactionService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/api/application")
@Slf4j
public class LoanAppliactionController {

   @Autowired
   private LoanAppliactionService loanAppliactionService;

   @Autowired
   private LoanApplicationRepository loanApplicationRepository;

   @Autowired
   private FileStorageService fileStorageService;

   @PostMapping
   ResponseEntity<LoanApplication> requestForLoan(@RequestParam("application-object") String loanApplicationObject,
                                                  @RequestParam(value = "document", required = false) MultipartFile[]  loanDocument
         ) throws JsonParseException, JsonMappingException, IOException {
      LoanApplication loanApplication = loanAppliactionService.requestForLoan(loanApplicationObject, loanDocument);
      if (loanApplication != null) {
         return ResponseEntity.status(HttpStatus.CREATED).body(loanApplication);
      } else {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      }
   }

   @GetMapping
   public ResponseEntity<List<LoanApplication>> fetchAllLoanApplication() {
      return ResponseEntity.ok().body(loanApplicationRepository.findAll());
   }

   @GetMapping("/document/{fileName}")
   public ResponseEntity getEventImage(@PathVariable("fileName") String fileName, HttpServletRequest request) {
      Resource resource = fileStorageService.loadFileAsResource(fileName);
      String contentType = null;
      try {
         contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
      } catch (IOException ex) {
         log.info(ex.toString());
      }
      if (contentType == null) {
         contentType = "application/octet-stream";
      }
      return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
            .body(resource);
   }

}
