    package com.logni.credit.service.exceptions;


    import com.logni.credit.service.utilis.constants.CreditErrors;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.FieldError;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseBody;
    import org.springframework.web.bind.annotation.ResponseStatus;
    import org.springframework.web.context.request.WebRequest;

    import java.util.HashMap;
    import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest webRequest) {
        Map<String, String> errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Exception",ex);
        return new ErrorResponse(CreditErrors
                .getErrorCode(CreditErrors.LOGNI_CREDIT_SERVICE,
                		CreditErrors.INVALID_INPUT),
                errors.toString());

    }
    /*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyBadDataException.class)
    @ResponseBody ErrorInfo
    handleBadRequest(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL(), ex);
    }
    */

    @ExceptionHandler({LoofiBusinessRunTimeException.class})
    public final ResponseEntity<ErrorResponse> handleAPIERRORExceptions(LoofiBusinessRunTimeException ex) {
        log.error("API Exception",ex);
        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({LoofiRunTimeException.class})
    public final ResponseEntity<ErrorResponse> handleEnternalServerxceptions(LoofiRunTimeException ex) {
        log.error("API Exception",ex);
        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({Exception.class,RuntimeException.class})
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Exception",ex);
        ErrorResponse error = new ErrorResponse(
        		CreditErrors.INTERNAL_ERROR, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
