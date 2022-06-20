package com.logni.credit.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logni.credit.service.exceptions.CommonException;
import com.logni.credit.service.model.DocumentFile;
import com.logni.credit.service.model.LoanApplication;
import com.logni.credit.service.model.LoanDocument;
import com.logni.credit.service.model.LoanProduct;
import com.logni.credit.service.model.enumaration.Status;
import com.logni.credit.service.repository.DocumentFileRepository;
import com.logni.credit.service.repository.LoanApplicationRepository;
import com.logni.credit.service.repository.LoanDocumentRepository;
import com.logni.credit.service.repository.LoanProductRepository;
import com.logni.credit.service.service.dto.LoanApplicationRequest;
import com.logni.credit.service.utilis.constants.CreditErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
public class LoanAppliactionService {
    @Autowired
    private LoanProductRepository loanProductRepository;
    @Autowired
    private LoanDocumentRepository loanDocumentRepository;
    @Autowired
    private DocumentFileRepository documentFileRepository;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    String fileName, uploadFolder = "C:\\File\\";

    public LoanApplication requestForLoan(String loanApplicationObject, MultipartFile[] loanDocuments) throws JsonMappingException, JsonProcessingException {
        LoanApplicationRequest loanApplicationRequest = new ObjectMapper().readValue(loanApplicationObject, LoanApplicationRequest.class);
        validate(loanApplicationRequest);
        LoanDocument loanDocument = createLoanDocument(loanApplicationRequest, loanDocuments);

        LoanApplication loanApplication = LoanApplication.builder().
                description(loanApplicationRequest.getDescription()).
                loanAmount(loanApplicationRequest.getLoanAmount()).
                loanDocument(loanDocument).
                name(loanApplicationRequest.getName()).
                period(loanApplicationRequest.getPeriod()).
                status(Status.DEFAULT).
                userId(loanApplicationRequest.getUserId()).build();
        return loanApplicationRepository.save(loanApplication);
    }

    private LoanDocument createLoanDocument(LoanApplicationRequest loanApplicationObject, MultipartFile[] loanDocuments) {
        LoanDocument loanDocumentSaved, loanDocument;
        loanDocument = LoanDocument.builder().
                name(loanApplicationObject.getName()).description(loanApplicationObject.getDescription()).build();
        loanDocumentSaved = loanDocumentRepository.save(loanDocument);

        for (MultipartFile loanDocumentFile : loanDocuments) {
            fileName = FileUtil.saveFile(uploadFolder, loanDocumentFile);
            //fileName = "111";
            DocumentFile documentFile = DocumentFile.builder().
                    fileName(fileName).
                    fileUrl("url").
                    loanDocument(loanDocumentSaved).build();
            documentFileRepository.save(documentFile);
        }
        return loanDocumentSaved;
    }

    private void validate(LoanApplicationRequest loanApplicationRequest) {
        Optional<LoanProduct> loanProduct = loanProductRepository.findById(loanApplicationRequest.getLoanProductId());
        if (loanProduct.isPresent()) {
            if (!(loanProduct.get().getMinAmount().compareTo(loanApplicationRequest.getLoanAmount()) <= 0 &&
                    loanProduct.get().getMaxAmount().compareTo(loanApplicationRequest.getLoanAmount()) >= 0 &&
                    loanProduct.get().getMinPeriod() <= loanApplicationRequest.getPeriod() &&
                    loanProduct.get().getMaxPeriod() >= loanApplicationRequest.getPeriod())) {
                throw new CommonException(
                        CreditErrors.getErrorCode(CreditErrors.LOGNI_CREDIT_SERVICE, CreditErrors.LOAN_APPLICATION_REQUEST_NOT_VALID),
                        CreditErrors.ERROR_MAP.get(CreditErrors.LOAN_APPLICATION_REQUEST_NOT_VALID));
            }
        } else
            throw new CommonException(
                    CreditErrors.getErrorCode(CreditErrors.LOGNI_CREDIT_SERVICE, CreditErrors.LOAN_PRODUCT_NOT_FOUND),
                    CreditErrors.ERROR_MAP.get(CreditErrors.LOAN_PRODUCT_NOT_FOUND));
    }
}
