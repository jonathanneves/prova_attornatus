package br.com.attornatus.responses;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessResponse<T> {

    private static final Logger LOG = LoggerFactory.getLogger(SuccessResponse.class);

    public ResponseEntity<T> handle(T object, Class<?> classController, HttpServletRequest request, HttpStatus status) {
        LOG.debug(classController.getName(), request, object);
        return new ResponseEntity<>(object, status);
    }

}
