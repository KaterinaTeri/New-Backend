import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BackendLog extends BackendBeforAll {

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void getRequestLogTest() {
        given().spec(requestSpecification)
                .log().method()
                .log().params()
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information");

        System.out.println("+++++++++++++++++++++++++++");

        given().spec(requestSpecification)
                .log().all()
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information");
    }

    @Test
    void getResponseLogTest() {
        given().spec(requestSpecification)
                .log().all()
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .prettyPeek();
    }

    @Test
    void getErrorTest() {
        given().spec(requestSpecification)
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .then()
                .spec(responseSpecification);
    }
}
