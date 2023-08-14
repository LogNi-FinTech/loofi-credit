package com.logni.credit.service.exceptions;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoofiBusinessRunTimeException extends RuntimeException {
    private String code;
    public LoofiBusinessRunTimeException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}