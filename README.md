# API Test Otomasyonu Projesi

Bu proje, REST API test otomasyonu öğrenmek için oluşturulmuş bir eğitim projesidir. [JSONPlaceholder](https://jsonplaceholder.typicode.com/) test API'si kullanılarak çeşitli HTTP metodları test edilmiştir.

## Kullanılan Teknolojiler

- Java
- Rest Assured
- TestNG
- Maven

## HTTP Metodları ve Kullanımları

### GET
- Veri okuma işlemleri için kullanılır
- İki farklı kullanım şekli:
  1. Tek kayıt getirme: `/posts/1`
  2. Tüm kayıtları getirme: `/posts`
- Örnek kullanım:
```java
given()
    .when()
    .get("/posts/1")
    .then()
    .statusCode(200)
    .body("title", notNullValue());
```

### POST
- Yeni kayıt oluşturma işlemleri için kullanılır
- Content-Type header'ı gereklidir
- Request body içinde veri gönderilir
- Örnek kullanım:
```java
Post newPost = new Post();
newPost.setTitle("Test Başlığı");
newPost.setBody("Test İçeriği");

given()
    .contentType(ContentType.JSON)
    .body(newPost)
    .when()
    .post("/posts")
    .then()
    .statusCode(201);
```

### PUT
- Mevcut kaydı tamamen güncelleme için kullanılır
- Tüm alanların gönderilmesi gerekir
- Örnek kullanım:
```java
Post updatePost = new Post();
updatePost.setTitle("Güncel Başlık");
updatePost.setBody("Güncel İçerik");

given()
    .contentType(ContentType.JSON)
    .body(updatePost)
    .when()
    .put("/posts/1")
    .then()
    .statusCode(200);
```

### PATCH
- Mevcut kaydın sadece belirli alanlarını güncelleme için kullanılır
- Sadece değişecek alanlar gönderilir
- Örnek kullanım:
```java
Post partialUpdate = new Post();
partialUpdate.setTitle("Yeni Başlık");

given()
    .contentType(ContentType.JSON)
    .body(partialUpdate)
    .when()
    .patch("/posts/1")
    .then()
    .statusCode(200);
```

### DELETE
- Kayıt silme işlemleri için kullanılır
- Silme işlemi sonrası kaydın erişilemez olması beklenir
- Örnek kullanım:
```java
given()
    .when()
    .delete("/posts/1")
    .then()
    .statusCode(200);
```

## Proje Yapısı

Proje iki ana test sınıfından oluşmaktadır:

### 1. FirstApiTest.java
Temel API testlerini içerir:
- `getFirstPost()`: GET metodu ile tek bir post getirme
- `getAllPosts()`: GET metodu ile tüm postları listeleme
- `createNewPost()`: POST metodu ile yeni post oluşturma

### 2. Pojo.java
POJO (Plain Old Java Object) kullanarak daha gelişmiş API testlerini içerir:

#### Post Sınıfı
```java
public static class Post {
    private String title;
    private String body;
    private int userId;
    // Getter ve Setter metodları
}
```

#### Test Metodları
- `createPost()`: POST metodu ile POJO kullanarak yeni post oluşturma
- `updatePost()`: PUT metodu ile post'u tamamen güncelleme
- `updatePostTitle()`: PATCH metodu ile post başlığını güncelleme
- `deletePost()`: DELETE metodu ile post silme
- `deleteAndVerifyPost()`: DELETE sonrası GET ile kontrol
- `createAndDeletePostTest()`: POST ve DELETE işlemlerini test etme

## Test Senaryoları ve Beklenen Sonuçlar

### 1. Tekil Post Getirme (GET)
- Endpoint: `/posts/{id}`
- Request Parameters: Yok
- Beklenen Status: 200 OK
- Response: Tekil post bilgileri ve alanları

### 2. Tüm Postları Listeleme (GET)
- Endpoint: `/posts`
- Request Parameters: Yok
- Beklenen Status: 200 OK
- Response: Tüm postların listesi ve sayısı

### 3. Post Oluşturma (POST)
- Endpoint: `/posts`
- Request Body: title, body, userId
- Beklenen Status: 201 Created
- Response: Oluşturulan post bilgileri ve ID

### 4. Post Güncelleme (PUT)
- Endpoint: `/posts/{id}`
- Request Body: Tüm alanlar
- Beklenen Status: 200 OK
- Response: Güncellenen post bilgileri

### 5. Post Başlığı Güncelleme (PATCH)
- Endpoint: `/posts/{id}`
- Request Body: Sadece title
- Beklenen Status: 200 OK
- Response: Güncellenen post bilgileri

### 6. Post Silme (DELETE)
- Endpoint: `/posts/{id}`
- Request Body: Boş
- Beklenen Status: 200 OK
- GET kontrolü: 404 Not Found

## Önemli Notlar

1. JSONPlaceholder bir test API'sidir:
   - Gerçek silme işlemi yapmaz
   - Değişiklikler kalıcı değildir
   - ID'ler 1-100 arasındadır

2. HTTP Durum Kodları:
   - 200: Başarılı (OK)
   - 201: Oluşturuldu (Created)
   - 404: Bulunamadı (Not Found)
   - 400: Hatalı İstek (Bad Request)
   - 500: Sunucu Hatası (Internal Server Error)

## Projeyi Çalıştırma

1. Projeyi klonlayın
2. Maven bağımlılıklarını yükleyin
3. TestNG test sınıflarını çalıştırın

```bash
mvn test
```

Belirli bir testi çalıştırmak için:
```bash
mvn test -Dtest=Pojo#createAndDeletePostTest
```

## Bağımlılıklar

Projenin çalışması için gereken Maven bağımlılıkları:
- rest-assured
- testng
- json-path
- json-schema-validator
- hamcrest-all
