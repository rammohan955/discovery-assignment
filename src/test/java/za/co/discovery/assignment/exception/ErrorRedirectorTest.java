package za.co.discovery.assignment.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class ErrorRedirectorTest {

    @InjectMocks
    ErrorRedirector errorRedirector;

    @Test
    public void handleValidationExceptionTest(){
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,errorRedirector.handleValidationException(new ValidationException("Some error")).getStatusCode());
    }

    @Test
    public void handleRuntimeExceptionTest(){
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,errorRedirector.handleRuntimeException(new ValidationException("Some error")).getStatusCode());
    }


    @Test
    public void handleExceptionTest(){
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,errorRedirector.handleException(new ValidationException("Some error")).getStatusCode());
    }



}
