package br.com.attornatus.handlers;

import br.com.attornatus.exceptions.models.Field;
import br.com.attornatus.exceptions.models.Problem;
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
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problema = new Problem(status.value(), ex.getMessage());
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var fields = new ArrayList<Field>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fields.add(new Field(nome, mensagem));
        }

        String titulo = "Um ou mais campos estão inválidos! "
                + "Faça o preenchimento correto e tente novamente.";
        var problema = new Problem(status.value(), titulo, fields);

        return handleExceptionInternal(ex, problema, headers, status, request);
    }
}


    /*
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

   /* @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Map<String, AbstractTreatment>> handleConflict(Exception ex, HttpServletRequest request) {
        //String bodyOfResponse = "Ocorreu um erro ao processar a solicitação: "+ex.getMessage();
        this.log(ex);
        return ErrorResponse.handle(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    protected ResponseEntity<Map<String, AbstractTreatment>> handleNotFound(Exception ex, HttpServletRequest request) {
      //  String bodyOfResponse = "O registro solicitado não foi encontrado.";
        return ErrorResponse.handle(ex, request, HttpStatus.NOT_FOUND);
    }

    private void log(Exception exception) {
        var message = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
        LOG.error(message);
    }
}
*/
