package pl.pollub.powerstrong_server.exception;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationExceptionHandlerTest {

    ValidationExceptionHandler handler = new ValidationExceptionHandler();

    @Test
    void handleValidationExceptions_shouldReturnFieldErrorsMap() {
        FieldError fieldError = new FieldError(
                "objectName",
                "username",
                "must not be empty"
        );

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<?> response = handler.handleValidationExceptions(ex);

        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof Map);

        Map<String, String> body = (Map<String, String>) response.getBody();

        assertEquals("must not be empty", body.get("username"));
        assertEquals(1, body.size());
    }
}
