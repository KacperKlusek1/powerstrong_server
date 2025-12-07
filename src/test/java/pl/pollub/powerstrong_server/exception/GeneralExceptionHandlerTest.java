package pl.pollub.powerstrong_server.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeneralExceptionHandlerTest {

    GeneralExceptionHandler handler = new GeneralExceptionHandler();

    @Test
    void globalExceptionHandling_shouldReturn500AndFormattedMessage() {
        Exception ex = new RuntimeException("Unexpected failure");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<?> response = handler.globalExceptionHandling(ex, request);

        assertEquals(500, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof Map);

        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertEquals(
                "Wystąpił nieoczekiwany błąd serwera: Unexpected failure",
                body.get("message")
        );
        assertEquals(500, body.get("status"));
        assertNotNull(body.get("timestamp"));
    }
}
