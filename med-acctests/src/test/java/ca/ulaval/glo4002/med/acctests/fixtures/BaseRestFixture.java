package ca.ulaval.glo4002.med.acctests.fixtures;

import ca.ulaval.glo4002.med.acctests.runners.MedServerRunner;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseRestFixture {

    public RequestSpecification givenBaseRequest() {
        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .port(MedServerRunner.JETTY_TEST_PORT);
    }
}
