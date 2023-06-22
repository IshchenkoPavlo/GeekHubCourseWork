package org.geekhub.pavlo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(basePackages = "org.geekhub.pavlo.controller.web")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    protected ModelAndView  handleConflict(RuntimeException e) {
        String errText = e.getMessage();
        if (errText == null) {
            errText = e.toString();
        }

        logger.error(errText, e);

        ModelAndView modelAndView = new ModelAndView("ErrorPage");
        modelAndView.addObject("errorText", errText);

        return modelAndView;
    }
}
