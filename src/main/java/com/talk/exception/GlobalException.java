package com.talk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {
	
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetail> userExceptionHandler(UserException userException,WebRequest request){
		 ErrorDetail err = new ErrorDetail(userException.getMessage(), request.getDescription(false));
		 
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ChatException.class)
	public ResponseEntity<ErrorDetail> chatExceptionHandler(ChatException chatException,WebRequest request){
		 ErrorDetail err = new ErrorDetail(chatException.getMessage(), request.getDescription(false));
		 
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MessageException.class)
	public ResponseEntity<ErrorDetail> messageExceptionHandler(MessageException messageException,WebRequest request){
		 ErrorDetail err = new ErrorDetail(messageException.getMessage(), request.getDescription(false));
		 
		return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
	}


}
