package nl.com.waes.rhmoreira.challenge.web.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import nl.com.waes.rhmoreira.challenge.ChallengeException;
import nl.com.waes.rhmoreira.challenge.web.rest.vo.Message;

/**
 * Exception handler to gracefully handle all the business exceptions thrown by service classes
 *  
 * @author renato.moreira
 *
 */
@ControllerAdvice
public class ChallengeExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ChallengeException.class)
	public ResponseEntity<?> handleAppException(Exception e, WebRequest req){
		Message errorMessage = Message.newError(e.getMessage());
		
		return handleExceptionInternal(e, errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, req);
	}
	
}
