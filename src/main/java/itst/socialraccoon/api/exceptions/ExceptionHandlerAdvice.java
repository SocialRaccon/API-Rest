package itst.socialraccoon.api.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.PersistentObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final Gson gson = new Gson();

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Resource not found");
        errorDetails.put("message", e.getMessage() != null ? e.getMessage() : "Resource not found");
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleJsonParseException(JsonParseException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Invalid JSON format");
        errorDetails.put("message", e.getOriginalMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Resource not found");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Type mismatch");
        errorDetails.put("message", "Type mismatch for parameter '" + e.getName() + "': expected type " + e.getRequiredType().getSimpleName());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Missing request parameter");
        errorDetails.put("message", "Missing request parameter: '" + e.getParameterName() + "'");
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("errors", errors);
        mav.setViewName("methodArgumentNotValid");
        return mav;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Entity not found");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        e.getConstraintViolations().forEach(violation -> {
            message.append("Parameter '")
                    .append(violation.getPropertyPath())
                    .append("' ")
                    .append(violation.getMessage())
                    .append(". \n");
        });
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Constraint violation");
        errorDetails.put("message", message.toString());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        String detailedMessage = "Invalid JSON format";
        if (message != null) {
            if (message.contains("Unexpected character")) {
                detailedMessage = "There is an unexpected character in the JSON data.";
            } else if (message.contains("Unexpected token")) {
                detailedMessage = "There is an unexpected token in the JSON data.";
            } else if (message.contains("Unexpected end-of-input")) {
                detailedMessage = "It looks like a closing brace '}' is missing.";
            } else if (message.contains("Cannot deserialize value of type")) {
                detailedMessage = "There is a type mismatch in the JSON data.";
            } else if (message.contains("Unrecognized field")) {
                detailedMessage = "There is an unrecognized field in the JSON data.";
            } else if (message.contains("Missing required creator property")) {
                detailedMessage = "A required property is missing in the JSON data.";
            }
        }
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", detailedMessage);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUnsatisfiedServletRequestParameterException(
            UnsatisfiedServletRequestParameterException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Unsatisfied request parameters");
        errorDetails.put("message", "Required request parameters are missing: " + e.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(
            SQLIntegrityConstraintViolationException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Integrity constraint violation");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Null pointer exception");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PersistentObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlePersistentObjectException(PersistentObjectException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Persistent object exception");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Missing request part");
        errorDetails.put("message", "Required part '" + e.getRequestPartName() + "' is not present.");
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ModelAndView handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("errors", errors);
        mav.setViewName("methodArgumentNotValid");
        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<String> handleInvalidFileType(InvalidFileTypeException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Invalid file type");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Method not allowed");
        errorDetails.put("message", "Request method '" + e.getMethod() + "' is not supported for this endpoint.");
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InappropriateContentException.class)
    public ResponseEntity<String> handleInappropriateContent(InappropriateContentException e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Inappropriate content");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Illegal state");
        errorDetails.put("message", ex.getMessage());
        return new ResponseEntity<>(gson.toJson(errorDetails), HttpStatus.CONFLICT);
    }
}