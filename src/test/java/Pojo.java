import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class Pojo {

    public static class Post {
        private String title;
        private String body;
        private int userId;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    @Test
    public void createPost() {

        Post newPost = new Post();
        newPost.setTitle("Api Testing Dersleri");
        newPost.setBody("Bu dersler Udemy api testing için yapılmıştır.");
        newPost.setUserId(1);

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(newPost)
                .when()
                .post("/posts");
        System.out.println("Durum Kodu: " + response.getStatusCode());
        System.out.println("Yanıt Gövdesi: " + response.getBody().asString());
        System.out.println("Gönderilen Post'un Başlığı: " + response.path("title"));
        System.out.println("Gönderilen Post'un ID'si: " + response.path("id"));

        response.then()
                .statusCode(201)
                .body("title", equalTo("Api Testing Dersleri"))
                .body("body", equalTo("Bu dersler Udemy api testing için yapılmıştır."))
                .body("id", notNullValue());
    }
    @Test
    public void updatePost() {
        Post updatePost = new Post();
        updatePost.setTitle("Api Testing Dersleri Güncellendi");
        updatePost.setBody("Bu dersler Udemy api testing için güncellenmiştir.");
        updatePost.setUserId(1);

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatePost)
                .when()
                .put("/posts/1");
        System.out.println("Durum Kodu: " + response.getStatusCode());
        System.out.println("Yanıt Gövdesi: " + response.getBody().asString());
        System.out.println("Güncellenen Post'un Başlığı: " + response.path("title"));

        response.then()
                .statusCode(200) //204 No Content
                .body("title", equalTo("Api Testing Dersleri Güncellendi"))
                .body("body", equalTo("Bu dersler Udemy api testing için güncellenmiştir."))
                .body("userId", equalTo(1));
    }


 @Test
    public void updatePostTitle() {
        Post updatePostWithPatch = new Post();
        updatePostWithPatch.setTitle("Api Testing Dersleri Güncellendi (With Patch)");

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatePostWithPatch)
                .when()
                .patch("/posts/1");
        System.out.println("Durum Kodu: " + response.getStatusCode());
        System.out.println("Yanıt Gövdesi: " + response.getBody().asString());
        System.out.println("Güncellenen Post'un Başlığı: " + response.path("title"));

        response.then()
                .statusCode(200) //204 No Content
                .body("title", equalTo("Api Testing Dersleri Güncellendi (With Patch)"));
 }


 @Test
    public void deletePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Response response = given()
                .when()
                .delete("/posts/1");
        System.out.println("Durum Kodu: " + response.getStatusCode());
        System.out.println("Yanıt Gövdesi: " + response.getBody().asString());

        response.then()
                .statusCode(200) ;
 }


 @Test
    public void deleteAndVerifyPost(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        given()
               .when()
               .delete("/posts/1")
                .then()
               .statusCode(200);

        given()
               .when()
               .get("/posts/1")
               .then()
               .statusCode(404); //404 Not Found


 }


}
