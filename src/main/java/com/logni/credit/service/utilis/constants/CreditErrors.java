package com.logni.credit.service.utilis.constants;

import java.util.HashMap;
import java.util.Map;

public class CreditErrors {
    // component code
    public static final String LOGNI_CREDIT_SERVICE = "12";

    // feature code
    public static final String LOGNI_CREDIT = "001";



    // error code
    public static final String LOAN_PRODUCT_NOT_FOUND = "001";
    public static final String LOAN_APPLICATION_REQUEST_NOT_VALID = "002";
    public static final String INVALID_INPUT = "004";
    public static final String INTERNAL_ERROR = "005";
    public static final String LOAN_REPAYMENT_NOT_FOUND = "006";
    public static final String LOAN_REPAYMENT_VALUE_INVALID = "007";

    public static final String FAILED_TO_CREATE_DIRECTORY = "008";
    public static final String FILE_NOT_FOUND = "009";
    



    // Error mapping
    public static final Map<String, String> ERROR_MAP = new HashMap<String, String>();

    static {
        ERROR_MAP.put(LOAN_PRODUCT_NOT_FOUND, "Loan Product Not Found");
        ERROR_MAP.put(LOAN_REPAYMENT_NOT_FOUND, "Loan Repayment Not Found");
        ERROR_MAP.put(INVALID_INPUT, "Invalid Input.");
        ERROR_MAP.put(LOAN_APPLICATION_REQUEST_NOT_VALID, "Loan Application Request Not Valid");
        ERROR_MAP.put(INTERNAL_ERROR, "Something went wrong.");
        ERROR_MAP.put(LOAN_REPAYMENT_VALUE_INVALID, "Loan Repayment Value is not valid");
        ERROR_MAP.put(FILE_NOT_FOUND, "File not found");
        ERROR_MAP.put(FAILED_TO_CREATE_DIRECTORY, "Could not create the directory where the uploaded files will be stored");
    }

    public static String getErrorCode(String featureCode,String errorCode){
        return CreditErrors.LOGNI_CREDIT_SERVICE + featureCode + errorCode;
    }
}
