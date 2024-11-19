package itst.socialraccoon.api.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.PersistentObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
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
import com.google.gson.Gson;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    // 404 - Not Found for missing resources
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException e) {
        if (e.getMessage() == null) {
            return new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 400 - Bad Request for invalid JSON format
    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleJsonParseException(JsonParseException e) {
        return new ResponseEntity<>("Invalid JSON format: " + e.getOriginalMessage(), HttpStatus.BAD_REQUEST);
    }

    // 404 - Not Found for unavailable resources
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException e) {
        return new ResponseEntity<>("Resource not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 400 - Bad Request for type mismatches in method arguments
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Type mismatch for parameter '" + e.getName() + "': expected type " + e.getRequiredType().getSimpleName());
    }

    // 400 - Bad Request for missing parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing request parameter: '" + e.getParameterName() + "'");
    }

    // 400 - Bad Request for illegal arguments
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
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Entity not found: " + e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        e.getConstraintViolations().forEach(violation -> {
            message.append("Parameter '")
                    .append(violation.getPropertyPath())
                    .append("' ")
                    .append(violation.getMessage())
                    .append(". \n");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        String detailedMessage = "Invalid JSON format";

        if (message != null) {
            if (message.contains("Unexpected end-of-input")) {
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

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Required request parameters are missing: " + e.getMessage());
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Integrity constraint violation: " + e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Null pointer exception: " + e.getMessage());
    }

    @ExceptionHandler(PersistentObjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handlePersistentObjectException(PersistentObjectException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Persistent object exception: " + e.getMessage());
    }
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Required part '" + e.getRequestPartName() + "' is not present.");
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
}
