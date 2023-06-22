package org.geekhub.pavlo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(basePackages = "org.geekhub.pavlo.controller.rest")
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

    @ExceptionHandler({DataAccessException.class, Exception.class})
    protected ResponseEntity<Object> handleConflict(Exception e, WebRequest request) {
        String errText = e.getMessage();
        if (errText == null) {
            errText = e.toString();
        }
        logger.error(errText, e);
        return handleExceptionInternal(e, errText, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
