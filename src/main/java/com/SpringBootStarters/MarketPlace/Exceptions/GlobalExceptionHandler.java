package com.SpringBootStarters.MarketPlace.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final String ERROR_MESSAGE = "An error occurred: ";
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
		logger.error(ERROR_MESSAGE + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ERROR_MESSAGE + ex.getMessage());
    }

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
		logger.error(ERROR_MESSAGE + ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ERROR_MESSAGE + ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		logger.error(ERROR_MESSAGE + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_MESSAGE + ex.getMessage());
	}

	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
		logger.error(ERROR_MESSAGE + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_MESSAGE + ex.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		logger.error(ERROR_MESSAGE + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ERROR_MESSAGE + ex.getMessage());
	}
}
