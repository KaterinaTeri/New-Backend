import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.lessThan;

public class BackendRecipes extends BackendBeforAll {

    @Test
    void getRecipeWithQueryParametersPositiveTest() {
        given().spec(getRequestSpecification())
                .when()
                .get("https://api.spoonacular.com/recipes/716429/information")
                .then()
                .spec(responseSpecification);
    }

    @Test
    void testGet1() {
        given().spec(requestSpecification)
                .pathParams("id", 1408)
                .when()
                .get(getBaseUrl()+"recipes/{id}/information")
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    void testGet2() {
        given().spec(requestSpecification)
                .pathParams("id", 716437)
                .when()
                .get(getBaseUrl()+"recipes/{id}/information")
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    void testGet3() {
        given().spec(requestSpecification)
                .expect()
                .body("title", equalTo("Chilled Cucumber Avocado Soup with Yogurt and Kefir"))
                .when()
                .get(getBaseUrl()+"recipes/716437/information")
                .prettyPeek();
    }

    @Test
    void testGet4() {
        JsonPath response = (JsonPath) given().spec(requestSpecification)
                .when()
                .get(getBaseUrl()+"recipes/716437/information")
                .body()
                .jsonPath()
                .prettyPeek();
        assertThat(response.get("vegetarian"), is(true));
    }

    @Test
    void testGet5() {
        given().spec(requestSpecification)
                .response().spec(responseSpecification)
                .header("Connection", "keep-alive")
                .expect()
                .body("extendedIngredients[0].name", equalTo("avocado"))
                .when()
                .get("https://api.spoonacular.com/recipes/716437/information")
                .prettyPeek();
    }

    @Test
    void testPost1() {
        given().spec(requestSpecification)
                .contentType("application/x-www-form-urlencoded")
                .formParam("title", "Korean Beef Rice Bowl")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .spec(responseSpecification);
    }

//    @Test
//    void testPost2() {
//        given().spec(requestSpecification)
//                .contentType("application/x-www-form-urlencoded")
//                .formParam("ingredientList", "chicken")
//                .when().post(getBaseUrl()+"recipes/visualizeIngredients")
//                .prettyPeek()
//                .then()
//                .spec(responseSpecification);
//    }
//
//    @Test
//    void testPost3() {
//        given().spec(requestSpecification)
//                .contentType("application/x-www-form-urlencoded")
//                .formParam("ingredientList", "apple")
//                .when().post(getBaseUrl()+"recipes/visualizeTaste")
//                .prettyPeek()
//                .then()
//                .spec(responseSpecification);
//    }

    @Test
    void testPost4() {
        String cuisine = given().spec(requestSpecification)
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .path("cuisine");

        System.out.println("cuisine: " + cuisine);
    }

    @Test
    void testPost5() {
        String id = given().spec(requestSpecification)
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .body("{\n"
                        + " \"date\": 06102022, \n"
                        + " \"slot\": 1, \n"
                        + " \"position\": 0, \n"
                        + " \"type\": \"INGREDIENTS\", \n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/geekbrains/items")
                .then()
                .spec(responseSpecification)
                .extract()
                .jsonPath()
                .get("id")
                .toString();
        given().spec(requestSpecification)
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .delete("https://api.spoonacular.com/mealplanner/geekbrains/items/" + id)
                .then()
                .spec(responseSpecification);
    }
}
