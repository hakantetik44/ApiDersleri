import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HeaderAndQueryParamsTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void headerIleTest() {
        given()
            .header("Content-Type", "application/json")
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200);
    }

    @Test
    public void queryParametreleriIleTest() {
        given()
            .queryParam("userId", 1)
                //https://jsonplaceholder.typicode.com/posts?userId=1
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("findAll { it.userId == 1 }.size()", equalTo(10));
    }

    @Test
    public void headerVeQueryParametreleriIleTest() {
        given()
            .header("Content-Type", "application/json")
            .queryParam("userId", 1)
            .queryParam("_limit", 3)
                //https://jsonplaceholder.typicode.com/posts?userId=1&_limit=3
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("size()", equalTo(3));
    }
}
