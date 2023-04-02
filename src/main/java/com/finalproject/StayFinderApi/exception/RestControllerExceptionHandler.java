package com.finalproject.StayFinderApi.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ErrorResponse handlerNotFoundException(NotFoundException ex)
	{		
//		String message = "Please provide Request Body in valid JSON format";
		List<String> messages = new ArrayList<>(1);
		messages.add(ex.getMessage());
	
		return new ErrorResponse(false,messages, HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(BadRequestException.class)
	public ErrorResponse resolveException(BadRequestException ex)
	{		
//		String message = "Please provide Request Body in valid JSON format";
		List<String> messages = new ArrayList<>(1);
		messages.add(ex.getMessage());
	
		return new ErrorResponse(false,messages, HttpStatus.BAD_REQUEST.getReasonPhrase(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AppException.class)
	public ErrorResponse resolveException(AppException ex)
	{		
//		String message = "Please provide Request Body in valid JSON format";
		List<String> messages = new ArrayList<>(1);
		messages.add(ex.getMessage());
	
		return new ErrorResponse(false,messages, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FileStorageException.class)
	public ErrorResponse resolveException(FileStorageException ex)
	{		
//		String message = "Please provide Request Body in valid JSON format";
		List<String> messages = new ArrayList<>(1);
		messages.add(ex.getMessage());
	
		return new ErrorResponse(false,messages, HttpStatus.NOT_FOUND.getReasonPhrase(),HttpStatus.NOT_FOUND);
	}
}
