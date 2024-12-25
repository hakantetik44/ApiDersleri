import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PathQueryParamsTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void pathParametreTest() {
        int userId = 5;

        given()
            .pathParam("id", userId)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(200)
            .body("id", equalTo(5));
    }

    @Test
    public void queryParametreTest() {
        given()
            .queryParam("userId", 1)
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .body("userId", everyItem(equalTo(1)));
    }

    @Test
    public void pathVeQueryParametreTest() {
        given()
            .pathParam("postId", 1)
            .queryParam("_embed", "comments")
        .when()
            .get("/posts/{postId}")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("comments.size()", greaterThan(0));
    }

    @Test
    public void headerTest() {
        given()
            .contentType(ContentType.JSON)
            .header("Accept-Language", "tr")
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    public void detayliResponseTest() {
        given()
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .body("email", matchesPattern("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))
            .body("address.city", not(emptyString()))
            .body("company.name", not(emptyString()));

    }

 

    @Test
    public void loggingTest() {
        given()
            .log().method()
            .log().uri()
        .when()
            .get("/posts/1")
        .then()
            .log().status()
            .log().body()
            .statusCode(200);
    }

    @Test
    public void notFoundTest() {
        given()
            .log().all()
        .when()
            .get("/posts/999")
        .then()
            .log().all()
            .assertThat()
            .statusCode(200);
    }
}
