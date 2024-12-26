# API Test Örnekleri

Bu proje, REST API testlerini öğrenmek için hazırlanmış örnek test senaryolarını içerir.

## Kullanılan Teknolojiler

- Java 11
- Rest Assured
- TestNG
- Maven

## Test Sınıfları

### 1. FirstApiTest
Temel API test örneklerini içerir:
- GET isteği ile tekil post getirme
- Tüm postları listeleme
- Yeni post oluşturma (POST)

### 2. HeaderAndQueryParamsTest
Header ve Query parametreleri ile test örnekleri:
- Header kullanımı
- Query parametreleri ile filtreleme
- Limit parametresi ile sayfalama

### 3. PathQueryParamsTest
Path ve Query parametreleri ile detaylı test örnekleri:
- Path parametresi kullanımı
- Query parametresi kullanımı
- İkisinin birlikte kullanımı
- Response body doğrulamaları
- Header kontrolleri
- Email format kontrolü
- Loglama işlemleri

## Kurulum

1. Projeyi klonlayın
2. Maven dependency'leri yükleyin
3. Test sınıflarını çalıştırın

## Test Edilen API

Testler [JSONPlaceholder](https://jsonplaceholder.typicode.com/) API'si üzerinde çalışmaktadır.

## Önemli Noktalar

- Her test sınıfı belirli bir konuya odaklanır
- Testler bağımsız ve tekrar edilebilir şekilde yazılmıştır
- Response doğrulamaları detaylı şekilde yapılmıştır
- Hata durumları da test edilmiştir
