package com.logni.credit.service.exceptions;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoofiRunTimeException extends RuntimeException {
    private String code;
    public LoofiRunTimeException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}