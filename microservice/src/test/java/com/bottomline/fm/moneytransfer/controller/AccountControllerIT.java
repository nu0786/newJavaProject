package com.bottomline.fm.moneytransfer.controller;

import com.bottomline.fm.moneytransfer.IntegrationTestClass;
import com.bottomline.fm.moneytransfer.dto.AccountCreationRequest;
import com.bottomline.fm.moneytransfer.repository.base.AccountRepository;
import com.bottomline.fm.moneytransfer.repository.entity.AccountEntity;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountControllerIT extends IntegrationTestClass {
    @LocalServerPort
    private String port;

    protected RequestSpecification spec;

    @Autowired
    protected AccountRepository accountRepository;

    @BeforeEach
    public void initialize() {
        RestAssuredConfig config = RestAssuredConfig.config();
        LogConfig logConfigForRequestSpec = config.getLogConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        config = config.logConfig(logConfigForRequestSpec);
        spec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri("http://localhost:" + port + "/api/account")
            .setConfig(config)
            .build();
    }

    @Test
    public void createAccountEndpoint_shouldCreateAccountWhenRequestIsValid() {
        // Given
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setBirthdate("1987-09-23");
        accountCreationRequest.setPhone("+7998964874");
        accountCreationRequest.setEmail("example@example.com");
        accountCreationRequest.setFullName("John doe");
        accountCreationRequest.setCurrency("CHF");
        JsonPath jsonPath = given().spec(spec)
            .when()
        // When
            .body(accountCreationRequest)
            .post("")
        // Then
            .then()
            .statusCode(200)
            .extract().jsonPath();
        Optional<AccountEntity> accountNumber = accountRepository.findByAccountNumber(jsonPath.get("accountNumber"));
        assertThat(accountNumber).isPresent();
    }

    @Test
    public void getAccountEndpoint_shouldFindAccountWhenAccountNumberExists() {
        // Given
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.setBirthdate("1987-09-23");
        accountCreationRequest.setPhone("+7998964874");
        accountCreationRequest.setEmail("example@example.com");
        accountCreationRequest.setFullName("John doe");
        accountCreationRequest.setCurrency("CHF");
        JsonPath jsonPath = given().spec(spec)
            .when()
            .body(accountCreationRequest)
            .post("")
            .then()
            .statusCode(200)
            .extract().jsonPath();
        String accountNumber = jsonPath.getString("accountNumber");
        jsonPath = given().spec(spec)
            .when()
            // When
            .get("/{accountNumber}", accountNumber)
            // Then
            .then()
            .statusCode(200)
            .extract().jsonPath();
        assertThat(jsonPath.getString("accountNumber")).isEqualTo(accountNumber);
    }
}
