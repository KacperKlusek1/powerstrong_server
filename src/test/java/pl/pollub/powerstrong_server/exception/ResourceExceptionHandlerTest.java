package pl.pollub.powerstrong_server.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceExceptionHandlerTest {

    ResourceExceptionHandler handler = new ResourceExceptionHandler();

    @Test
    void resourceNotFoundHandling_shouldReturnNotFoundStatusAndMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("User not found");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<?> response = handler.resourceNotFoundHandling(ex, request);

        assertEquals(404, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof Map);

        Map<String, Object> body = (Map<String, Object>) response.getBody();

        assertEquals("User not found", body.get("message"));
        assertEquals(404, body.get("status"));
        assertNotNull(body.get("timestamp"));
    }
}
