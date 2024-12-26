import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AuthenticationTest {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        logger.info("API Base URL ayarlandı: {}", RestAssured.baseURI);
        System.out.println("===========================================");
        System.out.println("API TEST BAŞLIYOR");
        System.out.println("===========================================");
    }

    @Test(priority = 1, description = "Başarılı token alma testi")
    public void createTokenSuccessTest() {
        System.out.println("\n=== Token Alma Testi (Başarılı) ===");
        logger.info("Token alma testi başlatılıyor (başarılı senaryo)");
        String requestBody = """
                {
                    "username": "admin",
                    "password": "password123"
                }
                """;
        logger.info("İstek gövdesi hazırlandı: {}", requestBody);

        Response response = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .log().all()
        .when()
            .post("/auth")
        .then()
            .log().all()
            .statusCode(200)
            .body("token", not(emptyString()))
            .extract()
            .response();
        
        String token = response.path("token");
        System.out.println("\n>>> TOKEN BAŞARIYLA ALINDI <<<");
        System.out.println(">>> Token değeri: " + token);
        System.out.println("===============================\n");
        logger.info("Token başarıyla alındı: {}", token);
    }

    @Test(priority = 2, description = "Başarısız token alma testi")
    public void createTokenFailTest() {
        System.out.println("\n=== Token Alma Testi (Başarısız) ===");
        logger.info("Token alma testi başlatılıyor (başarısız senaryo)");
        String requestBody = """
                {
                    "username": "wronguser",
                    "password": "wrongpass"
                }
                """;
        logger.info("Hatalı kullanıcı bilgileriyle istek gövdesi hazırlandı: {}", requestBody);

        Response response = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .log().all()
        .when()
            .post("/auth")
        .then()
            .log().all()
            .statusCode(200)
            .body("reason", equalTo("Bad credentials"))
            .extract()
            .response();
        
        String errorMessage = response.path("reason");
        System.out.println("\n>>> HATA MESAJI ALINDI <<<");
        System.out.println(">>> Hata: " + errorMessage);
        System.out.println("===========================\n");
        logger.info("Beklenen hata mesajı alındı: {}", errorMessage);
    }

    @Test(priority = 3, description = "Token gerektirmeyen endpoint testi")
    public void getBookingWithoutTokenTest() {
        System.out.println("\n=== Token Gerektirmeyen Test ===");
        logger.info("Token gerektirmeyen endpoint testi başlatılıyor");

        Response response = given()
            .log().all()
        .when()
            .get("/booking/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("firstname", not(emptyString()))
            .extract()
            .response();
        
        String firstname = response.path("firstname");
        System.out.println("\n>>> REZERVASYON BİLGİSİ ALINDI <<<");
        System.out.println(">>> Rezervasyon sahibi: " + firstname);
        System.out.println("================================\n");
        logger.info("Rezervasyon sahibinin adı başarıyla alındı: {}", firstname);
    }

    @Test(priority = 4, description = "Token ile rezervasyon güncelleme testi")
    public void updateBookingWithTokenTest() {
        System.out.println("\n=== Rezervasyon Güncelleme Testi ===");
        logger.info("Rezervasyon güncelleme testi başlatılıyor");
        
        // First, get token
        System.out.println(">>> Token Alınıyor...");
        logger.info("İlk adım: Token alınıyor");
        Response tokenResponse = given()
            .contentType(ContentType.JSON)
            .body("""
                    {
                        "username": "admin",
                        "password": "password123"
                    }
                    """)
        .when()
            .post("/auth")
        .then()
            .extract()
            .response();
        
        String token = tokenResponse.path("token");
        System.out.println(">>> Alınan Token: " + token);
        logger.info("Token başarıyla alındı: {}", token);

        // Then try to update a booking
        System.out.println("\n>>> Rezervasyon Güncelleniyor...");
        String updateBody = """
                {
                    "firstname": "James",
                    "lastname": "Brown",
                    "totalprice": 111,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2018-01-01",
                        "checkout": "2019-01-01"
                    },
                    "additionalneeds": "Breakfast"
                }
                """;
        logger.info("Güncelleme için hazırlanan veri: {}", updateBody);

        Response updateResponse = given()
            .contentType(ContentType.JSON)
            .cookie("token", token)
            .body(updateBody)
            .log().all()
        .when()
            .put("/booking/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("firstname", equalTo("James"))
            .extract()
            .response();
        
        String updatedName = updateResponse.path("firstname");
        System.out.println("\n>>> REZERVASYON GÜNCELLENDİ <<<");
        System.out.println(">>> Yeni isim: " + updatedName);
        System.out.println("===============================\n");
        logger.info("Rezervasyon başarıyla güncellendi. Yeni isim: {}", updatedName);
    }
}
