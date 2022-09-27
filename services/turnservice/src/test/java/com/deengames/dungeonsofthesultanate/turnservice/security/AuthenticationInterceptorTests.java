package com.deengames.dungeonsofthesultanate.turnservice.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class AuthenticationInterceptorTests {


    @Test
    public void preHandle_throwsAndSetsHttpStatusTo500_ifExpectedSecretIsNullOrEmpty() {
        // Arrange
        String[] testCases = {null, ""};
        for (String testCase : testCases) {
            var interceptor = new AuthenticationInterceptor(testCase);
            var request = mock(HttpServletRequest.class);
            var response = mock(HttpServletResponse.class);

            // Act
            var actual = Assertions.assertThrows(SecurityException.class,
                    () -> interceptor.preHandle(request, response, null));

            // Assert
            Assertions.assertNotNull(actual);
            verify(response).setStatus(500);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"EXPECTED secret!", "expectedsecret!", "haha obviously wrong", "1234#$#*@"})
    public void preHandle_throwsAndSetsHttpStatusTo401_ifSecretsDontMatch(String actualSecret) throws Exception {
        // Arrange
        var interceptor = new AuthenticationInterceptor("expected secret!");
        var request = mock(HttpServletRequest.class);
        when(request.getHeader("Client-Secret")).thenReturn(actualSecret);

        var response = mock(HttpServletResponse.class);
        var responseWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(responseWriter);

        // Act
        var result = interceptor.preHandle(request, response, null);

        // Assert
        Assertions.assertFalse(result);
        verify(response).setStatus(401);
        verify(responseWriter).write("unauthorized");
    }

    @Test
    public void preHandle_returnsTrue_ifSecretsMatch() throws Exception {
        // Arrange
        var expectedSecret = "expected secret!!";
        var interceptor = new AuthenticationInterceptor(expectedSecret);
        var request = mock(HttpServletRequest.class);
        when(request.getHeader("Client-Secret")).thenReturn(expectedSecret);

        var response = mock(HttpServletResponse.class);
        var responseWriter = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(responseWriter);

        // Act
        var result = interceptor.preHandle(request, response, null);

        // Assert
        Assertions.assertTrue(result);
    }
}
