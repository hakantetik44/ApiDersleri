# API Test Otomasyonu Projesi

Bu proje, REST API test otomasyonu öğrenmek için oluşturulmuş bir eğitim projesidir. [JSONPlaceholder](https://jsonplaceholder.typicode.com/) test API'si kullanılarak çeşitli HTTP metodları test edilmiştir.

## Kullanılan Teknolojiler

- Java
- Rest Assured
- TestNG
- Maven

## Proje Yapısı

Proje iki ana test sınıfından oluşmaktadır:

### 1. FirstApiTest.java
Temel API testlerini içerir:
- `getFirstPost()`: Tek bir post'u getirme testi
- `getAllPosts()`: Tüm post'ları listeleme testi
- `createNewPost()`: Yeni bir post oluşturma testi

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
- `createPost()`: POJO kullanarak yeni post oluşturma
- `updatePost()`: PUT metodu ile post güncelleme
- `updatePostTitle()`: PATCH metodu ile post başlığını güncelleme
- `deletePost()`: Post silme
- `deleteAndVerifyPost()`: Post silme ve silme işlemini doğrulama
- `createAndDeletePostTest()`: Post oluşturma ve silme işlemlerini test etme

## Test Senaryoları

### 1. Post Oluşturma (POST)
- Yeni bir post oluşturur
- 201 Created yanıtını kontrol eder
- Oluşturulan post'un bilgilerini doğrular

### 2. Post Güncelleme (PUT)
- Var olan bir post'u günceller
- 200 OK yanıtını kontrol eder
- Güncellenen bilgileri doğrular

### 3. Post Başlığı Güncelleme (PATCH)
- Sadece başlığı günceller
- 200 OK yanıtını kontrol eder
- Güncellenen başlığı doğrular

### 4. Post Silme (DELETE)
- Post'u siler
- 200 OK yanıtını kontrol eder
- Silinen post'un erişilemez olduğunu doğrular

## Önemli Notlar

1. JSONPlaceholder bir test API'sidir:
   - Gerçek silme işlemi yapmaz
   - Değişiklikler kalıcı değildir
   - ID'ler 1-100 arasındadır

2. HTTP Durum Kodları:
   - 200: Başarılı
   - 201: Oluşturuldu
   - 404: Bulunamadı

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
