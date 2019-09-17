package org.carlspring.strongbox.controllers;

import org.carlspring.strongbox.config.IntegrationTest;
import org.carlspring.strongbox.rest.common.MavenRestAssuredBaseTest;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT;

/**
 * @author Steve Todorov
 * @author Pablo Tirado
 */
@Execution(CONCURRENT)
@IntegrationTest
public class PingControllerTest
        extends MavenRestAssuredBaseTest
{

    @Override
    @BeforeEach
    public void init()
            throws Exception
    {
        super.init();
        setContextBaseUrl("/api/ping");
    }

    @Test
    void shouldReturnPong()
    {
        String acceptHeader = MediaType.TEXT_EVENT_STREAM_VALUE;

        ValidatableMockMvcResponse response = given().accept(acceptHeader)
                                                     .when()
                                                     .get(getContextBaseUrl())
                                                     .peek()
                                                     .then()
                                                     .statusCode(HttpStatus.OK.value());

        validateResponseBody(response, acceptHeader, PingController.READY_STREAM_VALUE);
    }

    @ParameterizedTest
    @ValueSource(strings = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.TEXT_PLAIN_VALUE })
    void shouldReturnPongForAuthenticatedUsers(String acceptHeader)
    {
        String url = getContextBaseUrl() + "/token";
        ValidatableMockMvcResponse response = given().accept(acceptHeader)
                                                     .when()
                                                     .get(url)
                                                     .peek()
                                                     .then()
                                                     .statusCode(HttpStatus.OK.value());

        String message = "pong";
        validateResponseBody(response, acceptHeader, message);
    }

    @ParameterizedTest
    @ValueSource(strings = { MediaType.APPLICATION_JSON_VALUE,
                             MediaType.TEXT_PLAIN_VALUE })
    @WithAnonymousUser
    void anonymousUsersShouldNotBeAbleToAccess(String acceptHeader)
    {
        String url = getContextBaseUrl() + "/token";
        given().accept(acceptHeader)
               .when()
               .get(url)
               .peek()
               .then()
               .log()
               .all()
               .statusCode(HttpStatus.UNAUTHORIZED.value())
               .body(notNullValue());
    }

    private void validateResponseBody(ValidatableMockMvcResponse response,
                                      String acceptHeader,
                                      String message)
    {
        if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE))
        {
            response.body("message", equalTo(message));
        }
        else if (acceptHeader.equals(MediaType.TEXT_PLAIN_VALUE) || acceptHeader.equals(MediaType.TEXT_EVENT_STREAM_VALUE))
        {
            response.body(equalTo(message));
        }
        else
        {
            throw new IllegalArgumentException("Unsupported content type: " + acceptHeader);
        }
    }
}
