package ru.ann.mast.crudDataBaseApi.exceptionHandling;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GrobalExceptionHandling {
	
	@ExceptionHandler
	public ResponseEntity<IncorrectData> handlerException(
			Exception exception) {
		IncorrectData data = new IncorrectData();
		data.setInfo(exception.getMessage());
		
		return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
		
	}

		@ExceptionHandler
		public ResponseEntity<IncorrectData> handlerException(
				NoSuchException exception) {
			IncorrectData data = new IncorrectData();
			data.setInfo(exception.getMessage());
			
			return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
			
		}
		
		
		@ExceptionHandler
		public ResponseEntity<IncorrectData> handlerException(HttpMessageNotReadableException ex) {
			IncorrectData incorrectData = new IncorrectData("Malformed JSON Request", ex.getMessage());
		    return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler
		protected ResponseEntity<IncorrectData> handlerException(MethodArgumentNotValidException ex) {
		    List<String> errors = ex.getBindingResult()
		            .getFieldErrors()
		            .stream()
		            .map(x -> x.getDefaultMessage())
		            .collect(Collectors.toList());
		    IncorrectData incorrectData = new IncorrectData();
		    incorrectData.setInfo("Method Argument Not Valid");
		    incorrectData.setErrors(errors);
		    return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler
		protected ResponseEntity<IncorrectData> handlerException(MethodArgumentTypeMismatchException ex) {
			IncorrectData incorrectData = new IncorrectData();
			incorrectData.setInfo(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
		            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
		    return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler
		public ResponseEntity<IncorrectData> handlerException(InvalidDataAccessApiUsageException ex) {
			IncorrectData incorrectData = new IncorrectData();
			incorrectData.setInfo("Unsaved object was passed in the request");
		     
		    return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
		}
	
}
