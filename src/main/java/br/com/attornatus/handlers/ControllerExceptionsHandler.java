package br.com.attornatus.handlers;

import br.com.attornatus.handlers.models.Field;
import br.com.attornatus.handlers.models.Problem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionsHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionsHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problem = new Problem(status.value(), ex.getMessage());
        LOG.error("NoSuchElementExcepetion: {}:", ex.getMessage());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = new Problem(status.value(), ex.getMessage());
        LOG.error("IllegalArgumentException {}:", ex.getMessage());
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var fields = new ArrayList<Field>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fields.add(new Field(name, mensagem));
        }

        String title = "Um ou mais campos estão inválidos! "
                + "Faça o preenchimento correto e tente novamente.";
        var problem = new Problem(status.value(), title, fields);

        LOG.error("MethodArgumentNotValid {}:", fields);
        return handleExceptionInternal(ex, problem, headers, status, request);
    }
}