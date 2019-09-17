package org.carlspring.strongbox.controllers.configuration.security.cors;

import org.carlspring.strongbox.config.IntegrationTest;
import org.carlspring.strongbox.forms.configuration.CorsConfigurationForm;
import org.carlspring.strongbox.rest.common.RestAssuredBaseTest;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

/**
 * @author Przemyslaw Fusik
 * @author Pablo Tirado
 */
@IntegrationTest
public class CorsConfigurationControllerTest
        extends RestAssuredBaseTest
{

    @Inject
    private CorsConfigurationSource corsConfigurationSource;

    private Map<String, CorsConfiguration> initialConfiguration;

    @Override
    @BeforeEach
    public void init()
            throws Exception
    {
        super.init();
        setContextBaseUrl("/api/configuration/cors");
        initialConfiguration = new HashMap<>(
                ((UrlBasedCorsConfigurationSource) corsConfigurationSource).getCorsConfigurations());
    }

    @AfterEach
    public void after()
    {
        ((UrlBasedCorsConfigurationSource) corsConfigurationSource).setCorsConfigurations(initialConfiguration);
    }

    @Test
    public void testUpdateWithEmptyCollectionAndJsonResponse()
    {
        given().accept(MediaType.APPLICATION_JSON_VALUE)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .body(new CorsConfigurationForm(Collections.emptyList()))
               .when()
               .put(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("message", containsString(CorsConfigurationController.SUCCESSFUL_UPDATE));

        // follow-up check to ensure records has been properly saved.
        given().accept(MediaType.APPLICATION_JSON_VALUE)
               .when()
               .get(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("origins", hasSize(0));

    }

    @Test
    public void testUpdateWithEmptyCollectionAndTextResponse()
    {
        given().accept(MediaType.TEXT_PLAIN_VALUE)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .body(new CorsConfigurationForm(Collections.emptyList()))
               .when()
               .put(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body(containsString(CorsConfigurationController.SUCCESSFUL_UPDATE));

        // follow-up check to ensure records has been properly saved.
        given().accept(MediaType.APPLICATION_JSON_VALUE)
               .when()
               .get(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("origins", hasSize(0));
    }

    @Test
    public void testAllowOneOrigin()
    {
        given().accept(MediaType.APPLICATION_JSON_VALUE)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .body(new CorsConfigurationForm(Collections.singletonList("http://example.com")))
               .when()
               .put(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("message", containsString(CorsConfigurationController.SUCCESSFUL_UPDATE));

        // follow-up check to ensure records has been properly saved.
        given().accept(MediaType.APPLICATION_JSON_VALUE)
               .when()
               .get(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("origins", hasSize(1));
    }

    @Test
    public void testAllowAllOrigins()
    {
        given().log().all().accept(MediaType.APPLICATION_JSON_VALUE)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .body(new CorsConfigurationForm(Collections.singletonList("*")))
               .when()
               .put(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("message", containsString(CorsConfigurationController.SUCCESSFUL_UPDATE));

        // follow-up check to ensure records has been properly saved.
        given().accept(MediaType.APPLICATION_JSON_VALUE)
               .when()
               .get(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("origins", hasSize(1))
               .body("origins", hasItem("*"));
    }

    @Test
    public void testAllowMultipleOrigins()
    {
        given().accept(MediaType.APPLICATION_JSON_VALUE)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .body(new CorsConfigurationForm(Arrays.asList("http://example.com", "https://github.com/strongbox", "http://carlspring.org")))
               .when()
               .put(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("message", containsString(CorsConfigurationController.SUCCESSFUL_UPDATE));

        // follow-up check to ensure records has been properly saved.
        given().accept(MediaType.APPLICATION_JSON_VALUE)
               .when()
               .get(getContextBaseUrl())
               .peek()
               .then()
               .statusCode(HttpStatus.OK.value())
               .body("origins", hasSize(equalTo(3)))
               .body("origins", hasItem("https://github.com/strongbox"));
    }

}
