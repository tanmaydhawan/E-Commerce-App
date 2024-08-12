package com.springboot.ecommerce.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception) {
		
		Map<String, String> response = new HashMap<>();
		
		exception.getAllErrors().forEach(err -> {
			String fieldName = ((FieldError)err).getField();
			String errMessage = err.getDefaultMessage();
			
			response.put(fieldName, errMessage);
		});
		
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
	}
	
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception){
//		
//		String message = exception.getMessage();
//		
//		return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
//	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
		
		ErrorDetails errorDetails = new ErrorDetails(
				HttpStatus.NOT_FOUND.value(),
				exception.getMessage(), 
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<String> handleApiException(ApiException exception){
		
		String message = exception.getMessage();
		
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

}
