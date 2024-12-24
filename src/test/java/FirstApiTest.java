import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class FirstApiTest {
    @Test
    public void getFirstPost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)//OK
                .body("title", notNullValue());
    }

    @Test
    public void getAllPosts() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Status Code : " + response.getStatusCode());
        System.out.println(" ======================***************=================");
        System.out.println("İlk post'un başlığı: " + response.path("[0].title"));
        System.out.println(" ======================***************=================");
        System.out.println("Toplam post sayısı: " + response.path("size()"));


    }

    @Test
    public void createNewPost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String newPost = "{\n" +
                "  \"title\": \"Ben Api ogreniyorum\",\n" +
                "  \"body\": \"Bu bir api deneme yazısıdır.\",\n" +
                "  \"userId\": 1\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(newPost)
                .when()
                .post("/posts");
        System.out.println("Durum Kodu: " + response.getStatusCode());
        System.out.println("Yanıt Gövdesi: " + response.getBody().asString());
        System.out.println("Oluşturulan Post'un Başlığı: " + response.path("title"));
        System.out.println("Oluşturulan Post'un Kullanıcı ID'si: " + response.path("userId"));
                response.then()
                .statusCode(201)
                .body("title", equalTo("Ben Api ogreniyorum"))
                .body("body", equalTo("Bu bir api deneme yazısıdır."))
                .body("userId", equalTo(1));
    }

}


